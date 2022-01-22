package io.coin.ccbc.kokoa;

import com.klaytn.caver.Caver;
import com.klaytn.caver.crypto.KlayCredentials;
import com.klaytn.caver.tx.SmartContract;
import com.klaytn.caver.tx.manager.TransactionManager;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated smart contract code.
 * <p><strong>Do not modify!</strong>
 */
public class CCBCKokoaViewer extends SmartContract {
  private static final String BINARY = "Bin file was not provided";

  public static final String FUNC_GETFARMTOKENSINFO = "getFarmTokensInfo";

  protected CCBCKokoaViewer(String contractAddress, Caver caver, KlayCredentials credentials, int chainId, ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, caver, credentials, chainId, contractGasProvider);
  }

  protected CCBCKokoaViewer(String contractAddress, Caver caver, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, caver, transactionManager, contractGasProvider);
  }

  public RemoteCall<Tuple4<List<String>, List<BigInteger>, List<String>, List<BigInteger>>> getFarmTokensInfo(List<String> _poolAddress, List<String> _farmAddress, String _targetAddress) {
    final Function function = new Function(FUNC_GETFARMTOKENSINFO,
        Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                org.web3j.abi.datatypes.Address.class,
                org.web3j.abi.Utils.typeMap(_poolAddress, org.web3j.abi.datatypes.Address.class)),
            new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                org.web3j.abi.datatypes.Address.class,
                org.web3j.abi.Utils.typeMap(_farmAddress, org.web3j.abi.datatypes.Address.class)),
            new org.web3j.abi.datatypes.Address(_targetAddress)),
        Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Address>>() {}, new TypeReference<DynamicArray<Uint256>>() {}));
    return new RemoteCall<Tuple4<List<String>, List<BigInteger>, List<String>, List<BigInteger>>>(
        new Callable<Tuple4<List<String>, List<BigInteger>, List<String>, List<BigInteger>>>() {
          @Override
          public Tuple4<List<String>, List<BigInteger>, List<String>, List<BigInteger>> call() throws Exception {
            List<Type> results = executeCallMultipleValueReturn(function);
            return new Tuple4<List<String>, List<BigInteger>, List<String>, List<BigInteger>>(
                convertToNative((List<Address>) results.get(0).getValue()),
                convertToNative((List<Uint256>) results.get(1).getValue()),
                convertToNative((List<Address>) results.get(2).getValue()),
                convertToNative((List<Uint256>) results.get(3).getValue()));
          }
        });
  }

  public static CCBCKokoaViewer load(String contractAddress, Caver caver, KlayCredentials credentials, int chainId, ContractGasProvider contractGasProvider) {
    return new CCBCKokoaViewer(contractAddress, caver, credentials, chainId, contractGasProvider);
  }

  public static CCBCKokoaViewer load(String contractAddress, Caver caver, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
    return new CCBCKokoaViewer(contractAddress, caver, transactionManager, contractGasProvider);
  }
}