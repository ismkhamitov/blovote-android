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
public class Contracts_BloGodImpl_sol_BloGodImpl extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b506130fd806100206000396000f3006080604052600436106100565763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166337a8cd49811461005b57806352d9d34f146100d85780636d40258b14610143575b600080fd5b6040805160206004803580820135601f81018490048402850184019095528484526100af9436949293602493928401919081908401838280828437509497505050923563ffffffff16935061016a92505050565b6040805173ffffffffffffffffffffffffffffffffffffffff9092168252519081900360200190f35b3480156100e457600080fd5b506100f3600435602435610307565b60408051602080825283518183015283519192839290830191858101910280838360005b8381101561012f578181015183820152602001610117565b505050509050019250505060405180910390f35b34801561014f57600080fd5b50610158610493565b60408051918252519081900360200190f35b60008034338585610179610499565b73ffffffffffffffffffffffffffffffffffffffff8416815263ffffffff82166040820152606060208083018281528551928401929092528451608084019186019080838360005b838110156101d95781810151838201526020016101c1565b50505050905090810190601f1680156102065780820380516001836020036101000a031916815260200191505b509450505050506040518091039082f080158015610228573d6000803e3d6000fd5b50600080546001810182558180527f290decd9548b62a8d60345a988386fc84ba6bc95484008f6362f93160ef3e56301805473ffffffffffffffffffffffffffffffffffffffff191673ffffffffffffffffffffffffffffffffffffffff848116918217909255604080517f61f9e5970000000000000000000000000000000000000000000000000000000081523090931660048401525193955093506361f9e597926024808301939282900301818387803b1580156102e757600080fd5b505af11580156102fb573d6000803e3d6000fd5b50929695505050505050565b606080600080851015801561031b57508484115b15156103d457604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152604760248201527f496e646578206d7573742062652030206f7220706f73697469766520616e642060448201527f656e64496e6465782073686f756c64206265206d6f726520746861742073746160648201527f7274496e64657800000000000000000000000000000000000000000000000000608482015290519081900360a40190fd5b848403604051908082528060200260200182016040528015610400578160200160208202803883390190505b5091508490505b8381108015610417575060005481105b1561048b57600080548290811061042a57fe5b600091825260209091200154825173ffffffffffffffffffffffffffffffffffffffff90911690839087840390811061045f57fe5b73ffffffffffffffffffffffffffffffffffffffff909216602092830290910190910152600101610407565b509392505050565b60005490565b604051612c28806104aa833901905600608060405260405162002c2838038062002c2883398101604090815281516020808401519284015160018054600160a060020a031916600160a060020a038516179055426003556000805460c060020a60ff02191690559290930180519193909291620000739160029190850190620000ca565b506000805460a060020a63ffffffff0219167401000000000000000000000000000000000000000063ffffffff848116820292909217928390559091041634811515620000bc57fe5b04600455506200016f915050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200010d57805160ff19168380011785556200013d565b828001600101855582156200013d579182015b828111156200013d57825182559160200191906001019062000120565b506200014b9291506200014f565b5090565b6200016c91905b808211156200014b576000815560010162000156565b90565b612aa9806200017f6000396000f3006080604052600436106101695763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166302d05d3f811461016e5780630591d3ab1461019f5780630b15f744146101bc5780630b275288146101e35780630d28135e146101f857806315b33f4c146102185780631865c57d146102bd57806320a66358146102f657806332cb6b161461030e57806335cb96781461032657806340f68b821461034a57806348df6324146103da5780634a79d50c146103f257806361f9e597146104075780636231a772146104285780636a16294214610463578063798168ae146104785780638439d8ff146104a15780639d8edfca14610587578063b3311086146105a7578063bc2d5b16146105bc578063bc840826146105d7578063bea20117146105ec578063c2e112d214610613578063d259994014610628578063e085f9801461064f578063e7bee05d14610667578063ee9c902414610690575b600080fd5b34801561017a57600080fd5b506101836106ab565b60408051600160a060020a039092168252519081900360200190f35b3480156101ab57600080fd5b506101ba60ff600435166106ba565b005b3480156101c857600080fd5b506101d1610782565b60408051918252519081900360200190f35b3480156101ef57600080fd5b506101d1610789565b34801561020457600080fd5b506101ba600480356024810191013561078f565b34801561022457600080fd5b506102306004356109c0565b6040518083600381111561024057fe5b60ff16815260200180602001828103825283818151815260200191508051906020019080838360005b83811015610281578181015183820152602001610269565b50505050905090810190601f1680156102ae5780820380516001836020036101000a031916815260200191505b50935050505060405180910390f35b3480156102c957600080fd5b506102d2610a7f565b604051808260028111156102e257fe5b60ff16815260200191505060405180910390f35b34801561030257600080fd5b506101d1600435610a8f565b34801561031a57600080fd5b506101d1600435610b11565b34801561033257600080fd5b506101ba600480359060248035908101910135610ba8565b34801561035657600080fd5b50610365600435602435610d57565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561039f578181015183820152602001610387565b50505050905090810190601f1680156103cc5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b3480156103e657600080fd5b50610230600435610fed565b3480156103fe57600080fd5b50610365611132565b34801561041357600080fd5b506101ba600160a060020a03600435166111c5565b34801561043457600080fd5b506104406004356111f4565b60408051600160a060020a03909316835290151560208301528051918290030190f35b34801561046f57600080fd5b506101d161122c565b34801561048457600080fd5b5061048d61123f565b604080519115158252519081900360200190f35b3480156104ad57600080fd5b506104b9600435611256565b604051808460038111156104c957fe5b60ff1681526020018060200180602001838103835285818151815260200191508051906020019080838360005b8381101561050e5781810151838201526020016104f6565b50505050905090810190601f16801561053b5780820380516001836020036101000a031916815260200191505b508381038252845181528451602091820191808701910280838360005b83811015610570578181015183820152602001610558565b505050509050019550505050505060405180910390f35b34801561059357600080fd5b506101ba6004803560248101910135611427565b3480156105b357600080fd5b506101d1611947565b3480156105c857600080fd5b5061036560043560243561194d565b3480156105e357600080fd5b506101d1611a71565b3480156105f857600080fd5b506101ba6004803560ff169060248035908101910135611a77565b34801561061f57600080fd5b506101d1611c5d565b34801561063457600080fd5b506101ba6004803560ff169060248035908101910135611c63565b34801561065b57600080fd5b50610230600435611f35565b34801561067357600080fd5b506101ba6004803590602480359081019101356044351515611fbf565b34801561069c57600080fd5b50610365600435602435612118565b600154600160a060020a031681565b60015433600160a060020a03908116911614610746576040805160e560020a62461bcd02815260206004820152602c60248201527f4f6e6c792063726561746f72206f66207375727665792063616e20757064617460448201527f6520697473207374617465210000000000000000000000000000000000000000606482015290519081900360840190fd5b6000805482919078ff000000000000000000000000000000000000000000000000191660c060020a83600281111561077a57fe5b021790555050565b6005545b90565b60095490565b60008054819060019060c060020a900460ff1660028111156107ad57fe5b8160028111156107b957fe5b146107fc576040805160e560020a62461bcd0281526020600482015260156024820152600080516020612a3e833981519152604482015290519081900360640190fd5b61080533612227565b6006546009805492955090918590811061081b57fe5b6000918252602090912060016002909202010154106108aa576040805160e560020a62461bcd02815260206004820152603260248201527f416c6c20616e73776572732066726f6d20746869732073656e6465722061726560448201527f20616c7265616479207265636569766564210000000000000000000000000000606482015290519081900360840190fd5b60098054849081106108b857fe5b60009182526020822060016002909202010154925060068054849081106108db57fe5b600091825260209091206003918202015460ff16908111156108f957fe5b14610974576040805160e560020a62461bcd02815260206004820152602360248201527f546172676574207175657374696f6e2068617320696e636f727265637420747960448201527f7065210000000000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b600980548490811061098257fe5b6000918252602080832060016002909302018201805492830180825590845292206109af910187876127fd565b50506109b9612506565b5050505050565b60058054829081106109ce57fe5b600091825260209182902060049091020180546001808301805460408051601f600260001996851615610100029690960190931694909404918201879004870284018701905280835260ff9093169550929390929190830182828015610a755780601f10610a4a57610100808354040283529160200191610a75565b820191906000526020600020905b815481529060010190602001808311610a5857829003601f168201915b5050505050905082565b60005460c060020a900460ff1690565b60008160008110158015610aa4575060065481105b1515610ae8576040805160e560020a62461bcd0281526020600482015260156024820152600080516020612a1e833981519152604482015290519081900360640190fd5b6006805484908110610af657fe5b60009182526020909120600260039092020101549392505050565b60008160008110158015610b2757506005548111155b1515610b7f576040805160e560020a62461bcd0281526020600482015260276024820152600080516020612a5e83398151915260448201526000805160206129fe833981519152606482015290519081900360840190fd5b6005805484908110610b8d57fe5b60009182526020909120600260049092020101549392505050565b8260008110158015610bbb575060065481105b1515610bff576040805160e560020a62461bcd0281526020600482015260156024820152600080516020612a1e833981519152604482015290519081900360640190fd5b6000805460c060020a900460ff166002811115610c1857fe5b816002811115610c2457fe5b14610c67576040805160e560020a62461bcd0281526020600482015260156024820152600080516020612a3e833981519152604482015290519081900360640190fd5b610c92600686815481101515610c7957fe5b600091825260209091206003909102015460ff1661257e565b1515610d0e576040805160e560020a62461bcd02815260206004820152602c60248201527f556e61626c6520746f2061646420706f696e747320666f7220616e737765722060448201527f6f66207468697320747970650000000000000000000000000000000000000000606482015290519081900360840190fd5b6006805486908110610d1c57fe5b6000918252602080832060026003909302019190910180546001810180835591845291909220610d4e910186866127fd565b50505050505050565b60608260008110158015610d6c575060065481105b1515610db0576040805160e560020a62461bcd0281526020600482015260156024820152600080516020612a1e833981519152604482015290519081900360640190fd5b8260008110158015610dc457506009548111155b1515610e1a576040805160e560020a62461bcd02815260206004820152601b60248201527f526573706f6e64656e7420646f6573206e6f7420657869737473210000000000604482015290519081900360640190fd5b60005460019060c060020a900460ff166002811115610e3557fe5b816002811115610e4157fe5b14610e84576040805160e560020a62461bcd0281526020600482015260156024820152600080516020612a3e833981519152604482015290519081900360640190fd5b85600986815481101515610e9457fe5b600091825260209091206001600290920201015411610f23576040805160e560020a62461bcd02815260206004820152602d60248201527f526573706f6e64656e7420646964206e6f7420616e73776572656420746f207460448201527f686174207175657374696f6e2100000000000000000000000000000000000000606482015290519081900360840190fd5b6009805486908110610f3157fe5b906000526020600020906002020160010186815481101515610f4f57fe5b600091825260209182902001805460408051601f6002600019610100600187161502019094169390930492830185900485028101850190915281815292830182828015610fdd5780601f10610fb257610100808354040283529160200191610fdd565b820191906000526020600020905b815481529060010190602001808311610fc057829003601f168201915b5050505050935050505092915050565b600060608260008110158015611004575060065481105b1515611048576040805160e560020a62461bcd0281526020600482015260156024820152600080516020612a1e833981519152604482015290519081900360640190fd5b600680548590811061105657fe5b60009182526020909120600390910201546006805460ff909216918690811061107b57fe5b9060005260206000209060030201600101808054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156111215780601f106110f657610100808354040283529160200191611121565b820191906000526020600020905b81548152906001019060200180831161110457829003601f168201915b505050505090509250925050915091565b60028054604080516020601f60001961010060018716150201909416859004938401819004810282018101909252828152606093909290918301828280156111bb5780601f10611190576101008083540402835291602001916111bb565b820191906000526020600020905b81548152906001019060200180831161119e57829003601f168201915b5050505050905090565b6000805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0392909216919091179055565b600980548290811061120257fe5b6000918252602090912060029091020154600160a060020a038116915060a060020a900460ff1682565b60005460a060020a900463ffffffff1690565b60095460005460a060020a900463ffffffff161190565b6000606080836000811015801561126f57506005548111155b15156112c7576040805160e560020a62461bcd0281526020600482015260276024820152600080516020612a5e83398151915260448201526000805160206129fe833981519152606482015290519081900360840190fd5b60058054869081106112d557fe5b60009182526020909120600490910201546005805460ff90921691879081106112fa57fe5b906000526020600020906004020160010160058781548110151561131a57fe5b9060005260206000209060040201600301818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156113c05780601f10611395576101008083540402835291602001916113c0565b820191906000526020600020905b8154815290600101906020018083116113a357829003601f168201915b505050505091508080548060200260200160405190810160405280929190818152602001828054801561141257602002820191906000526020600020905b8154815260200190600101908083116113fe575b50505050509050935093509350509193909250565b60008060006001600060189054906101000a900460ff16600281111561144957fe5b81600281111561145557fe5b14611498576040805160e560020a62461bcd0281526020600482015260156024820152600080516020612a3e833981519152604482015290519081900360640190fd5b6114a133612227565b600654600980549296509091869081106114b757fe5b600091825260209091206001600290920201015410611546576040805160e560020a62461bcd02815260206004820152603260248201527f416c6c20616e73776572732066726f6d20746869732073656e6465722061726560448201527f20616c7265616479207265636569766564210000000000000000000000000000606482015290519081900360840190fd5b600980548590811061155457fe5b600091825260208220600160029092020101549350600680548590811061157757fe5b600091825260209091206003918202015460ff169081111561159557fe5b1415611611576040805160e560020a62461bcd02815260206004820152602560248201527f546172676574207175657374696f6e2063616e6e6f742068617665207465787460448201527f2074797065000000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b6001600680548590811061162157fe5b600091825260209091206003918202015460ff169081111561163f57fe5b14158061164c5750600185145b15156116c8576040805160e560020a62461bcd02815260206004820152602960248201527f4f6e6c79206f6e65206e756d6265722073686f756c642062652073656e74206160448201527f7320616e73776572210000000000000000000000000000000000000000000000606482015290519081900360840190fd5b600360068054859081106116d857fe5b600091825260209091206003918202015460ff16908111156116f657fe5b1415806117445750600660098581548110151561170f57fe5b60009182526020909120600160029092020101548154811061172d57fe5b600091825260209091206002600390920201015485145b15156117c0576040805160e560020a62461bcd02815260206004820152603660248201527f536f72742d74797065207175657374696f6e206d75737420726563656976652060448201527f6172726179206f6620616c6c2076617269616e74732100000000000000000000606482015290519081900360840190fd5b60098054859081106117ce57fe5b9060005260206000209060020201600101868690506040519080825280601f01601f191660200182016040528015611810578160200160208202803883390190505b50815460018101808455600093845260209384902083519194611839949190930192019061287b565b5050600091505b848210156119375785858381811061185457fe5b9050602002013560ff167f01000000000000000000000000000000000000000000000000000000000000000260098581548110151561188f57fe5b9060005260206000209060020201600101848154811015156118ad57fe5b906000526020600020018381546001816001161561010002031660029004811015156118d557fe5b8154600116156118f45790600052602060002090602091828204019190065b601f036101000a81548160ff021916907f010000000000000000000000000000000000000000000000000000000000000084040217905550816001019150611840565b61193f612506565b505050505050565b60035490565b6060826000811015801561196357506005548111155b15156119bb576040805160e560020a62461bcd0281526020600482015260276024820152600080516020612a5e83398151915260448201526000805160206129fe833981519152606482015290519081900360840190fd5b8383600081101580156119ef575060058054839081106119d757fe5b90600052602060002090600402016002018054905081105b1515611a45576040805160e560020a62461bcd02815260206004820152601d60248201527f5175657374696f6e20706f696e7420646f6573206e6f74206578697374000000604482015290519081900360640190fd5b6005805487908110611a5357fe5b906000526020600020906004020160020185815481101515610f4f57fe5b60045490565b6000805460c060020a900460ff166002811115611a9057fe5b816002811115611a9c57fe5b14611adf576040805160e560020a62461bcd0281526020600482015260156024820152600080516020612a3e833981519152604482015290519081900360640190fd5b811515611b5c576040805160e560020a62461bcd02815260206004820152602160248201527f5175657374696f6e2773207469746c652063616e6e6f7420626520656d70747960448201527f2100000000000000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b6006606060405190810160405280866003811115611b7657fe5b815260200185858080601f0160208091040260200160405190810160405280939291908181526020018383808284375050509284525050604080516000808252602082810190935291909301929150611bdf565b6060815260200190600190039081611bca5790505b509052815460018181018085556000948552602090942083516003938402909101805490939192849260ff19909216918490811115611c1a57fe5b02179055506020828101518051611c37926001850192019061287b565b5060408201518051611c539160028401916020909101906128e9565b5050505050505050565b60065490565b826001816003811115611c7257fe5b1480611c8957506002816003811115611c8757fe5b145b1515611d2b576040805160e560020a62461bcd02815260206004820152604760248201527f4f6e6c79204f6e652d66726f6d2d6d616e7920616e64204d616e792d66726f6d60448201527f2d6d616e79207175657374696f6e732063616e2062652066696c74657220717560648201527f657374696f6e7300000000000000000000000000000000000000000000000000608482015290519081900360a40190fd5b6000805460c060020a900460ff166002811115611d4457fe5b816002811115611d5057fe5b14611d93576040805160e560020a62461bcd0281526020600482015260156024820152600080516020612a3e833981519152604482015290519081900360640190fd5b821515611dea576040805160e560020a62461bcd02815260206004820152601860248201527f5175657374696f6e2063616e6e6f7420626520656d7074790000000000000000604482015290519081900360640190fd5b6005608060405190810160405280876003811115611e0457fe5b815260200186868080601f0160208091040260200160405190810160405280939291908181526020018383808284375050509284525050604080516000808252602082810190935291909301929150611e6d565b6060815260200190600190039081611e585790505b5081526020016000604051908082528060200260200182016040528015611e9e578160200160208202803883390190505b509052815460018181018085556000948552602090942083516004909302018054909291839160ff191690836003811115611ed557fe5b02179055506020828101518051611ef2926001850192019061287b565b5060408201518051611f0e9160028401916020909101906128e9565b5060608201518051611f2a916003840191602090910190612942565b505050505050505050565b6006805482908110611f4357fe5b600091825260209182902060039091020180546001808301805460408051601f600260001996851615610100029690960190931694909404918201879004870284018701905280835260ff9093169550929390929190830182828015610a755780601f10610a4a57610100808354040283529160200191610a75565b8360008110158015611fd357506005548111155b151561202b576040805160e560020a62461bcd0281526020600482015260276024820152600080516020612a5e83398151915260448201526000805160206129fe833981519152606482015290519081900360840190fd5b6000805460c060020a900460ff16600281111561204457fe5b81600281111561205057fe5b14612093576040805160e560020a62461bcd0281526020600482015260156024820152600080516020612a3e833981519152604482015290519081900360640190fd5b60058054879081106120a157fe5b60009182526020808320600260049093020191909101805460018101808355918452919092206120d3910187876127fd565b5050821561193f5760058054879081106120e957fe5b600091825260208083206003600490930201919091018054600181018255908352912001869055505050505050565b6060826000811015801561212d575060065481105b1515612171576040805160e560020a62461bcd0281526020600482015260156024820152600080516020612a1e833981519152604482015290519081900360640190fd5b8383600081101580156121a55750600680548390811061218d57fe5b90600052602060002090600302016002018054905081105b15156121fb576040805160e560020a62461bcd02815260206004820152601b60248201527f5175657374696f6e20706f696e742073686f756c642065786973740000000000604482015290519081900360640190fd5b600680548790811061220957fe5b906000526020600020906003020160020185815481101515610f4f57fe5b600061223161123f565b15156122ad576040805160e560020a62461bcd02815260206004820152603260248201527f5265717569726564206e756d626572206f6620726573706f6e64656e7473206960448201527f7320616c72656164792072656163686564210000000000000000000000000000606482015290519081900360840190fd5b600754600160a060020a031615806122d25750600754600160a060020a038381169116145b156123ee576007805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03841617905560095415156123e65760408051606081018252600160a060020a03841681526000602080830182905283518281529081018452600993830191612352565b606081526020019060019003908161233d5790505b5090528154600181810180855560009485526020948590208451600290940201805486860151151560a060020a0274ff000000000000000000000000000000000000000019600160a060020a0390961673ffffffffffffffffffffffffffffffffffffffff1990921691909117949094169390931783556040840151805191956123e1938501929101906128e9565b505050505b506000612501565b50600160a060020a038116600090815260086020526040902054801515612501575060098054600160a060020a038316600081815260086020908152604080832085905580516060810182529384528382018390528051838152918201815293949383019161246d565b60608152602001906001900390816124585790505b5090528154600181810180855560009485526020948590208451600290940201805486860151151560a060020a0274ff000000000000000000000000000000000000000019600160a060020a0390961673ffffffffffffffffffffffffffffffffffffffff1990921691909117949094169390931783556040840151805191956124fc938501929101906128e9565b505050505b919050565b61250e6125c1565b60095460005460a060020a900463ffffffff16111580612539575060045430600160a060020a031631105b1561257c576000805478ff000000000000000000000000000000000000000000000000191678020000000000000000000000000000000000000000000000001790555b565b6000600182600381111561258e57fe5b14806125a5575060028260038111156125a357fe5b145b806125bb575060038260038111156125b957fe5b145b92915050565b60006125cb612706565b1515612647576040805160e560020a62461bcd02815260206004820152602660248201527f53656e646572206973206e6f742061207061727469636970616e74206f66207360448201527f7572766579210000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b61265033612227565b905061265a612742565b15801561268a5750600654600980548390811061267357fe5b906000526020600020906002020160010180549050145b1561270357600454604051600160a060020a0333169180156108fc02916000818181858888f193505050505060016009828154811015156126c757fe5b60009182526020909120600290910201805491151560a060020a0274ff0000000000000000000000000000000000000000199092169190911790555b50565b60075460009033600160a060020a039081169116148061273d5750600160a060020a03331660009081526008602052604090205415155b905090565b600061274c612706565b15156127c8576040805160e560020a62461bcd02815260206004820152602660248201527f53656e646572206973206e6f742061207061727469636970616e74206f66207360448201527f7572766579210000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b60096127d333612227565b815481106127dd57fe5b600091825260209091206002909102015460a060020a900460ff16919050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061283e5782800160ff1982351617855561286b565b8280016001018555821561286b579182015b8281111561286b578235825591602001919060010190612850565b5061287792915061297c565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106128bc57805160ff191683800117855561286b565b8280016001018555821561286b579182015b8281111561286b5782518255916020019190600101906128ce565b828054828255906000526020600020908101928215612936579160200282015b82811115612936578251805161292691849160209091019061287b565b5091602001919060010190612909565b50612877929150612996565b82805482825590600052602060002090810192821561286b579160200282018281111561286b5782518255916020019190600101906128ce565b61078691905b808211156128775760008155600101612982565b61078691905b808211156128775760006129b082826129b9565b5060010161299c565b50805460018160011615610100020316600290046000825580601f106129df5750612703565b601f016020900490600052602060002090810190612703919061297c560020657869737473000000000000000000000000000000000000000000000000005175657374696f6e2073686f756c642065786973740000000000000000000000496e76616c6964207375727665792073746174652100000000000000000000005175657374696f6e20776974682064617420696e64657820646f6573206e6f74a165627a7a72305820ad8c5cc79d0d843e12ee725d23285a157ac0849aa9f1b182995c6cd5354847000029a165627a7a72305820935ae2532cece90821229d13d2477a41ece37225d6cf1e409cd007deef561eeb0029";

    public static final String FUNC_CREATENEWSURVEY = "createNewSurvey";

    public static final String FUNC_GETBLOVOTEADDRESSES = "getBlovoteAddresses";

    public static final String FUNC_GETSURVEYSNUMBER = "getSurveysNumber";

    protected Contracts_BloGodImpl_sol_BloGodImpl(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Contracts_BloGodImpl_sol_BloGodImpl(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
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

    public static RemoteCall<Contracts_BloGodImpl_sol_BloGodImpl> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Contracts_BloGodImpl_sol_BloGodImpl.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Contracts_BloGodImpl_sol_BloGodImpl> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Contracts_BloGodImpl_sol_BloGodImpl.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static Contracts_BloGodImpl_sol_BloGodImpl load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Contracts_BloGodImpl_sol_BloGodImpl(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Contracts_BloGodImpl_sol_BloGodImpl load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Contracts_BloGodImpl_sol_BloGodImpl(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
