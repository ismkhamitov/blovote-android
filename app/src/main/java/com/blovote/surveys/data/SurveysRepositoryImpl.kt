package com.blovote.surveys.data

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import com.blovote.account.data.AccountStorage
import com.blovote.contracts.ContractBloGodImpl
import com.blovote.contracts.ContractBlovoteImpl
import com.blovote.surveys.domain.SurveysRepository
import com.blovote.surveys.data.entities.Survey
import com.blovote.surveys.data.entities.SurveyState
import io.reactivex.Observable
import org.spongycastle.util.Strings
import org.web3j.protocol.Web3j
import org.web3j.tx.Contract
import java.math.BigInteger

class SurveysRepositoryImpl(private val surveysStorage: SurveysStorage,
                            private val web3j : Web3j,
                            private val godContractAddress : String,
                            private val accountStorage: AccountStorage) : SurveysRepository {

    private val gasPrice by lazy { web3j.ethGasPrice().send().gasPrice }
    private val contractBloGodImpl by lazy { ContractBloGodImpl.load(godContractAddress, web3j, accountStorage.loadCredentials(),
            gasPrice, Contract.GAS_LIMIT) }

    override fun getAllSurveys(): List<Survey> {
        return surveysStorage.getAllSurveys().value ?: listOf()
    }

    override fun observeAllSurveys(lifecycleOwner: LifecycleOwner) : Observable<List<Survey>> {
        return Observable.create<List<Survey>> {it ->
            surveysStorage.getAllSurveys().observe(lifecycleOwner,
                    Observer<List<Survey>> { t -> it.onNext(t!!) })
        }
    }

    override fun updateSurveys(): List<Survey> {
//        val creds = accountStorage.loadCredentials()
        val surveysNumder = contractBloGodImpl.surveysNumber.send()
        val lastIndex = surveysStorage.getLastSurveyIndex()

        val newAddresses = contractBloGodImpl.getBlovoteAddresses(BigInteger.valueOf(lastIndex + 1L),
                surveysNumder).send()

        val list = mutableListOf<Survey>()
        for (i in newAddresses.indices) {
            list.add(loadBlovote(newAddresses[i].toString(), lastIndex + 1 + i))
        }

        surveysStorage.saveSurveys(list)

        return list
    }

    private fun loadBlovote(address : String, index : Int) : Survey {
        val blovote = ContractBlovoteImpl.load(address, web3j, accountStorage.loadCredentials(),
                gasPrice, Contract.GAS_LIMIT)
        val title = Strings.fromByteArray(blovote.title().send())
        val state = SurveyState.values()[blovote.state.send().toInt()]
        val creationTime = blovote.creationTimestamp().send().toLong()
        val requiredRespCnt = blovote.requiredRespondentsCount().send().toInt()
        val currentRespCnt = blovote.currentRespondentsCount().send().toInt()
        val rewardSize  = blovote.rewardSize().send().toString()
        val questionsCount = blovote.questionsCount.send().toInt()

        return Survey(address, index, title, state, creationTime, requiredRespCnt, currentRespCnt, rewardSize, questionsCount)
    }



    companion object {
        private val REWARD_DIV_EXP = 12
    }

}