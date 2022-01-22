package io.coin.ccbc.kokoa;

import io.coin.ccbc.domain.Address;
import io.coin.ccbc.kokoa.dto.KokoaBalanceDto;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.web3j.tuples.generated.Tuple4;

@Slf4j
public class HttpKoKoaProtocolClient implements KoKoaFinanceClient {

  private final RestTemplate restTemplate = new RestTemplate();
  private final CCBCKokoaViewer ccbcKokoaViewerContract;

  public HttpKoKoaProtocolClient(CCBCKokoaViewer ccbcKokoaViewerContract) {
    this.restTemplate.setUriTemplateHandler(
        new DefaultUriBuilderFactory("https://kokoa-mainnet.du.r.appspot.com"));
    this.ccbcKokoaViewerContract = ccbcKokoaViewerContract;
  }

  @Override
  public KokoaStatus getStatus() {
    return this.restTemplate.getForObject("/status", KokoaStatus.class);
  }

  @Override
  public KokoaBalance getBalance(Address address) {
    KokoaBalanceDto balanceDto = this.restTemplate
        .getForObject(String.format("/balance/%s", address.getValue()), KokoaBalanceDto.class);
    Tuple4<List<String>, List<BigInteger>, List<String>, List<BigInteger>> response = null;

    try {
      response = this.ccbcKokoaViewerContract.getFarmTokensInfo(
          balanceDto.getLpAddresses(),
          balanceDto.getFarmAddresses(),
          address.getValue()
      ).send();
    } catch (Exception e) {
      HttpKoKoaProtocolClient.log.error("CCBCKokoaViewer has some errors.");
    }

    return new KokoaBalance(
        balanceDto,
        Objects.isNull(response) ? null : response.component2(),
        Objects.isNull(response) ? null : response.component4()
    );
  }
}