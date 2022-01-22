package io.coin.ccbc.infra.blockchain.contracts;

import com.klaytn.caver.Caver;
import com.klaytn.caver.crypto.KlayCredentials;
import com.klaytn.caver.methods.response.KlayTransactionReceipt;
import com.klaytn.caver.tx.SmartContract;
import com.klaytn.caver.tx.manager.TransactionManager;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated smart contract code.
 * <p><strong>Do not modify!</strong>
 */
public class IKlaySwapExchange extends SmartContract {

  public static final String FUNC_USERREWARDSUM = "userRewardSum";
  public static final String FUNC_USERLASTINDEX = "userLastIndex";
  public static final String FUNC_MININGINDEX = "miningIndex";
  public static final String FUNC_LASTMINED = "lastMined";
  public static final String FUNC_MINING = "mining";
  public static final String FUNC_BALANCEOF = "balanceOf";
  public static final String FUNC_VERSION = "version";
  public static final String FUNC_TOTALSUPPLY = "totalSupply";
  public static final String FUNC_GETCURRENTPOOL = "getCurrentPool";
  public static final String FUNC_ALLOWANCE = "allowance";
  public static final String FUNC_ESTIMATEPOS = "estimatePos";
  public static final String FUNC_ESTIMATENEG = "estimateNeg";
  public static final String FUNC_TOKENA = "tokenA";
  public static final String FUNC_TOKENB = "tokenB";
  public static final String FUNC_NAME = "name";
  public static final String FUNC_SYMBOL = "symbol";
  public static final String FUNC_DECIMALS = "decimals";
  public static final String FUNC_GETTREASURY = "getTreasury";
  public static final String FUNC_CLAIMREWARD = "claimReward";
  public static final String FUNC_APPROVE = "approve";
  public static final String FUNC_TRANSFERFROM = "transferFrom";
  public static final String FUNC_TRANSFER = "transfer";
  public static final String FUNC_ADDKLAYLIQUIDITY = "addKlayLiquidity";
  public static final String FUNC_ADDKLAYLIQUIDITYWITHLIMIT = "addKlayLiquidityWithLimit";
  public static final String FUNC_ADDKCTLIQUIDITY = "addKctLiquidity";
  public static final String FUNC_ADDKCTLIQUIDITYWITHLIMIT = "addKctLiquidityWithLimit";
  public static final String FUNC_REMOVELIQUIDITY = "removeLiquidity";
  public static final String FUNC_REMOVELIQUIDITYWITHLIMIT = "removeLiquidityWithLimit";
  protected static final HashMap<String, String> _addresses;
  private static final String BINARY = "0x";

  static {
    _addresses = new HashMap<String, String>();
  }

