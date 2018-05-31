package com.blovote.account.domain

import com.blovote.account.data.AccountStorage
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.tx.Transfer
import org.web3j.utils.Convert
import java.math.BigDecimal
import java.math.BigInteger


class AccountInteractorImpl(val accountStorage: AccountStorage,
                            val web3j: Web3j) : AccountInteractor {

    override fun getBalance(): Single<BigInteger> {
        val getBalanceSingle : Single<BigInteger> = Single.create { emitter ->
                try {
                    val getBalance = web3j.ethGetBalance(accountStorage.loadCredentials().address,
                            DefaultBlockParameterName.LATEST).send()
                    if (getBalance.hasError()) {
                        emitter.onError(IllegalStateException())
                    } else {
                        emitter.onSuccess(getBalance.balance)
                    }
                } catch (e: Exception) {
                    emitter.onError(e)
                }
        }

        return getBalanceSingle.subscribeOn(Schedulers.computation())
    }

    override fun sendEther(address: String, amount: BigDecimal, unit: Convert.Unit): Completable {
        val sendCompletable = Completable.create { emitter ->
            try {
                Transfer.sendFunds(web3j, accountStorage.loadCredentials(), address,
                        amount, unit).send()
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }

        }

        return sendCompletable.subscribeOn(Schedulers.computation())
    }
}