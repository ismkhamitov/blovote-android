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
public class ContractBloGodImpl extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50613394806100206000396000f3006080604052600436106100565763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166337a8cd49811461005b57806352d9d34f146100d85780636d40258b14610143575b600080fd5b6040805160206004803580820135601f81018490048402850184019095528484526100af9436949293602493928401919081908401838280828437509497505050923563ffffffff16935061016a92505050565b6040805173ffffffffffffffffffffffffffffffffffffffff9092168252519081900360200190f35b3480156100e457600080fd5b506100f3600435602435610319565b60408051602080825283518183015283519192839290830191858101910280838360005b8381101561012f578181015183820152602001610117565b505050509050019250505060405180910390f35b34801561014f57600080fd5b506101586104a5565b60408051918252519081900360200190f35b600080343385856101796104ab565b73ffffffffffffffffffffffffffffffffffffffff8416815263ffffffff82166040820152606060208083018281528551928401929092528451608084019186019080838360005b838110156101d95781810151838201526020016101c1565b50505050905090810190601f1680156102065780820380516001836020036101000a031916815260200191505b509450505050506040518091039082f080158015610228573d6000803e3d6000fd5b506002805473ffffffffffffffffffffffffffffffffffffffff838116600081815260016020819052604080832086905590850186559481527f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5ace909301805473ffffffffffffffffffffffffffffffffffffffff19168217905583517f61f9e59700000000000000000000000000000000000000000000000000000000815230909216600483015292519395509193506361f9e5979260248084019382900301818387803b1580156102f957600080fd5b505af115801561030d573d6000803e3d6000fd5b50929695505050505050565b606080600080851015801561032d57508484115b15156103e657604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152604760248201527f496e646578206d7573742062652030206f7220706f73697469766520616e642060448201527f656e64496e6465782073686f756c64206265206d6f726520746861742073746160648201527f7274496e64657800000000000000000000000000000000000000000000000000608482015290519081900360a40190fd5b848403604051908082528060200260200182016040528015610412578160200160208202803883390190505b5091508490505b8381108015610429575060025481105b1561049d57600280548290811061043c57fe5b600091825260209091200154825173ffffffffffffffffffffffffffffffffffffffff90911690839087840390811061047157fe5b73ffffffffffffffffffffffffffffffffffffffff909216602092830290910190910152600101610419565b509392505050565b60025490565b604051612ead806104bc833901905600608060405260405162002ead38038062002ead83398101604090815281516020808401519284015160018054600160a060020a031916600160a060020a038516179055426003556000805460c060020a60ff02191690559290930180519193909291620000739160029190850190620000ca565b506000805460a060020a63ffffffff0219167401000000000000000000000000000000000000000063ffffffff848116820292909217928390559091041634811515620000bc57fe5b04600455506200016f915050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200010d57805160ff19168380011785556200013d565b828001600101855582156200013d579182015b828111156200013d57825182559160200191906001019062000120565b506200014b9291506200014f565b5090565b6200016c91905b808211156200014b576000815560010162000156565b90565b612d2e806200017f6000396000f30060806040526004361061015e5763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416630591d3ab81146101635780630b15f744146101805780630b275288146101a75780630d28135e146101bc57806315b33f4c146101dc5780631865c57d1461028157806320a66358146102ba57806332cb6b16146102d257806335cb9678146102ea57806340f68b821461030e57806348df63241461037d5780634a79d50c1461039557806361f9e5971461041f5780636231a772146104405780636a1629421461047b578063798168ae146104905780638439d8ff146104b95780639d8edfca1461059f578063b3311086146105bf578063bc2d5b16146105d4578063bc840826146105ef578063bea2011714610604578063c2e112d21461062b578063d259994014610640578063e085f98014610667578063e7bee05d1461067f578063ee9c9024146106a8575b600080fd5b34801561016f57600080fd5b5061017e60ff600435166106c3565b005b34801561018c57600080fd5b5061019561078b565b60408051918252519081900360200190f35b3480156101b357600080fd5b50610195610792565b3480156101c857600080fd5b5061017e6004803560248101910135610798565b3480156101e857600080fd5b506101f4600435610a8e565b6040518083600381111561020457fe5b60ff16815260200180602001828103825283818151815260200191508051906020019080838360005b8381101561024557818101518382015260200161022d565b50505050905090810190601f1680156102725780820380516001836020036101000a031916815260200191505b50935050505060405180910390f35b34801561028d57600080fd5b50610296610b4d565b604051808260028111156102a657fe5b60ff16815260200191505060405180910390f35b3480156102c657600080fd5b50610195600435610b5d565b3480156102de57600080fd5b50610195600435610bdf565b3480156102f657600080fd5b5061017e600480359060248035908101910135610c76565b34801561031a57600080fd5b50610329600435602435610e1c565b6040518083600160a060020a0316600160a060020a0316815260200180602001828103825283818151815260200191508051906020019080838360008381101561024557818101518382015260200161022d565b34801561038957600080fd5b506101f46004356110e8565b3480156103a157600080fd5b506103aa61122d565b6040805160208082528351818301528351919283929083019185019080838360005b838110156103e45781810151838201526020016103cc565b50505050905090810190601f1680156104115780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561042b57600080fd5b5061017e600160a060020a03600435166112c0565b34801561044c57600080fd5b506104586004356112ef565b60408051600160a060020a03909316835290151560208301528051918290030190f35b34801561048757600080fd5b50610195611327565b34801561049c57600080fd5b506104a561133a565b604080519115158252519081900360200190f35b3480156104c557600080fd5b506104d1600435611351565b604051808460038111156104e157fe5b60ff1681526020018060200180602001838103835285818151815260200191508051906020019080838360005b8381101561052657818101518382015260200161050e565b50505050905090810190601f1680156105535780820380516001836020036101000a031916815260200191505b508381038252845181528451602091820191808701910280838360005b83811015610588578181015183820152602001610570565b505050509050019550505050505060405180910390f35b3480156105ab57600080fd5b5061017e6004803560248101910135611522565b3480156105cb57600080fd5b50610195611b04565b3480156105e057600080fd5b506103aa600435602435611b0a565b3480156105fb57600080fd5b50610195611ccc565b34801561061057600080fd5b5061017e6004803560ff169060248035908101910135611cd2565b34801561063757600080fd5b50610195611eae565b34801561064c57600080fd5b5061017e6004803560ff169060248035908101910135611eb4565b34801561067357600080fd5b506101f4600435612186565b34801561068b57600080fd5b5061017e6004803590602480359081019101356044351515612210565b3480156106b457600080fd5b506103aa60043560243561236a565b60015433600160a060020a0390811691161461074f576040805160e560020a62461bcd02815260206004820152602c60248201527f4f6e6c792063726561746f72206f66207375727665792063616e20757064617460448201527f6520697473207374617465210000000000000000000000000000000000000000606482015290519081900360840190fd5b6000805482919078ff000000000000000000000000000000000000000000000000191660c060020a83600281111561078357fe5b021790555050565b6005545b90565b60095490565b60008080600654811015156107e5576040805160e560020a62461bcd0281526020600482015260156024820152600080516020612ca3833981519152604482015290519081900360640190fd5b816006818154811015156107f557fe5b60009182526020909120600260039092020101541561085e576040805160e560020a62461bcd02815260206004820152601d60248201527f5175657374696f6e20697320616c726561647920616e73776572656421000000604482015290519081900360640190fd5b60005460019060c060020a900460ff16600281111561087957fe5b81600281111561088557fe5b146108c8576040805160e560020a62461bcd0281526020600482015260156024820152600080516020612cc3833981519152604482015290519081900360640190fd5b6108d133612479565b600654600980549297509091879081106108e757fe5b600091825260209091206001600290920201015410610976576040805160e560020a62461bcd02815260206004820152603260248201527f416c6c20616e73776572732066726f6d20746869732073656e6465722061726560448201527f20616c7265616479207265636569766564210000000000000000000000000000606482015290519081900360840190fd5b600980548690811061098457fe5b60009182526020822060016002909202010154945060068054869081106109a757fe5b600091825260209091206003918202015460ff16908111156109c557fe5b14610a40576040805160e560020a62461bcd02815260206004820152602360248201527f546172676574207175657374696f6e2068617320696e636f727265637420747960448201527f7065210000000000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b6009805486908110610a4e57fe5b600091825260208083206001600290930201820180549283018082559084529220610a7b91018989612a7f565b5050610a85612740565b50505050505050565b6005805482908110610a9c57fe5b600091825260209182902060049091020180546001808301805460408051601f600260001996851615610100029690960190931694909404918201879004870284018701905280835260ff9093169550929390929190830182828015610b435780601f10610b1857610100808354040283529160200191610b43565b820191906000526020600020905b815481529060010190602001808311610b2657829003601f168201915b5050505050905082565b60005460c060020a900460ff1690565b60008160008110158015610b72575060065481105b1515610bb6576040805160e560020a62461bcd0281526020600482015260156024820152600080516020612ca3833981519152604482015290519081900360640190fd5b6006805484908110610bc457fe5b60009182526020909120600260039092020101549392505050565b60008160008110158015610bf557506005548111155b1515610c4d576040805160e560020a62461bcd0281526020600482015260276024820152600080516020612ce38339815191526044820152600080516020612c83833981519152606482015290519081900360840190fd5b6005805484908110610c5b57fe5b60009182526020909120600260049092020101549392505050565b8260008110158015610c89575060065481105b1515610ccd576040805160e560020a62461bcd0281526020600482015260156024820152600080516020612ca3833981519152604482015290519081900360640190fd5b6000805460c060020a900460ff166002811115610ce657fe5b816002811115610cf257fe5b14610d35576040805160e560020a62461bcd0281526020600482015260156024820152600080516020612cc3833981519152604482015290519081900360640190fd5b610d60600686815481101515610d4757fe5b600091825260209091206003909102015460ff166127b8565b1515610ddc576040805160e560020a62461bcd02815260206004820152602c60248201527f556e61626c6520746f2061646420706f696e747320666f7220616e737765722060448201527f6f66207468697320747970650000000000000000000000000000000000000000606482015290519081900360840190fd5b6006805486908110610dea57fe5b6000918252602080832060026003909302019190910180546001810180835591845291909220610a8591018686612a7f565b600060608360008110158015610e33575060065481105b1515610e77576040805160e560020a62461bcd0281526020600482015260156024820152600080516020612ca3833981519152604482015290519081900360640190fd5b8360008110158015610e8b57506009548111155b1515610ee1576040805160e560020a62461bcd02815260206004820152601b60248201527f526573706f6e64656e7420646f6573206e6f7420657869737473210000000000604482015290519081900360640190fd5b60005460019060c060020a900460ff166002811115610efc57fe5b816002811115610f0857fe5b14610f4b576040805160e560020a62461bcd0281526020600482015260156024820152600080516020612cc3833981519152604482015290519081900360640190fd5b86600987815481101515610f5b57fe5b600091825260209091206001600290920201015411610fea576040805160e560020a62461bcd02815260206004820152602d60248201527f526573706f6e64656e7420646964206e6f7420616e73776572656420746f207460448201527f686174207175657374696f6e2100000000000000000000000000000000000000606482015290519081900360840190fd5b6009805487908110610ff857fe5b600091825260209091206002909102015460098054600160a060020a03909216918890811061102357fe5b90600052602060002090600202016001018881548110151561104157fe5b600091825260209182902001805460408051601f6002600019610100600187161502019094169390930492830185900485028101850190915281815291928391908301828280156110d35780601f106110a8576101008083540402835291602001916110d3565b820191906000526020600020905b8154815290600101906020018083116110b657829003601f168201915b50505050509050945094505050509250929050565b6000606082600081101580156110ff575060065481105b1515611143576040805160e560020a62461bcd0281526020600482015260156024820152600080516020612ca3833981519152604482015290519081900360640190fd5b600680548590811061115157fe5b60009182526020909120600390910201546006805460ff909216918690811061117657fe5b9060005260206000209060030201600101808054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561121c5780601f106111f15761010080835404028352916020019161121c565b820191906000526020600020905b8154815290600101906020018083116111ff57829003601f168201915b505050505090509250925050915091565b60028054604080516020601f60001961010060018716150201909416859004938401819004810282018101909252828152606093909290918301828280156112b65780601f1061128b576101008083540402835291602001916112b6565b820191906000526020600020905b81548152906001019060200180831161129957829003601f168201915b5050505050905090565b6000805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0392909216919091179055565b60098054829081106112fd57fe5b6000918252602090912060029091020154600160a060020a038116915060a060020a900460ff1682565b60005460a060020a900463ffffffff1690565b60095460005460a060020a900463ffffffff161190565b6000606080836000811015801561136a57506005548111155b15156113c2576040805160e560020a62461bcd0281526020600482015260276024820152600080516020612ce38339815191526044820152600080516020612c83833981519152606482015290519081900360840190fd5b60058054869081106113d057fe5b60009182526020909120600490910201546005805460ff90921691879081106113f557fe5b906000526020600020906004020160010160058781548110151561141557fe5b9060005260206000209060040201600301818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156114bb5780601f10611490576101008083540402835291602001916114bb565b820191906000526020600020905b81548152906001019060200180831161149e57829003601f168201915b505050505091508080548060200260200160405190810160405280929190818152602001828054801561150d57602002820191906000526020600020905b8154815260200190600101908083116114f9575b50505050509050935093509350509193909250565b600080808060065481101515611570576040805160e560020a62461bcd0281526020600482015260156024820152600080516020612ca3833981519152604482015290519081900360640190fd5b8260068181548110151561158057fe5b6000918252602090912060026003909202010154156115e9576040805160e560020a62461bcd02815260206004820152601d60248201527f5175657374696f6e20697320616c726561647920616e73776572656421000000604482015290519081900360640190fd5b60005460019060c060020a900460ff16600281111561160457fe5b81600281111561161057fe5b14611653576040805160e560020a62461bcd0281526020600482015260156024820152600080516020612cc3833981519152604482015290519081900360640190fd5b61165c33612479565b6006546009805492985090918890811061167257fe5b600091825260209091206001600290920201015410611701576040805160e560020a62461bcd02815260206004820152603260248201527f416c6c20616e73776572732066726f6d20746869732073656e6465722061726560448201527f20616c7265616479207265636569766564210000000000000000000000000000606482015290519081900360840190fd5b600980548790811061170f57fe5b600091825260208220600160029092020101549550600680548790811061173257fe5b600091825260209091206003918202015460ff169081111561175057fe5b14156117cc576040805160e560020a62461bcd02815260206004820152602560248201527f546172676574207175657374696f6e2063616e6e6f742068617665207465787460448201527f2074797065000000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b600160068054879081106117dc57fe5b600091825260209091206003918202015460ff16908111156117fa57fe5b1415806118075750600187145b1515611883576040805160e560020a62461bcd02815260206004820152602960248201527f4f6e6c79206f6e65206e756d6265722073686f756c642062652073656e74206160448201527f7320616e73776572210000000000000000000000000000000000000000000000606482015290519081900360840190fd5b6003600680548790811061189357fe5b600091825260209091206003918202015460ff16908111156118b157fe5b1415806118ff575060066009878154811015156118ca57fe5b6000918252602090912060016002909202010154815481106118e857fe5b600091825260209091206002600390920201015487145b151561197b576040805160e560020a62461bcd02815260206004820152603660248201527f536f72742d74797065207175657374696f6e206d75737420726563656976652060448201527f6172726179206f6620616c6c2076617269616e74732100000000000000000000606482015290519081900360840190fd5b600980548790811061198957fe5b9060005260206000209060020201600101888890506040519080825280601f01601f1916602001820160405280156119cb578160200160208202803883390190505b508154600181018084556000938452602093849020835191946119f49491909301920190612afd565b5050600093505b86841015611af257878785818110611a0f57fe5b9050602002013560ff167f010000000000000000000000000000000000000000000000000000000000000002600987815481101515611a4a57fe5b906000526020600020906002020160010186815481101515611a6857fe5b90600052602060002001858154600181600116156101000203166002900481101515611a9057fe5b815460011615611aaf5790600052602060002090602091828204019190065b601f036101000a81548160ff021916907f0100000000000000000000000000000000000000000000000000000000000000840402179055508360010193506119fb565b611afa612740565b5050505050505050565b60035490565b60608260008110158015611b2057506005548111155b1515611b78576040805160e560020a62461bcd0281526020600482015260276024820152600080516020612ce38339815191526044820152600080516020612c83833981519152606482015290519081900360840190fd5b838360008110158015611bac57506005805483908110611b9457fe5b90600052602060002090600402016002018054905081105b1515611c02576040805160e560020a62461bcd02815260206004820152601d60248201527f5175657374696f6e20706f696e7420646f6573206e6f74206578697374000000604482015290519081900360640190fd5b6005805487908110611c1057fe5b906000526020600020906004020160020185815481101515611c2e57fe5b600091825260209182902001805460408051601f6002600019610100600187161502019094169390930492830185900485028101850190915281815292830182828015611cbc5780601f10611c9157610100808354040283529160200191611cbc565b820191906000526020600020905b815481529060010190602001808311611c9f57829003601f168201915b5050505050935050505092915050565b60045490565b6000805460c060020a900460ff166002811115611ceb57fe5b816002811115611cf757fe5b14611d3a576040805160e560020a62461bcd0281526020600482015260156024820152600080516020612cc3833981519152604482015290519081900360640190fd5b811515611db7576040805160e560020a62461bcd02815260206004820152602160248201527f5175657374696f6e2773207469746c652063616e6e6f7420626520656d70747960448201527f2100000000000000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b6006606060405190810160405280866003811115611dd157fe5b815260200185858080601f0160208091040260200160405190810160405280939291908181526020018383808284375050509284525050604080516000808252602082810190935291909301929150611e3a565b6060815260200190600190039081611e255790505b509052815460018181018085556000948552602090942083516003938402909101805490939192849260ff19909216918490811115611e7557fe5b02179055506020828101518051611e929260018501920190612afd565b5060408201518051611afa916002840191602090910190612b6b565b60065490565b826001816003811115611ec357fe5b1480611eda57506002816003811115611ed857fe5b145b1515611f7c576040805160e560020a62461bcd02815260206004820152604760248201527f4f6e6c79204f6e652d66726f6d2d6d616e7920616e64204d616e792d66726f6d60448201527f2d6d616e79207175657374696f6e732063616e2062652066696c74657220717560648201527f657374696f6e7300000000000000000000000000000000000000000000000000608482015290519081900360a40190fd5b6000805460c060020a900460ff166002811115611f9557fe5b816002811115611fa157fe5b14611fe4576040805160e560020a62461bcd0281526020600482015260156024820152600080516020612cc3833981519152604482015290519081900360640190fd5b82151561203b576040805160e560020a62461bcd02815260206004820152601860248201527f5175657374696f6e2063616e6e6f7420626520656d7074790000000000000000604482015290519081900360640190fd5b600560806040519081016040528087600381111561205557fe5b815260200186868080601f01602080910402602001604051908101604052809392919081815260200183838082843750505092845250506040805160008082526020828101909352919093019291506120be565b60608152602001906001900390816120a95790505b50815260200160006040519080825280602002602001820160405280156120ef578160200160208202803883390190505b509052815460018181018085556000948552602090942083516004909302018054909291839160ff19169083600381111561212657fe5b021790555060208281015180516121439260018501920190612afd565b506040820151805161215f916002840191602090910190612b6b565b506060820151805161217b916003840191602090910190612bc4565b505050505050505050565b600680548290811061219457fe5b600091825260209182902060039091020180546001808301805460408051601f600260001996851615610100029690960190931694909404918201879004870284018701905280835260ff9093169550929390929190830182828015610b435780601f10610b1857610100808354040283529160200191610b43565b836000811015801561222457506005548111155b151561227c576040805160e560020a62461bcd0281526020600482015260276024820152600080516020612ce38339815191526044820152600080516020612c83833981519152606482015290519081900360840190fd5b6000805460c060020a900460ff16600281111561229557fe5b8160028111156122a157fe5b146122e4576040805160e560020a62461bcd0281526020600482015260156024820152600080516020612cc3833981519152604482015290519081900360640190fd5b60058054879081106122f257fe5b600091825260208083206002600490930201919091018054600181018083559184529190922061232491018787612a7f565b5050821561236257600580548790811061233a57fe5b6000918252602080832060036004909302019190910180546001810182559083529120018690555b505050505050565b6060826000811015801561237f575060065481105b15156123c3576040805160e560020a62461bcd0281526020600482015260156024820152600080516020612ca3833981519152604482015290519081900360640190fd5b8383600081101580156123f7575060068054839081106123df57fe5b90600052602060002090600302016002018054905081105b151561244d576040805160e560020a62461bcd02815260206004820152601b60248201527f5175657374696f6e20706f696e742073686f756c642065786973740000000000604482015290519081900360640190fd5b600680548790811061245b57fe5b906000526020600020906003020160020185815481101515611c2e57fe5b600061248361133a565b15156124ff576040805160e560020a62461bcd02815260206004820152603260248201527f5265717569726564206e756d626572206f6620726573706f6e64656e7473206960448201527f7320616c72656164792072656163686564210000000000000000000000000000606482015290519081900360840190fd5b600754600160a060020a03161515612628576007805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03841617905560095415156126205760408051606081018252600160a060020a0384168152600060208083018290528351828152908101845260099383019161258c565b60608152602001906001900390816125775790505b5090528154600181810180855560009485526020948590208451600290940201805486860151151560a060020a0274ff000000000000000000000000000000000000000019600160a060020a0390961673ffffffffffffffffffffffffffffffffffffffff19909216919091179490941693909317835560408401518051919561261b93850192910190612b6b565b505050505b50600061273b565b50600160a060020a03811660009081526008602052604090205480151561273b575060098054600160a060020a03831660008181526008602090815260408083208590558051606081018252938452838201839052805183815291820181529394938301916126a7565b60608152602001906001900390816126925790505b5090528154600181810180855560009485526020948590208451600290940201805486860151151560a060020a0274ff000000000000000000000000000000000000000019600160a060020a0390961673ffffffffffffffffffffffffffffffffffffffff19909216919091179490941693909317835560408401518051919561273693850192910190612b6b565b505050505b919050565b6127486127fb565b60095460005460a060020a900463ffffffff16111580612773575060045430600160a060020a031631105b156127b6576000805478ff000000000000000000000000000000000000000000000000191678020000000000000000000000000000000000000000000000001790555b565b600060018260038111156127c857fe5b14806127df575060028260038111156127dd57fe5b145b806127f5575060038260038111156127f357fe5b145b92915050565b612803612974565b151561287f576040805160e560020a62461bcd02815260206004820152602660248201527f53656e646572206973206e6f742061207061727469636970616e74206f66207360448201527f7572766579210000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b6128876129b0565b1580156128d15750600654600160a060020a0333166000908152600860205260409020546009805490919081106128ba57fe5b906000526020600020906002020160010180549050145b156127b657600454604051600160a060020a0333169180156108fc02916000818181858888f1935050505015801561290d573d6000803e3d6000fd5b50600160a060020a0333166000908152600860205260409020546009805460019290811061293757fe5b60009182526020909120600290910201805491151560a060020a0274ff000000000000000000000000000000000000000019909216919091179055565b60075460009033600160a060020a03908116911614806129ab5750600160a060020a03331660009081526008602052604090205415155b905090565b60006129ba612974565b1515612a36576040805160e560020a62461bcd02815260206004820152602660248201527f53656e646572206973206e6f742061207061727469636970616e74206f66207360448201527f7572766579210000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b600160a060020a033316600090815260086020526040902054600980549091908110612a5e57fe5b600091825260209091206002909102015460a060020a900460ff1615919050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10612ac05782800160ff19823516178555612aed565b82800160010185558215612aed579182015b82811115612aed578235825591602001919060010190612ad2565b50612af9929150612bfe565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10612b3e57805160ff1916838001178555612aed565b82800160010185558215612aed579182015b82811115612aed578251825591602001919060010190612b50565b828054828255906000526020600020908101928215612bb8579160200282015b82811115612bb85782518051612ba8918491602090910190612afd565b5091602001919060010190612b8b565b50612af9929150612c18565b828054828255906000526020600020908101928215612aed5791602002820182811115612aed578251825591602001919060010190612b50565b61078f91905b80821115612af95760008155600101612c04565b61078f91905b80821115612af9576000612c328282612c3b565b50600101612c1e565b50805460018160011615610100020316600290046000825580601f10612c615750612c7f565b601f016020900490600052602060002090810190612c7f9190612bfe565b50560020657869737473000000000000000000000000000000000000000000000000005175657374696f6e2073686f756c642065786973740000000000000000000000496e76616c6964207375727665792073746174652100000000000000000000005175657374696f6e20776974682064617420696e64657820646f6573206e6f74a165627a7a72305820678db28bf0da874c53d8a409e9378169fec04ff1284cbebf58fce54e8a4353770029a165627a7a72305820946e919a4359ee3138aeb2a77d9d9f4941844f479e3f223b30335b08f17768dd0029";

    public static final String FUNC_CREATENEWSURVEY = "createNewSurvey";

    public static final String FUNC_GETBLOVOTEADDRESSES = "getBlovoteAddresses";

    public static final String FUNC_GETSURVEYSNUMBER = "getSurveysNumber";

    protected ContractBloGodImpl(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ContractBloGodImpl(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
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

    public static RemoteCall<ContractBloGodImpl> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ContractBloGodImpl.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<ContractBloGodImpl> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ContractBloGodImpl.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static ContractBloGodImpl load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ContractBloGodImpl(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static ContractBloGodImpl load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ContractBloGodImpl(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
