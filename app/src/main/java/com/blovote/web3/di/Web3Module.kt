package com.blovote.web3.di

import dagger.Module
import dagger.Provides
import org.web3j.protocol.Web3j
import org.web3j.protocol.Web3jFactory
import org.web3j.protocol.http.HttpService
import javax.inject.Named
import javax.inject.Singleton

const val NODE_ADDRESS: String = "node_address"

@Module
class Web3Module {

    private val ADDRESS: String = "https://rinkeby.infura.io/QY0BaVdS6Y3uQBYLnfJJ"

    @Provides
    @Singleton
    @Named(NODE_ADDRESS)
    fun provideNodeAddress() : String {
        return ADDRESS
    }

    @Provides
    @Singleton
    fun provideWeb3(@Named(NODE_ADDRESS) address: String) : Web3j {
        return Web3jFactory.build(HttpService(address))
    }

}