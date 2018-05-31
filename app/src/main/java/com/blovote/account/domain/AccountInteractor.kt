package com.blovote.account.domain

import io.reactivex.Completable
import io.reactivex.Single
import org.web3j.utils.Convert
import java.math.BigDecimal
import java.math.BigInteger

interface AccountInteractor {

    fun getBalance() : Single<BigInteger>


    fun sendEther(address: String, amount: BigDecimal, unit: Convert.Unit) : Completable

}