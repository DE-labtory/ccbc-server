package io.coin.ccbc.infra.blockchain.config;

import com.klaytn.caver.Caver;
import com.klaytn.caver.crypto.KlayCredentials;
import com.klaytn.caver.tx.gas.DefaultGasProvider;
import io.coin.ccbc.infra.blockchain.config.properties.KlaytnProperties;
import io.coin.ccbc.infra.blockchain.contracts.CCBCDefinixViewer;
import io.coin.ccbc.infra.blockchain.contracts.CCBCKlayswapViewer;
import io.coin.ccbc.kokoa.CCBCKokoaViewer;
import io.coin.ccbc.infra.blockchain.contracts.ICCBCViewer;
import io.coin.ccbc.infra.blockchain.klaytn.DefinixKlaytnClient;
import io.coin.ccbc.infra.blockchain.klaytn.KlayswapKlaytnClient;
import io.coin.ccbc.infra.blockchain.klaytn.KlaytnClient;
import io.coin.ccbc.kokoa.HttpKoKoaProtocolClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KlaytnConfig {

  @Bean
  @ConfigurationProperties(KlaytnProperties.KLAYTN_PROPERTIES_PREFIX)
  public KlaytnProperties klaytnProperies() {
    return new KlaytnProperties();
  }

  @Bean
  public KlayCredentials viewerKlayCredentials() {
    return KlayCredentials.create(
        "0x34dd269ddc4eef3671098e7dc2323eecab7d782eb6cc4e8d4ea2d5d8b595b81a" // 0 클레이 깡통계정
    );
  }

  @Bean
  public Caver klaytnCaver(KlaytnProperties klaytnProperties) {
    return Caver.build(klaytnProperties.getNodeUrl());
  }

  @Bean
  public KlayCredentials transactionalKlayCredentials(KlaytnProperties klaytnProperties) {
    return KlayCredentials.create(klaytnProperties.getTransactionAccountPk());
  }

  @Bean
  public ICCBCViewer ccbcViewerContract(
      KlaytnProperties klaytnProperties,
      Caver caver,
      @Qualifier("viewerKlayCredentials") KlayCredentials klayCredentials
  ) {
    return ICCBCViewer.load(
        klaytnProperties.getContracts().getViewerAddress(),
        caver,
        klayCredentials,
        klaytnProperties.getChainId(),
        new DefaultGasProvider()
    );
  }

  @Bean
  public CCBCKokoaViewer ccbcKokoaViewer(
      KlaytnProperties klaytnProperties,
      Caver caver,
      @Qualifier("viewerKlayCredentials") KlayCredentials klayCredentials
  ) {
    return CCBCKokoaViewer.load(
        klaytnProperties.getContracts().getKokoaViewerAddress(),
        caver,
        klayCredentials,
        klaytnProperties.getChainId(),
        new DefaultGasProvider()
    );
  }

  @Bean
  public CCBCKlayswapViewer ccbcKlayswapViewerContract(
      KlaytnProperties klaytnProperties,
      Caver caver,
      @Qualifier("viewerKlayCredentials") KlayCredentials klayCredentials
  ) {
    return CCBCKlayswapViewer.load(
        klaytnProperties.getContracts().getKlayswapViewerAddress(),
        caver,
        klayCredentials,
        klaytnProperties.getChainId(),
        new DefaultGasProvider()
    );
  }

  @Bean
  public CCBCDefinixViewer ccbcDefinixViewerContract(
      KlaytnProperties klaytnProperties,
      Caver caver,
      @Qualifier("viewerKlayCredentials") KlayCredentials klayCredentials
  ) {
    return CCBCDefinixViewer.load(
        klaytnProperties.getContracts().getDefinixViewerAddress(),
        caver,
        klayCredentials,
        klaytnProperties.getChainId(),
        new DefaultGasProvider()
    );
  }

  @Bean
  public KlaytnClient klaytnClient(
      Caver caver,
      ICCBCViewer ccbcViewerContract,
      KlaytnProperties klaytnProperties
  ) {
    return new KlaytnClient(
        caver,
        ccbcViewerContract,
        klaytnProperties.getContracts().getSwapByKlayswapImplAddress(),
        klaytnProperties.getContracts().getSwapByDefinixImplAddress()
    );
  }


  @Bean
  public KlayswapKlaytnClient klayswapKlaytnClient(
      CCBCKlayswapViewer ccbcKlayswapViewerContract
  ) {
    return new KlayswapKlaytnClient(
        ccbcKlayswapViewerContract
    );
  }

  @Bean
  public HttpKoKoaProtocolClient koKoaFinanceClient(CCBCKokoaViewer kokoaViewer) {
    return new HttpKoKoaProtocolClient(kokoaViewer);
  }

  @Bean
  public DefinixKlaytnClient definixKlaytnClient(
      CCBCDefinixViewer ccbcDefinixViewer
  ) {
    return new DefinixKlaytnClient(
        ccbcDefinixViewer
    );
  }

  @Bean
  @Qualifier("kip7ApproveAbi")
  public String kip7Abi() {
//    return this
//        .extractContentFromFile("/abi/KIP7-approve.json");
    return "";
  }

  private String extractContentFromFile(String path) {
    try {
      InputStream in = this.getClass().getResourceAsStream(path);
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      StringBuilder out = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        out.append(line);
      }
      return out.toString();
    } catch (IOException e) {
      throw new IllegalStateException("fail to read email html", e);
    }
  }
}
