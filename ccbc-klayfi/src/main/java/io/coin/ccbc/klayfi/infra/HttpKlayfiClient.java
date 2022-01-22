package io.coin.ccbc.klayfi.infra;

import com.klaytn.caver.Caver;
import com.klaytn.caver.methods.request.CallObject;
import com.klaytn.caver.methods.response.Bytes;
import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Amount;
import io.coin.ccbc.domain.exceptions.BlockchainClientException;
import io.coin.ccbc.klayfi.domain.Balance;
import io.coin.ccbc.klayfi.domain.KlayfiClient;
import io.coin.ccbc.klayfi.domain.Reward;
import io.coin.ccbc.klayfi.domain.StakingInfo;
import io.coin.ccbc.klayfi.domain.VaultBalance;
import io.coin.ccbc.klayfi.domain.VaultInfo;
import io.coin.ccbc.support.Converter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.DefaultBlockParameterName;

public class HttpKlayfiClient implements KlayfiClient {

  private final Address ccbcKlayfiViewerAddress;
  private final Caver caver;

  public HttpKlayfiClient(String nodeUrl, String klayfiViewerAddress) {
    this.caver = new Caver(nodeUrl);
    this.ccbcKlayfiViewerAddress = Address.of(klayfiViewerAddress);
  }

  @Override
  public StakingInfo getCorePoolInfo(Address stakingAddress, Address targetAddress) {
    final Function function = new Function(
        "getStakingInfo",
        Arrays.asList(
            new org.web3j.abi.datatypes.Address(stakingAddress.getValue()),
            new org.web3j.abi.datatypes.Address(targetAddress.getValue())
        ),
        Arrays.asList(
            new TypeReference<DynamicArray<org.web3j.abi.datatypes.Address>>() {
            },
            new TypeReference<DynamicArray<Uint256>>() {
            },
            new TypeReference<DynamicArray<Uint256>>() {
            }
        )
    );

    String encodedFunction = FunctionEncoder.encode(function);

    Bytes klayCall = null;
    try {
      klayCall = this.caver.rpc.klay.call(
          new CallObject(
              targetAddress.getValue(),
              this.ccbcKlayfiViewerAddress.getValue(),
              null,
              null,
              null,
              encodedFunction
          ),
          DefaultBlockParameterName.LATEST)
          .send();
    } catch (IOException e) {
      throw new BlockchainClientException("error while call earned: '%s'",
          e.getMessage());
    }

    String result = Converter.remove0x(klayCall.getResult());

    Address kfiAddress = Address.of(result.substring(24, 64));
    Amount stakedKfiAmount = Amount.of(new BigInteger(result.substring(64, 128), 16));
    Amount rewardedKfiAmount = Amount.of(new BigInteger(result.substring(152, 192), 16));
    return new StakingInfo(
        new Balance(
            kfiAddress,
            stakedKfiAmount
        ),
        new Balance(
            kfiAddress,
            rewardedKfiAmount
        )
    );
  }

  @Override
  public VaultInfo getCoreVaultInfo(Address vaultAddress, Address targetAddress) {
    final Function function = new Function(
        "getCoreVaultInfo",
        Arrays.asList(
            new org.web3j.abi.datatypes.Address(vaultAddress.getValue()),
            new org.web3j.abi.datatypes.Address(targetAddress.getValue())
        ),
        Arrays.asList(
            new TypeReference<DynamicArray<org.web3j.abi.datatypes.Address>>() {
            },
            new TypeReference<DynamicArray<Uint256>>() {
            },
            new TypeReference<DynamicArray<org.web3j.abi.datatypes.Address>>() {
            },
            new TypeReference<DynamicArray<Uint256>>() {
            },
            new TypeReference<DynamicArray<org.web3j.abi.datatypes.Address>>() {
            },
            new TypeReference<DynamicArray<Uint256>>() {
            }
        )
    );

    String encodedFunction = FunctionEncoder.encode(function);
    Bytes klayCall = null;
    try {
      klayCall = this.caver.rpc.klay.call(
          new CallObject(
              targetAddress.getValue(),
              this.ccbcKlayfiViewerAddress.getValue(),
              null,
              null,
              null,
              encodedFunction
          ),
          DefaultBlockParameterName.LATEST
      )
          .send();
    } catch (IOException e) {
      throw new BlockchainClientException("error while call getReward. '%s'",
          e.getMessage());
    }

    String result = Converter.remove0x(klayCall.getResult());
    Address kfiAddress = Address.of(result.substring(24, 64));
    Amount kfiRewardAmount = Amount.of(new BigInteger(result.substring(64, 128), 16));
    Address coin0Address = Address.of(result.substring(152, 192));
    Amount coin0Amount = Amount.of(new BigInteger(result.substring(192, 256), 16));
    Address coin1Address = Address.of(result.substring(280, 320));
    Amount coin1Amount = Amount.of(new BigInteger(result.substring(320, 384), 16));

    return new VaultInfo(
        Arrays.asList(
            new Reward(
                kfiAddress,
                kfiRewardAmount
            )
        ),
        new VaultBalance(
            new Balance(coin0Address, coin0Amount),
            new Balance(coin1Address, coin1Amount)
        )
    );
  }

