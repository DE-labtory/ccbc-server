package io.coin.ccbc.kronos;


import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Amount;
import io.coin.ccbc.domain.Asset;
import io.coin.ccbc.domain.Coin;
import io.coin.ccbc.domain.CoinRepository;
import io.coin.ccbc.domain.Commodity;
import io.coin.ccbc.domain.Defi;
import io.coin.ccbc.domain.DefiInformation;
import io.coin.ccbc.domain.InvestmentAsset;
import io.coin.ccbc.domain.Reward;
import io.coin.ccbc.domain.Value;

import java.util.Collections;
import java.util.List;

public class KronosDaoFinance implements Defi {
    public static String NAME = "Kronos";

    public static Address ADDRESS = Address.of(
            "0x39281362641Da798De3801B23BFBA19155B57f13");
    public static Address KRONOS_ADDRESS = Address.of("0xd676e57ca65b827feb112ad81ff738e7b6c1048d");
    public static Address USDT_ADDRESS = Address.of("0xcee8faf64bb97a73bb51e115aa89c17ffa8dd167");
    private final CoinRepository coinRepository;
    private final KronosClient kronosClient;

    public KronosDaoFinance(CoinRepository coinRepository, KronosClient kronosClient) {
        this.coinRepository = coinRepository;
        this.kronosClient = kronosClient;
    }

    @Override
    public List<Commodity> getAllCommodities() {
        return Collections.singletonList(
                this.getKronosCommodity()
        );
    }

    private Commodity getKronosCommodity() {
        Coin kronos = coinRepository.findByAddressOrElseThrow(KRONOS_ADDRESS);
        return new Commodity(
                "kronos staking",
                ADDRESS,
                this.kronosClient.getApy(),
                this.kronosClient.getTotalTvl() * this.coinRepository.findByAddressOrElseThrow(USDT_ADDRESS)
                        .getPrice().getValue(),
                List.of(kronos)
        );
    }

    @Override
    public List<InvestmentAsset> getAllInvestmentAssets(Address userAddress) {
        Coin kronos = coinRepository.findByAddressOrElseThrow(KRONOS_ADDRESS);
        Amount amount = this.kronosClient.getStakedBalance(userAddress);
        return Collections.singletonList(
                new InvestmentAsset(
                        this.getKronosCommodity(),
                        Value.zero(),
                        Value.zero(),
                        Collections.singletonList(new Asset(
                                kronos,
                                amount,
                                Value.of(
                                        amount,
                                        kronos.getPrice(),
                                        kronos.getDecimals()
                                )
                        ))
                )
        );
    }

    @Override
    public String getProtocolName() {
        return NAME;
    }

    @Override
    public DefiInformation getInformation() {
        return new DefiInformation(
                NAME,
                "https://storage.googleapis.com/ccbc-app-public-asset/icon/defi/krnos.png",
                "2D136E",
                this.kronosClient.getTotalTvl() * this.coinRepository.findByAddressOrElseThrow(USDT_ADDRESS)
                        .getPrice().getValue()
        );
    }

    @Override
    public List<Reward> getRewards(Address address) {
        // todo Should the staking reward of kronos be displayed separately?
        return Collections.emptyList();
    }
}
