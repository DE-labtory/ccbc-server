package io.coin.ccbc.kronos.infra;

import com.klaytn.caver.Caver;
import com.klaytn.caver.methods.request.CallObject;
import com.klaytn.caver.methods.response.Bytes;
import io.coin.ccbc.domain.Address;
import io.coin.ccbc.domain.Amount;
import io.coin.ccbc.domain.exceptions.BlockchainClientException;
import io.coin.ccbc.kronos.KronosClient;
import io.coin.ccbc.support.Converter;
import org.apache.commons.lang3.StringUtils;
import org.web3j.protocol.core.DefaultBlockParameterName;

import java.io.IOException;
import java.math.BigInteger;

public class HttpKronosClient implements KronosClient {

    private final KronosCrawler kronosCrawler;
    private final Caver caver;

    public HttpKronosClient(String url, KronosCrawler kronosCrawler) {
        this.caver = new Caver(url);
        this.kronosCrawler = kronosCrawler;
    }

    @Override
    public double getTotalTvl() {
        return kronosCrawler.getTotalTvl();
    }

    @Override
    public double getApy() {
        return kronosCrawler.getApy();
    }

    @Override
    public Amount getStakedBalance(Address address) {
        final String functionSelector = "0x70a08231";
        Bytes klayCall;
        try {
            klayCall = caver.rpc.klay.call(
                    new CallObject(
                            address.getValue(),
                            "0x6555F93f608980526B5cA79b3bE2d4EdadB5C562",
                            null,
                            null,
                            null,
                            functionSelector + StringUtils.leftPad(Converter.remove0x(address.getValue()), 64,
                                    "0")
                    ),
                    DefaultBlockParameterName.LATEST)
                    .send();
        } catch (IOException e) {
            throw new BlockchainClientException("error while call staked balance. '%s'",
                    e.getMessage());
        }

        String value = klayCall.getResult();
        return Amount.of(new BigInteger(Converter.remove0x(value), 16));
    }
}
