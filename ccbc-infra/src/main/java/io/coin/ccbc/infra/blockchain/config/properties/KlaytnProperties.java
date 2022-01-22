package io.coin.ccbc.infra.blockchain.config.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KlaytnProperties {

  public static final String KLAYTN_PROPERTIES_PREFIX = "app.klaytn";

  private String nodeUrl;
  private int chainId;
  private String transactionAccountPk;
  private KlaytnContractProperties contracts;

  @Getter
  @Setter
  public static class KlaytnContractProperties {

    private String viewerAddress;
    private String klayswapViewerAddress;
    private String kokoaViewerAddress;
    private String definixViewerAddress;
    private String klayfiViewerAddress;
    private String helperAddress;
    private String swapByKlayswapImplAddress;
    private String swapByDefinixImplAddress;
  }

}
