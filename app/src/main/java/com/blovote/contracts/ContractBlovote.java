package com.blovote.contracts;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
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
public class ContractBlovote extends Contract {
    private static final String BINARY = "";

    public static final String FUNC_UPDATESTATE = "updateState";

    public static final String FUNC_CURRENTRESPONDENTSCOUNT = "currentRespondentsCount";

    public static final String FUNC_RESPONDTEXT = "respondText";

    public static final String FUNC_GETSTATE = "getState";

    public static final String FUNC_GETQUESTIONPOINTSCOUNT = "getQuestionPointsCount";

    public static final String FUNC_ADDQUESTIONPOINT = "addQuestionPoint";

    public static final String FUNC_GETRESPONDDATA = "getRespondData";

    public static final String FUNC_GETQUESTIONINFO = "getQuestionInfo";

    public static final String FUNC_TITLE = "title";

    public static final String FUNC_SETBLOGOD = "setBloGod";

    public static final String FUNC_REQUIREDRESPONDENTSCOUNT = "requiredRespondentsCount";

    public static final String FUNC_ISAVAILABLETONEWRESPONDENTS = "isAvailableToNewRespondents";

    public static final String FUNC_RESPONDNUMBERS = "respondNumbers";

    public static final String FUNC_CREATIONTIMESTAMP = "creationTimestamp";

    public static final String FUNC_REWARDSIZE = "rewardSize";

    public static final String FUNC_ADDQUESTION = "addQuestion";

    public static final String FUNC_GETQUESTIONSCOUNT = "getQuestionsCount";

    public static final String FUNC_GETQUESTIONPOINTINFO = "getQuestionPointInfo";

    protected ContractBlovote(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ContractBlovote(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> updateState(BigInteger state) {
        final Function function = new Function(
                FUNC_UPDATESTATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(state)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> currentRespondentsCount() {
        final Function function = new Function(FUNC_CURRENTRESPONDENTSCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> respondText(byte[] answerText) {
        final Function function = new Function(
                FUNC_RESPONDTEXT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(answerText)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getState() {
        final Function function = new Function(FUNC_GETSTATE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> getQuestionPointsCount(BigInteger qIndex) {
        final Function function = new Function(FUNC_GETQUESTIONPOINTSCOUNT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(qIndex)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> addQuestionPoint(BigInteger qIndex, byte[] ansText) {
        final Function function = new Function(
                FUNC_ADDQUESTIONPOINT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(qIndex), 
                new org.web3j.abi.datatypes.DynamicBytes(ansText)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> getRespondData(BigInteger qIndex, BigInteger respondIndex) {
        final Function function = new Function(
                FUNC_GETRESPONDDATA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(qIndex), 
                new org.web3j.abi.datatypes.generated.Uint256(respondIndex)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple2<BigInteger, byte[]>> getQuestionInfo(BigInteger index) {
        final Function function = new Function(FUNC_GETQUESTIONINFO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}, new TypeReference<DynamicBytes>() {}));
        return new RemoteCall<Tuple2<BigInteger, byte[]>>(
                new Callable<Tuple2<BigInteger, byte[]>>() {
                    @Override
                    public Tuple2<BigInteger, byte[]> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<BigInteger, byte[]>(
                                (BigInteger) results.get(0).getValue(), 
                                (byte[]) results.get(1).getValue());
                    }
                });
    }

    public RemoteCall<byte[]> title() {
        final Function function = new Function(FUNC_TITLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<TransactionReceipt> setBloGod(String blogod) {
        final Function function = new Function(
                FUNC_SETBLOGOD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(blogod)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> requiredRespondentsCount() {
        final Function function = new Function(FUNC_REQUIREDRESPONDENTSCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Boolean> isAvailableToNewRespondents() {
        final Function function = new Function(FUNC_ISAVAILABLETONEWRESPONDENTS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> respondNumbers(List<BigInteger> numbers) {
        final Function function = new Function(
                FUNC_RESPONDNUMBERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint8>(
                        org.web3j.abi.Utils.typeMap(numbers, org.web3j.abi.datatypes.generated.Uint8.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> creationTimestamp() {
        final Function function = new Function(FUNC_CREATIONTIMESTAMP, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> rewardSize() {
        final Function function = new Function(FUNC_REWARDSIZE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> addQuestion(BigInteger qtype, byte[] qtitle) {
        final Function function = new Function(
                FUNC_ADDQUESTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(qtype), 
                new org.web3j.abi.datatypes.DynamicBytes(qtitle)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getQuestionsCount() {
        final Function function = new Function(FUNC_GETQUESTIONSCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<byte[]> getQuestionPointInfo(BigInteger qIndex, BigInteger pointIndex) {
        final Function function = new Function(FUNC_GETQUESTIONPOINTINFO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(qIndex), 
                new org.web3j.abi.datatypes.generated.Uint256(pointIndex)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public static RemoteCall<ContractBlovote> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ContractBlovote.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<ContractBlovote> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ContractBlovote.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static ContractBlovote load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ContractBlovote(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static ContractBlovote load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ContractBlovote(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
