package io.coin.ccbc.infra.blockchain.contracts;

import com.klaytn.caver.Caver;
import com.klaytn.caver.crypto.KlayCredentials;
import com.klaytn.caver.tx.SmartContract;
import com.klaytn.caver.tx.manager.TransactionManager;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated smart contract code.
 * <p><strong>Do not modify!</strong>
 */
public class ICCBCViewer extends SmartContract {

  public static final String FUNC_GETCOINBALANCES = "getCoinBalances";
  public static final String FUNC_GETKLAYSWAPPOOLLPBALANCES = "getKlaySwapPoolLpBalances";
  public static final String FUNC_GETPOOLINFOS = "getPoolInfos";
  public static final String FUNC_ESTIMATEEXACTIN = "estimateExactIn";
  public static final String FUNC_ESTIMATEEXACTOUT = "estimateExactOut";
  public static final String FUNC_CHECKAPPROVALS = "checkApprovals";
  public static final String FUNC_CHECKAPPROVAL = "checkApproval";
  protected static final HashMap<String, String> _addresses;
  private static final String BINARY = "0x";

  static {
    _addresses = new HashMap<String, String>();
  }

  protected ICCBCViewer(String contractAddress, Caver caver, KlayCredentials credentials,
      int chainId, ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, caver, credentials, chainId, contractGasProvider);
  }

  protected ICCBCViewer(String contractAddress, Caver caver, TransactionManager transactionManager,
      ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, caver, transactionManager, contractGasProvider);
  }

  public static ICCBCViewer load(String contractAddress, Caver caver, KlayCredentials credentials,
      int chainId, ContractGasProvider contractGasProvider) {
    return new ICCBCViewer(contractAddress, caver, credentials, chainId, contractGasProvider);
  }

  public static ICCBCViewer load(String contractAddress, Caver caver,
      TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
    return new ICCBCViewer(contractAddress, caver, transactionManager, contractGasProvider);
  }

  public static RemoteCall<ICCBCViewer> deploy(Caver caver, KlayCredentials credentials,
      int chainId, ContractGasProvider contractGasProvider) {
    return deployRemoteCall(ICCBCViewer.class, caver, credentials, chainId, contractGasProvider,
        BINARY, "");
  }

  public static RemoteCall<ICCBCViewer> deploy(Caver caver, TransactionManager transactionManager,
      ContractGasProvider contractGasProvider) {
    return deployRemoteCall(ICCBCViewer.class, caver, transactionManager, contractGasProvider,
        BINARY, "");
  }

  public static String getPreviouslyDeployedAddress(String networkId) {
    return _addresses.get(networkId);
  }

  public RemoteCall<Tuple2<List<String>, List<BigInteger>>> getCoinBalances(
      List<String> _coinAddresses, String _walletAddress) {
    final Function function = new Function(FUNC_GETCOINBALANCES,
        Arrays.<Type>asList(new DynamicArray<Address>(
                Address.class,
                org.web3j.abi.Utils.typeMap(_coinAddresses, Address.class)),
            new Address(_walletAddress)),
        Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {
        }, new TypeReference<DynamicArray<Uint256>>() {
        }));
    return new RemoteCall<Tuple2<List<String>, List<BigInteger>>>(
        new Callable<Tuple2<List<String>, List<BigInteger>>>() {
          @Override
          public Tuple2<List<String>, List<BigInteger>> call() throws Exception {
            List<Type> results = ICCBCViewer.this.executeCallMultipleValueReturn(function);
            return new Tuple2<List<String>, List<BigInteger>>(
                convertToNative((List<Address>) results.get(0).getValue()),
                convertToNative((List<Uint256>) results.get(1).getValue()));
          }
        });
  }

  public RemoteCall<Tuple6<List<String>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>>> getKlaySwapPoolLpBalances(
      List<String> _poolAddresses, String walletAddress) {
    final Function function = new Function(FUNC_GETKLAYSWAPPOOLLPBALANCES,
        Arrays.<Type>asList(new DynamicArray<Address>(
                Address.class,
                org.web3j.abi.Utils.typeMap(_poolAddresses, Address.class)),
            new Address(walletAddress)),
        Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {
        }, new TypeReference<DynamicArray<Uint256>>() {
        }, new TypeReference<DynamicArray<Uint256>>() {
        }, new TypeReference<DynamicArray<Uint256>>() {
        }, new TypeReference<DynamicArray<Uint256>>() {
        }, new TypeReference<DynamicArray<Uint256>>() {
        }));
    return new RemoteCall<Tuple6<List<String>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>>>(
        new Callable<Tuple6<List<String>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>>>() {
          @Override
          public Tuple6<List<String>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>> call()
              throws Exception {
            List<Type> results = ICCBCViewer.this.executeCallMultipleValueReturn(function);
            return new Tuple6<List<String>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>>(
                convertToNative((List<Address>) results.get(0).getValue()),
                convertToNative((List<Uint256>) results.get(1).getValue()),
                convertToNative((List<Uint256>) results.get(2).getValue()),
                convertToNative((List<Uint256>) results.get(3).getValue()),
                convertToNative((List<Uint256>) results.get(4).getValue()),
                convertToNative((List<Uint256>) results.get(5).getValue()));
          }
        });
  }

  public RemoteCall<Tuple5<List<String>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>>> getPoolInfos(
      List<String> _poolAddresses) {
    final Function function = new Function(FUNC_GETPOOLINFOS,
        Arrays.<Type>asList(new DynamicArray<Address>(
            Address.class,
            org.web3j.abi.Utils.typeMap(_poolAddresses, Address.class))),
        Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {
        }, new TypeReference<DynamicArray<Uint256>>() {
        }, new TypeReference<DynamicArray<Uint256>>() {
        }, new TypeReference<DynamicArray<Uint256>>() {
        }, new TypeReference<DynamicArray<Uint256>>() {
        }));
    return new RemoteCall<Tuple5<List<String>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>>>(
        new Callable<Tuple5<List<String>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>>>() {
          @Override
          public Tuple5<List<String>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>> call()
              throws Exception {
            List<Type> results = ICCBCViewer.this.executeCallMultipleValueReturn(function);
            return new Tuple5<List<String>, List<BigInteger>, List<BigInteger>, List<BigInteger>, List<BigInteger>>(
                convertToNative((List<Address>) results.get(0).getValue()),
                convertToNative((List<Uint256>) results.get(1).getValue()),
                convertToNative((List<Uint256>) results.get(2).getValue()),
                convertToNative((List<Uint256>) results.get(3).getValue()),
                convertToNative((List<Uint256>) results.get(4).getValue()));
          }
        });
  }

  public RemoteCall<Tuple2<BigInteger, BigInteger>> estimateExactIn(BigInteger _amountIn,
      List<String> _tokenAddresses, List<String> _ICCBCSwapAddresses) {
    final Function function = new Function(FUNC_ESTIMATEEXACTIN,
        Arrays.<Type>asList(new Uint256(_amountIn),
            new DynamicArray<Address>(
                Address.class,
                org.web3j.abi.Utils.typeMap(_tokenAddresses, Address.class)),
            new DynamicArray<Address>(
                Address.class,
                org.web3j.abi.Utils.typeMap(_ICCBCSwapAddresses, Address.class))),
        Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
        }, new TypeReference<Uint256>() {
        }));
    return new RemoteCall<Tuple2<BigInteger, BigInteger>>(
        new Callable<Tuple2<BigInteger, BigInteger>>() {
          @Override
          public Tuple2<BigInteger, BigInteger> call() throws Exception {
            List<Type> results = ICCBCViewer.this.executeCallMultipleValueReturn(function);
            return new Tuple2<BigInteger, BigInteger>(
                (BigInteger) results.get(0).getValue(),
                (BigInteger) results.get(1).getValue());
          }
        });
  }

  public RemoteCall<Tuple2<BigInteger, BigInteger>> estimateExactOut(BigInteger _amountOut,
      List<String> _tokenAddresses, List<String> _ICCBCSwapAddresses) {
    final Function function = new Function(FUNC_ESTIMATEEXACTOUT,
        Arrays.<Type>asList(new Uint256(_amountOut),
            new DynamicArray<Address>(
                Address.class,
                org.web3j.abi.Utils.typeMap(_tokenAddresses, Address.class)),
            new DynamicArray<Address>(
                Address.class,
                org.web3j.abi.Utils.typeMap(_ICCBCSwapAddresses, Address.class))),
        Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
        }, new TypeReference<Uint256>() {
        }));
    return new RemoteCall<Tuple2<BigInteger, BigInteger>>(
        new Callable<Tuple2<BigInteger, BigInteger>>() {
          @Override
          public Tuple2<BigInteger, BigInteger> call() throws Exception {
            List<Type> results = ICCBCViewer.this.executeCallMultipleValueReturn(function);
            return new Tuple2<BigInteger, BigInteger>(
                (BigInteger) results.get(0).getValue(),
                (BigInteger) results.get(1).getValue());
          }
        });
  }

  public RemoteCall<List> checkApprovals(String userAddress, String spenderAddress,
      List<String> kipAddresses) {
    final Function function = new Function(FUNC_CHECKAPPROVALS,
        Arrays.<Type>asList(new Address(userAddress),
            new Address(spenderAddress),
            new DynamicArray<Address>(
                Address.class,
                org.web3j.abi.Utils.typeMap(kipAddresses, Address.class))),
        Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bool>>() {
        }));
    return new RemoteCall<List>(
        new Callable<List>() {
          @Override
          @SuppressWarnings("unchecked")
          public List call() throws Exception {
            List<Type> result = (List<Type>) ICCBCViewer.this
                .executeCallSingleValueReturn(function, List.class);
            return convertToNative(result);
          }
        });
  }

  public RemoteCall<Boolean> checkApproval(String userAddress, String spenderAddress,
      String kipAddress) {
    final Function function = new Function(FUNC_CHECKAPPROVAL,
        Arrays.<Type>asList(new Address(userAddress),
            new Address(spenderAddress),
            new Address(kipAddress)),
        Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
        }));
    return this.executeRemoteCallSingleValueReturn(function, Boolean.class);
  }

  protected String getStaticDeployedAddress(String networkId) {
    return _addresses.get(networkId);
  }
}
