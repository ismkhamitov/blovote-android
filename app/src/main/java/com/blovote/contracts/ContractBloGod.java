package com.blovote.contracts;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.4.0.
 */
public class ContractBloGod extends Contract {
    private static final String BINARY = "";

    public static final String FUNC_CREATENEWSURVEY = "createNewSurvey";

    public static final String FUNC_GETBLOVOTEADDRESSES = "getBlovoteAddresses";

    public static final String FUNC_GETSURVEYSNUMBER = "getSurveysNumber";

    protected ContractBloGod(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ContractBloGod(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> createNewSurvey(byte[] _title, BigInteger _respondentsCount, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_CREATENEWSURVEY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_title), 
                new org.web3j.abi.datatypes.generated.Uint32(_respondentsCount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<List> getBlovoteAddresses(BigInteger startIndex, BigInteger endIndex) {
        final Function function = new Function(FUNC_GETBLOVOTEADDRESSES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(startIndex), 
                new org.web3j.abi.datatypes.generated.Uint256(endIndex)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        return new RemoteCall<List>(
                () -> executeCallSingleValueReturn(function, List.class));
    }

    public RemoteCall<BigInteger> getSurveysNumber() {
        final Function function = new Function(FUNC_GETSURVEYSNUMBER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public static RemoteCall<ContractBloGod> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ContractBloGod.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<ContractBloGod> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ContractBloGod.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static ContractBloGod load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ContractBloGod(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static ContractBloGod load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ContractBloGod(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
