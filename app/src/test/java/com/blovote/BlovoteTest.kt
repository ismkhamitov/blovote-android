package com.blovote.test

import com.blovote.contracts.Contracts_BloGodImpl_sol_BloGodImpl
import com.blovote.contracts.Contracts_BlovoteImpl_sol_BlovoteImpl
import com.blovote.surveys.data.entities.QuestionType
import com.blovote.surveys.data.entities.SurveyState
import org.junit.Test
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.Web3jFactory
import org.web3j.protocol.http.HttpService
import org.web3j.tx.Contract
import org.web3j.tx.ManagedTransaction
import java.math.BigInteger
import java.nio.charset.Charset

private val NODE_ADDRESS = "https://rinkeby.infura.io/QY0BaVdS6Y3uQBYLnfJJ"

private val PASSWORD = "Ramovi07"
private val KEYSTORE = "/Users/ismail/Library/Ethereum/keystore/"
private val MAIN_WALLET_FILE = KEYSTORE + "UTC--2018-04-22T09-58-09.850325000Z--e12fb268f1ab83c738e48191d1d417d3e907e91e"

private val MAIN_ADDRESS = "0xe12fb268f1ab83c738e48191d1d417d3e907e91e"

class BlovoteTest {

    val web3j : Web3j by lazy { Web3jFactory.build(HttpService(NODE_ADDRESS)) }
    val credentials : Credentials by lazy { WalletUtils.loadCredentials(PASSWORD, MAIN_WALLET_FILE) }

    val godAddress : String = "0xe13db7b7d95afc0964940b6a4f03db2e51161386"

    val gasPrice by lazy { web3j.ethGasPrice().send().gasPrice }
    val contractBloGod by lazy { Contracts_BloGodImpl_sol_BloGodImpl.load(
            godAddress, web3j, credentials, gasPrice, Contract.GAS_LIMIT) }

    //@Deprecated("DO NOT RUN THIS TEST, CONTRACT DEPLOYED AT ADDRESS: 0x6b47d71361fdaf6de9dd58ed3d1a68e857d87969")
    @Test
    fun testDeployGod() {

        println("Connected to Ethereum client version: ${web3j.web3ClientVersion().send().web3ClientVersion}")


        println("Deploying smart-contract")

        val contractGod : Contracts_BloGodImpl_sol_BloGodImpl = Contracts_BloGodImpl_sol_BloGodImpl
                .deploy(web3j, credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT).send()

        print("Smart contract deployed to address: ${contractGod.contractAddress}")
    }

    @Test
    fun testDeployedGod() {

//        println("current gas price: $gasPrice")
//
//        val surveysNumber = contractBloGod.surveysNumber.send()
//        println("current surveys number : $surveysNumber")
//
//        val surveyAddress = contractBloGod.getBlovoteAddresses(BigInteger.valueOf(5L), BigInteger.valueOf(6L)).send()[0].toString()
//        val surveyContract = Contracts_BlovoteImpl_sol_BlovoteImpl.load(surveyAddress,
//                web3j, credentials, gasPrice, Contract.GAS_LIMIT)
//
//        val list = contractBloGod.getBlovoteAddresses(BigInteger.ZERO, surveysNumber).send()
//        list.forEach { println("address: $it" ) }
    }

    @Test
    fun testCreateSurvey() {

//        val prevNumber = contractBloGod.surveysNumber.send()
//        println("Current surveys number: $prevNumber")
//
//        var receipt = contractBloGod.createNewSurvey("Fifth test survey".toByteArray(Charset.forName("UTF8")),
//                BigInteger.valueOf(100), BigInteger.valueOf(100)).send()
//
//        println("Survey creation gas used: ${receipt.gasUsed}")
//        println("Cumulative gas used: ${receipt.cumulativeGasUsed}")
//        println("$receipt")
//
//        val currNumber = contractBloGod.surveysNumber.send()
//        println("Current surveys number: $currNumber")
//        val surveyAddress = contractBloGod.getBlovoteAddresses(prevNumber, currNumber).send()[0]
//        val surveyContract = ContractBlovoteImpl.load(surveyAddress.toString(), web3j, credentials, gasPrice, Contract.GAS_LIMIT)
//
//        receipt = surveyContract.addQuestion(BigInteger.valueOf(QuestionType.SingleVariant.getContractQuestionType().toLong()),
//                "Первый вопрос".toByteArray(Charset.forName("UTF-8"))).send()
//
//        println("Question gas used: ${receipt.gasUsed}")
//        println("Cumulative gas used: ${receipt.cumulativeGasUsed}")
//        println("$receipt")
//
//        receipt = surveyContract.addQuestionPoint(BigInteger.ZERO, "First point".toByteArray(Charset.forName("UTF-8"))).send()
//
//        println("Point gas used: ${receipt.gasUsed}")
//        println("Cumulative gas used: ${receipt.cumulativeGasUsed}")
//        println("$receipt")
//
//        receipt = surveyContract.addQuestionPoint(BigInteger.ZERO, ("Second megemegemgegaegeagefklfsjds;klfajdklsfjdslfjasd;fjjkffjfjfjjfjfjf  " +
//                "point").toByteArray(Charset.forName("UTF-8"))).send()
//
//        println("Second point gas used: ${receipt.gasUsed}")
//        println("Cumulative gas used: ${receipt.cumulativeGasUsed}")
//        println("$receipt")
//
//
//        receipt = surveyContract.addFilterQuestion(BigInteger.valueOf(QuestionType.SingleVariant.getContractQuestionType().toLong()),
//                "Ты реальный пацан?".toByteArray(Charset.forName("UTF-8"))).send()
//
//        println("Filter question gas used: ${receipt.gasUsed}")
//        println("Cumulative gas used: ${receipt.cumulativeGasUsed}")
//        println("$receipt")
//
//        receipt = surveyContract.addFilterQuestionPoint(BigInteger.ZERO, "Да".toByteArray(Charset.forName("UTF-8")), true).send()
//
//        println("Filter question point 1 gas used: ${receipt.gasUsed}")
//        println("Cumulative gas used: ${receipt.cumulativeGasUsed}")
//        println("$receipt")
//
//        receipt = surveyContract.addFilterQuestionPoint(BigInteger.ZERO, "Нет".toByteArray(Charset.forName("UTF-8")), false).send()
//
//        println("Filter question point 2 gas used: ${receipt.gasUsed}")
//        println("Cumulative gas used: ${receipt.cumulativeGasUsed}")
//        println("$receipt")
//
//        receipt = surveyContract.updateState(BigInteger.valueOf(SurveyState.Active.ordinal.toLong())).send()
//
//        println("Activation gas used: ${receipt.gasUsed}")
//        println("Cumulative gas used: ${receipt.cumulativeGasUsed}")
//        println("$receipt")
//
//        val surveysNumber = contractBloGod.surveysNumber.send()
//        println("New number of surveys: $surveysNumber")
//
//        val list = contractBloGod.getBlovoteAddresses(BigInteger.ZERO, surveysNumber).send()
//        list.forEach { println("address: $it" ) }
    }

    @Test
    fun testFillSurvey() {



    }

}