  @Override
  public VaultInfo getVaultInfo(Address vaultAddress, Address targetAddress) {
    final Function function = new Function(
        "getRewardForRelatedTokens",
        Arrays.asList(
            new org.web3j.abi.datatypes.Address(vaultAddress.getValue()),
            new org.web3j.abi.datatypes.Address(targetAddress.getValue())
        ),
        Arrays.asList(
            new TypeReference<DynamicArray<org.web3j.abi.datatypes.Address>>() {
            },
            new TypeReference<DynamicArray<Uint256>>() {
            },
            new TypeReference<DynamicArray<org.web3j.abi.datatypes.Address>>() {
            },
            new TypeReference<DynamicArray<Uint256>>() {
            },
            new TypeReference<DynamicArray<org.web3j.abi.datatypes.Address>>() {
            },
            new TypeReference<DynamicArray<Uint256>>() {
            }
        )
    );

    String encodedFunction = FunctionEncoder.encode(function);
    Bytes klayCall = null;
    try {
      klayCall = this.caver.rpc.klay.call(
          new CallObject(
              targetAddress.getValue(),
              this.ccbcKlayfiViewerAddress.getValue(),
              null,
              null,
              null,
              encodedFunction
          ),
          DefaultBlockParameterName.LATEST
      )
          .send();
    } catch (IOException e) {
      throw new BlockchainClientException("error while call getReward. '%s'",
          e.getMessage());
    }

    String result = Converter.remove0x(klayCall.getResult());

    Address kfiAddress = Address.of(result.substring(24, 64));
    Amount kfiRewardAmount = Amount.of(new BigInteger(result.substring(64, 128), 16));
    Address coin1Address = Address.of(result.substring(152, 192));
    Amount coin1RewardAmount = Amount.of(new BigInteger(result.substring(192, 256), 16));
    Address coin0Address = Address.of(result.substring(280, 320));
    Amount coin0RewardAmount = Amount.of(new BigInteger(result.substring(320, 384), 16));

    VaultBalance vaultBalance = this.getVaultBalance(vaultAddress, targetAddress);

    return new VaultInfo(
        Arrays.asList(
            new Reward(
                kfiAddress,
                kfiRewardAmount
            ),
            new Reward(
                coin0Address,
                coin0RewardAmount
            ),
            new Reward(
                coin1Address,
                coin1RewardAmount
            )
        ),
        vaultBalance
    );
  }

  private VaultBalance getVaultBalance(Address vaultAddress, Address targetAddress) {
    final Function function = new Function(
        "getAmountForRelatedTokens",
        Arrays.asList(
            new org.web3j.abi.datatypes.Address(vaultAddress.getValue()),
            new org.web3j.abi.datatypes.Address(targetAddress.getValue())
        ),
        Arrays.asList(
            new TypeReference<DynamicArray<org.web3j.abi.datatypes.Address>>() {
            },
            new TypeReference<DynamicArray<Uint256>>() {
            },
            new TypeReference<DynamicArray<org.web3j.abi.datatypes.Address>>() {
            },
            new TypeReference<DynamicArray<Uint256>>() {
            }
        )
    );

    String encodedFunction = FunctionEncoder.encode(function);
    Bytes klayCall = null;
    try {
      klayCall = this.caver.rpc.klay.call(
          new CallObject(
              targetAddress.getValue(),
              this.ccbcKlayfiViewerAddress.getValue(),
              null,
              null,
              null,
              encodedFunction
          ),
          DefaultBlockParameterName.LATEST
      )
          .send();
    } catch (IOException e) {
      throw new BlockchainClientException("error while call getReward. '%s'",
          e.getMessage());
    }

    String result = Converter.remove0x(klayCall.getResult());
    Address coin0Address = Address.of(result.substring(24, 64));
    Amount coin0Amount = Amount.of(new BigInteger(result.substring(64, 128), 16));
    Address coin1Address = Address.of(result.substring(152, 192));
    Amount coin1Amount = Amount.of(new BigInteger(result.substring(192, 256), 16));

    return new VaultBalance(
        new Balance(coin0Address, coin0Amount),
        new Balance(coin1Address, coin1Amount)
    );
  }
}
