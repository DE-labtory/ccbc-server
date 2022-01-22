package io.coin.ccbc.kai.infra;

import com.klaytn.caver.Caver;
import com.klaytn.caver.methods.request.CallObject;
import com.klaytn.caver.methods.response.Bytes;
import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Amount;
import io.coin.ccbc.domain.exceptions.BlockchainClientException;
import io.coin.ccbc.kai.KaiProtocol;
import io.coin.ccbc.kai.KaiProtocolClient;
import io.coin.ccbc.kai.Reward;
import io.coin.ccbc.support.Converter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.DefaultBlockParameterName;

public class HttpKaiProtocolClient implements KaiProtocolClient {

  private final Caver caver;
  private final KaiProtocolCrawler kaiProtocolCrawler;

  public HttpKaiProtocolClient(String url, KaiProtocolCrawler kaiProtocolCrawler) {
    this.caver = new Caver(url);
    this.kaiProtocolCrawler = kaiProtocolCrawler;
  }

  @Override
  public Amount getStakedBalance(Address address) {
    final Function function = new Function(
        "balanceOf",
        Arrays.asList(new org.web3j.abi.datatypes.Address(address.getValue())),
        Arrays.asList(new TypeReference<DynamicArray<Uint256>>() {
        })
    );

    String encodedFunction = FunctionEncoder.encode(function);

    Bytes klayCall;
    try {
      klayCall = caver.rpc.klay.call(
              new CallObject(
                  address.getValue(),
                  KaiProtocol.BOARDROOM_ADDRESS.getValue(),
                  null,
                  null,
                  null,
                  encodedFunction
              ),
              DefaultBlockParameterName.LATEST)
          .send();
    } catch (IOException e) {
      throw new BlockchainClientException("error while call getStakedBalance. '%s'",
          e.getMessage());
    }

    String value = klayCall.getResult();
    return Amount.of(new BigInteger(Converter.remove0x(value), 16));
  }

  @Override
  public Reward getReward(Address address) {
    final String functionSelector = "0x9e88ec10";
    Bytes klayCall;
    try {
      klayCall = caver.rpc.klay.call(
              new CallObject(
                  address.getValue(),
                  KaiProtocol.BOARDROOM_ADDRESS.getValue(),
                  null,
                  null,
                  null,
                  functionSelector + StringUtils.leftPad(Converter.remove0x(address.getValue()), 64,
                      "0")
              ),
              DefaultBlockParameterName.LATEST)
          .send();
    } catch (IOException e) {
      throw new BlockchainClientException("error while call getReward. '%s'",
          e.getMessage());
    }

    String value = klayCall.getResult();
    return Reward.fromCallResult(value);
  }

  @Override
  public double getApr() {
    return this.kaiProtocolCrawler.getApr();
  }

  @Override
  public double getTotalTvl() {
    return this.kaiProtocolCrawler.getTotalTvl();
  }

  @Override
  public double getTvl() {
    return this.kaiProtocolCrawler.getTvl();
  }

  public void close() {
    this.kaiProtocolCrawler.close();
  }
}
