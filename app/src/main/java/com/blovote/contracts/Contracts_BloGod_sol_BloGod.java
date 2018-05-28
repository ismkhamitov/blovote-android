package com.blovote.contracts;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
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
public class Contracts_BloGod_sol_BloGod extends Contract {
    private static final String BINARY = "";

    public static final String FUNC_CREATENEWSURVEY = "createNewSurvey";

    public static final String FUNC_GETBLOVOTEADDRESSES = "getBlovoteAddresses";

    public static final String FUNC_GETSURVEYSNUMBER = "getSurveysNumber";

    protected Contracts_BloGod_sol_BloGod(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Contracts_BloGod_sol_BloGod(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
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
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteCall<BigInteger> getSurveysNumber() {
        final Function function = new Function(FUNC_GETSURVEYSNUMBER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public static RemoteCall<Contracts_BloGod_sol_BloGod> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Contracts_BloGod_sol_BloGod.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Contracts_BloGod_sol_BloGod> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Contracts_BloGod_sol_BloGod.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static Contracts_BloGod_sol_BloGod load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Contracts_BloGod_sol_BloGod(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Contracts_BloGod_sol_BloGod load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Contracts_BloGod_sol_BloGod(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
