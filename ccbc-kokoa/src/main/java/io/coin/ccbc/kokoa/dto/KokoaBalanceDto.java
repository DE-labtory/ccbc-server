package io.coin.ccbc.kokoa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.coin.ccbc.kokoa.DecimalStringToBigIntegerDeserializer;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KokoaBalanceDto {

  private Tokens tokens;
  private KokoaClaimableRewards kokoaClaimableRewards;
  @JsonProperty("KSDClaimableRewards")
  private KSDClaimableRewards ksdClaimableRewards;
  private List<CollateralInfo> collateralInfos;

  public BigInteger getBorrowReward() {
    return this.getKokoaClaimableRewards().getIssuedKsdPool().getBalance();
  }

  public BigInteger getStakingKokoaReward() {
    return this.getKokoaClaimableRewards().getSKokoa().getBalance();
  }

  public BigInteger getStakingKsdReward() {
    return this.getKsdClaimableRewards().getSKokoa().getBalance();
  }

  public BigInteger getStakedKokoaBalance() {
    return this.getTokens().getSKokoa().getBalance();
  }

  public BigInteger getDepositedKSDBalance() {
    return this.tokens.dKsd.getBalance();
  }

  // 순서 주의
  public List<String> getFarmAddresses() {
    return Arrays.asList(
        this.tokens.getKlayKokoaPool().getAddress(),
        this.tokens.getKlayKsdPool().getAddress(),
        this.tokens.getKsdKdaiPool().getAddress(),
        this.tokens.getKsdKusdtPool().getAddress(),
        this.tokens.getAKlayKokoaPool().getAddress(),
        this.tokens.getAKlayKsdPool().getAddress(),
        this.tokens.getKWbtcKsdPool().getAddress(),
        this.tokens.getKEthKsdPool().getAddress(),
        this.tokens.getKsdKusdcPool().getAddress(),
        this.tokens.getKspKsdPool().getAddress(),
        this.tokens.getKXrpKsdPool().getAddress()
    );
  }

  // 순서 주의
  public List<String> getLpAddresses() {
    return Arrays.asList(
        this.tokens.getKlayKokoaLp().getAddress(),
        this.tokens.getKlayKsdLp().getAddress(),
        this.tokens.getKsdKdaiLp().getAddress(),
        this.tokens.getKsdKusdtLp().getAddress(),
        this.tokens.getAKlayKokoaLp().getAddress(),
        this.tokens.getAKlayKsdLp().getAddress(),
        this.tokens.getKWbtcKsdLp().getAddress(),
        this.tokens.getKEthKsdLp().getAddress(),
        this.tokens.getKsdKusdcLp().getAddress(),
        this.tokens.getKspKsdLp().getAddress(),
        this.tokens.getKXrpKsdLp().getAddress()
    );
  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Tokens {
    @JsonProperty("KLAY")
    private Token klay;
    @JsonProperty("AKLAY")
    private Token aKlay;
    @JsonProperty("KSD")
    private Token ksd;
    @JsonProperty("dKSD")
    private Token dKsd; // deposited KSD, EARN
    @JsonProperty("KOKOA")
    private Token kokoa;
    @JsonProperty("sKOKOA")
    private Token sKokoa; // staked KOKOA, GOVERN
    @JsonProperty("AKLAYKOKOALP")
    private Token aKlayKokoaLp;
    @JsonProperty("AKLAYKSDLP")
    private Token aKlayKsdLp;
    @JsonProperty("KETHKSDLP")
    private Token kEthKsdLp;
    @JsonProperty("KLAYKOKOALP")
    private Token klayKokoaLp;
    @JsonProperty("KLAYKSDLP")
    private Token klayKsdLp;
    @JsonProperty("KSDKDAILP")
    private Token ksdKdaiLp;
    @JsonProperty("KSDKUSDCLP")
    private Token ksdKusdcLp;
    @JsonProperty("KSDKUSDTLP")
    private Token ksdKusdtLp;
    @JsonProperty("KSPKSDLP")
    private Token kspKsdLp;
    @JsonProperty("KWBTCKSDLP")
    private Token kWbtcKsdLp;
    @JsonProperty("KXRPKSDLP")
    private Token kXrpKsdLp;
    @JsonProperty("AKLAYKOKOAPool")
    private Token aKlayKokoaPool;
    @JsonProperty("AKLAYKSDPool")
    private Token aKlayKsdPool;
    @JsonProperty("KETHKSDPool")
    private Token kEthKsdPool;
    @JsonProperty("KLAYKOKOAPool")
    private Token klayKokoaPool;
    @JsonProperty("KLAYKSDPool")
    private Token klayKsdPool;
    @JsonProperty("KSDKDAIPool")
    private Token ksdKdaiPool;
    @JsonProperty("KSDKUSDCPool")
    private Token ksdKusdcPool;
    @JsonProperty("KSDKUSDTPool")
    private Token ksdKusdtPool;
    @JsonProperty("KSPKSDPool")
    private Token kspKsdPool;
    @JsonProperty("KWBTCKSDPool")
    private Token kWbtcKsdPool;
    @JsonProperty("KXRPKSDPool")
    private Token kXrpKsdPool;
    @JsonProperty("issuedKSDPool")
    private Token issuedKsdPool;
  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class KokoaClaimableRewards {
    @JsonProperty("issuedKSDPool")
    private Token issuedKsdPool;
    @JsonProperty("KLAYKOKOAPool")
    private Token klayKokoaPool;
    @JsonProperty("KLAYKSDPool")
    private Token klayKsdPool;
    @JsonProperty("KSDKDAIPool")
    private Token ksdKdaiPool;
    @JsonProperty("KSDKUSDTPool")
    private Token ksdKusdtPool;
    @JsonProperty("AKLAYKOKOAPool")
    private Token aKlayKokoaPool;
    @JsonProperty("AKLAYKSDPool")
    private Token aKlayKsdPool;
    @JsonProperty("KWBTCKSDPool")
    private Token kWbtcKsdPool;
    @JsonProperty("KETHKSDPool")
    private Token kEthKsdPool;
    @JsonProperty("KSDKUSDCPool")
    private Token ksdKusdcPool;
    @JsonProperty("KSPKSDPool")
    private Token kspKsdPool;
    @JsonProperty("KXRPKSDPool")
    private Token kXrpKsdPool;
    @JsonProperty("sKOKOA")
    private Token sKokoa;
  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class KSDClaimableRewards {
    @JsonProperty("sKOKOA")
    private Token sKokoa;
  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Token {
    private String name;
    private String address;
    @JsonDeserialize(using = DecimalStringToBigIntegerDeserializer.class)
    private BigInteger balance;
  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class CollateralInfo {
    private String collateralType; // KLAY, AKLAY
    @JsonDeserialize(using = DecimalStringToBigIntegerDeserializer.class)
    private BigInteger lockedCollateral;
    @JsonDeserialize(using = DecimalStringToBigIntegerDeserializer.class)
    private BigInteger loan;
  }
}
