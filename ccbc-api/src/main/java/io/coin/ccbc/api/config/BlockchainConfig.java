package io.coin.ccbc.api.config;

import io.coin.ccbc.infra.blockchain.annotations.EnableBlockchainClient.EnableKlaytnClient;
import org.springframework.context.annotation.Configuration;

@EnableKlaytnClient // todo : blockchain으로 추상화 해서 klaytn, bsc등 받는 구조로 가야함
@Configuration
public class BlockchainConfig {

}
