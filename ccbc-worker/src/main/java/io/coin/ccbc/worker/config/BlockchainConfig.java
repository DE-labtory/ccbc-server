package io.coin.ccbc.worker.config;

import io.coin.ccbc.infra.blockchain.annotations.EnableBlockchainClient.EnableKlaytnClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableKlaytnClient // todo : blockchain으로 추상화 해서 klaytn, bsc등 받는 구조로 가야함
public class BlockchainConfig {

}