  protected IKlaySwapExchange(String contractAddress, Caver caver, KlayCredentials credentials,
      int chainId, ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, caver, credentials, chainId, contractGasProvider);
  }

  protected IKlaySwapExchange(String contractAddress, Caver caver,
      TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, caver, transactionManager, contractGasProvider);
  }

  public static IKlaySwapExchange load(String contractAddress, Caver caver,
      KlayCredentials credentials, int chainId, ContractGasProvider contractGasProvider) {
    return new IKlaySwapExchange(contractAddress, caver, credentials, chainId, contractGasProvider);
  }

  public static IKlaySwapExchange load(String contractAddress, Caver caver,
      TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
    return new IKlaySwapExchange(contractAddress, caver, transactionManager, contractGasProvider);
  }

  public static RemoteCall<IKlaySwapExchange> deploy(Caver caver, KlayCredentials credentials,
      int chainId, ContractGasProvider contractGasProvider) {
    return SmartContract
        .deployRemoteCall(IKlaySwapExchange.class, caver, credentials, chainId, contractGasProvider,
            BINARY, "");
  }

  public static RemoteCall<IKlaySwapExchange> deploy(Caver caver,
      TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
    return SmartContract
        .deployRemoteCall(IKlaySwapExchange.class, caver, transactionManager, contractGasProvider,
            BINARY, "");
  }

  public static String getPreviouslyDeployedAddress(String networkId) {
    return _addresses.get(networkId);
  }

  public RemoteCall<BigInteger> userRewardSum(String account) {
    final Function function = new Function(FUNC_USERREWARDSUM,
        Arrays.<Type>asList(new Address(account)),
        Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
        }));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }

  public RemoteCall<BigInteger> userLastIndex(String account) {
    final Function function = new Function(FUNC_USERLASTINDEX,
        Arrays.<Type>asList(new Address(account)),
        Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
        }));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }

  public RemoteCall<BigInteger> miningIndex() {
    final Function function = new Function(FUNC_MININGINDEX,
        Arrays.<Type>asList(),
        Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
        }));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }

  public RemoteCall<BigInteger> lastMined() {
    final Function function = new Function(FUNC_LASTMINED,
        Arrays.<Type>asList(),
        Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
        }));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }

  public RemoteCall<BigInteger> mining() {
    final Function function = new Function(FUNC_MINING,
        Arrays.<Type>asList(),
        Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
        }));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }

  public RemoteCall<BigInteger> balanceOf(String account) {
    final Function function = new Function(FUNC_BALANCEOF,
        Arrays.<Type>asList(new Address(account)),
        Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
        }));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }

  public RemoteCall<String> version() {
    final Function function = new Function(FUNC_VERSION,
        Arrays.<Type>asList(),
        Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
        }));
    return executeRemoteCallSingleValueReturn(function, String.class);
  }

  public RemoteCall<BigInteger> totalSupply() {
    final Function function = new Function(FUNC_TOTALSUPPLY,
        Arrays.<Type>asList(),
        Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
        }));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }

  public RemoteCall<Tuple2<BigInteger, BigInteger>> getCurrentPool() {
    final Function function = new Function(FUNC_GETCURRENTPOOL,
        Arrays.<Type>asList(),
        Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
        }, new TypeReference<Uint256>() {
        }));
    return new RemoteCall<Tuple2<BigInteger, BigInteger>>(
        new Callable<Tuple2<BigInteger, BigInteger>>() {
          @Override
          public Tuple2<BigInteger, BigInteger> call() throws Exception {
            List<Type> results = executeCallMultipleValueReturn(function);
            return new Tuple2<BigInteger, BigInteger>(
                (BigInteger) results.get(0).getValue(),
                (BigInteger) results.get(1).getValue());
          }
        });
  }

  public RemoteCall<BigInteger> allowance(String owner, String spender) {
    final Function function = new Function(FUNC_ALLOWANCE,
        Arrays.<Type>asList(new Address(owner),
            new Address(spender)),
        Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
        }));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }

  public RemoteCall<BigInteger> estimatePos(String token, BigInteger amount) {
    final Function function = new Function(FUNC_ESTIMATEPOS,
        Arrays.<Type>asList(new Address(token),
            new Uint256(amount)),
        Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
        }));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }

  public RemoteCall<BigInteger> estimateNeg(String token, BigInteger amount) {
    final Function function = new Function(FUNC_ESTIMATENEG,
        Arrays.<Type>asList(new Address(token),
            new Uint256(amount)),
        Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
        }));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }

  public RemoteCall<String> tokenA() {
    final Function function = new Function(FUNC_TOKENA,
        Arrays.<Type>asList(),
        Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
        }));
    return executeRemoteCallSingleValueReturn(function, String.class);
  }

  public RemoteCall<String> tokenB() {
    final Function function = new Function(FUNC_TOKENB,
        Arrays.<Type>asList(),
        Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
        }));
    return executeRemoteCallSingleValueReturn(function, String.class);
  }

  public RemoteCall<String> name() {
    final Function function = new Function(FUNC_NAME,
        Arrays.<Type>asList(),
        Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
        }));
    return executeRemoteCallSingleValueReturn(function, String.class);
  }

  public RemoteCall<String> symbol() {
    final Function function = new Function(FUNC_SYMBOL,
        Arrays.<Type>asList(),
        Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
        }));
    return executeRemoteCallSingleValueReturn(function, String.class);
  }

  public RemoteCall<BigInteger> decimals() {
    final Function function = new Function(FUNC_DECIMALS,
        Arrays.<Type>asList(),
        Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {
        }));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }

  public RemoteCall<String> getTreasury() {
    final Function function = new Function(FUNC_GETTREASURY,
        Arrays.<Type>asList(),
        Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
        }));
    return executeRemoteCallSingleValueReturn(function, String.class);
  }

  public RemoteCall<KlayTransactionReceipt.TransactionReceipt> claimReward() {
    final Function function = new Function(
        FUNC_CLAIMREWARD,
        Arrays.<Type>asList(),
        Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteCall<KlayTransactionReceipt.TransactionReceipt> approve(String _spender,
      BigInteger _value) {
    final Function function = new Function(
        FUNC_APPROVE,
        Arrays.<Type>asList(new Address(_spender),
            new Uint256(_value)),
        Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteCall<KlayTransactionReceipt.TransactionReceipt> transferFrom(String _from,
      String _to, BigInteger _value) {
    final Function function = new Function(
        FUNC_TRANSFERFROM,
        Arrays.<Type>asList(new Address(_from),
            new Address(_to),
            new Uint256(_value)),
        Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteCall<KlayTransactionReceipt.TransactionReceipt> transfer(String _to,
      BigInteger _value) {
    final Function function = new Function(
        FUNC_TRANSFER,
        Arrays.<Type>asList(new Address(_to),
            new Uint256(_value)),
        Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteCall<KlayTransactionReceipt.TransactionReceipt> addKlayLiquidity(BigInteger amount,
      BigInteger pebValue) {
    final Function function = new Function(
        FUNC_ADDKLAYLIQUIDITY,
        Arrays.<Type>asList(new Uint256(amount)),
        Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function, pebValue);
  }

  public RemoteCall<KlayTransactionReceipt.TransactionReceipt> addKlayLiquidityWithLimit(
      BigInteger amount, BigInteger minAmountA, BigInteger minAmountB, BigInteger pebValue) {
    final Function function = new Function(
        FUNC_ADDKLAYLIQUIDITYWITHLIMIT,
        Arrays.<Type>asList(new Uint256(amount),
            new Uint256(minAmountA),
            new Uint256(minAmountB)),
        Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function, pebValue);
  }

  public RemoteCall<KlayTransactionReceipt.TransactionReceipt> addKctLiquidity(BigInteger amountA,
      BigInteger amountB) {
    final Function function = new Function(
        FUNC_ADDKCTLIQUIDITY,
        Arrays.<Type>asList(new Uint256(amountA),
            new Uint256(amountB)),
        Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteCall<KlayTransactionReceipt.TransactionReceipt> addKctLiquidityWithLimit(
      BigInteger amountA, BigInteger amountB, BigInteger minAmountA, BigInteger minAmountB) {
    final Function function = new Function(
        FUNC_ADDKCTLIQUIDITYWITHLIMIT,
        Arrays.<Type>asList(new Uint256(amountA),
            new Uint256(amountB),
            new Uint256(minAmountA),
            new Uint256(minAmountB)),
        Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteCall<KlayTransactionReceipt.TransactionReceipt> removeLiquidity(BigInteger amount) {
    final Function function = new Function(
        FUNC_REMOVELIQUIDITY,
        Arrays.<Type>asList(new Uint256(amount)),
        Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteCall<KlayTransactionReceipt.TransactionReceipt> removeLiquidityWithLimit(
      BigInteger amount, BigInteger minAmountA, BigInteger minAmountB) {
    final Function function = new Function(
        FUNC_REMOVELIQUIDITYWITHLIMIT,
        Arrays.<Type>asList(new Uint256(amount),
            new Uint256(minAmountA),
            new Uint256(minAmountB)),
        Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  protected String getStaticDeployedAddress(String networkId) {
    return _addresses.get(networkId);
  }
}
