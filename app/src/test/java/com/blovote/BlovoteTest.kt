package com.blovote.test

import com.blovote.contracts.ContractBloGodImpl
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

    val godAddress : String = "0x6b47d71361fdaf6de9dd58ed3d1a68e857d87969"

    val gasPrice by lazy { web3j.ethGasPrice().send().gasPrice }
    val contractBloGod by lazy { ContractBloGodImpl.load(godAddress, web3j, credentials,
            gasPrice, Contract.GAS_LIMIT) }

    @Deprecated("DO NOT RUN THIS TEST, CONTRACT DEPLOYED AT ADDRESS: 0x6b47d71361fdaf6de9dd58ed3d1a68e857d87969")
    fun testDeployGod() {

//        println("Connected to Ethereum client version: ${web3j.web3ClientVersion().send().web3ClientVersion}")
//
//
//        print("Deploying smart-contract")
//
//        val contractGod : ContractBloGodImpl = ContractBloGodImpl.deploy(web3j, credentials,
//                ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT).send()
//
//        print("Smart contract deployed to address: ${contractGod.contractAddress}")
    }

    @Test
    fun testDeployedGod() {

        println("current gas price: " + gasPrice)

        val surveysNumber = contractBloGod.surveysNumber.send()
        println("current surveys number : ${surveysNumber}")

        val list = contractBloGod.getBlovoteAddresses(BigInteger.ZERO, surveysNumber).send()
        list.forEach { println("address: ${it}" ) }
    }

    @Test
    fun testCreateSurvey() {

//        val receipt = contractBloGod.createNewSurvey("First test survey".toByteArray(Charset.forName("UTF8")),
//                BigInteger.valueOf(100), BigInteger.valueOf(100)).send()
//
//        println("Gas used: ${receipt.gasUsed}")
//        println("Cumulative gas used: ${receipt.cumulativeGasUsed}")
//        println("$receipt")
//
//        val surveysNumber = contractBloGod.surveysNumber.send()
//        println("New number of surveys: ${surveysNumber}")
//
//        val list = contractBloGod.getBlovoteAddresses(BigInteger.ZERO, surveysNumber).send()
//        list.forEach { println("address: ${it}" ) }
    }

    @Test
    fun testFillSurvey() {



    }

}
