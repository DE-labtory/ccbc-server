package io.coin.ccbc.kokoa;

import io.coin.ccbc.domain.Amount;
import io.coin.ccbc.kokoa.KokoaBalance.BorrowInfo.CollateralInfo;
import io.coin.ccbc.kokoa.KokoaBalance.EarnInfo.FarmInfo;
import io.coin.ccbc.kokoa.dto.KokoaBalanceDto;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KokoaBalance {

  private GovernInfo governInfo;
  private EarnInfo earnInfo;
  private BorrowInfo borrowInfo;
  private FarmInfo klayKokoaFarm;
  private FarmInfo klayKsdFarm;
  private FarmInfo ksdKdaiFarm;
  private FarmInfo ksdKusdtFarm;
  private FarmInfo aKlayKokoaFarm;
  private FarmInfo aKlayKsdFarm;
  private FarmInfo kWbtcKsdFarm;
  private FarmInfo kEthKsdFarm;
  private FarmInfo ksdKusdcFarm;
  private FarmInfo kspKsdFarm;
  private FarmInfo kXrpKsdFarm;

  public KokoaBalance(
      KokoaBalanceDto kokoaBalanceDto,
      List<BigInteger> token0Amounts,
      List<BigInteger> token1Amounts
  ) {
    this.borrowInfo = new BorrowInfo(
        kokoaBalanceDto.getCollateralInfos()
            .stream()
            .map(CollateralInfo::new)
            .collect(Collectors.toList()),
        kokoaBalanceDto.getBorrowReward()
    );
    this.governInfo = new GovernInfo(
        kokoaBalanceDto.getStakingKokoaReward(),
        kokoaBalanceDto.getStakingKsdReward(),
        kokoaBalanceDto.getStakedKokoaBalance()
    );
    this.earnInfo = new EarnInfo(
        kokoaBalanceDto.getDepositedKSDBalance()
    );
    this.klayKokoaFarm = new FarmInfo(
        "KLAY",
        "KOKOA",
        kokoaBalanceDto.getTokens().getKlayKokoaPool().getAddress(),
        kokoaBalanceDto.getTokens().getKlayKokoaLp().getAddress(),
        Amount.of(kokoaBalanceDto.getTokens().getKlayKokoaPool().getBalance()),
        Amount.of(kokoaBalanceDto.getKokoaClaimableRewards().getKlayKokoaPool().getBalance()),
        Objects.isNull(token0Amounts) ? Amount.zero() : Amount.of(token0Amounts.get(0)),
        Objects.isNull(token1Amounts) ? Amount.zero() : Amount.of(token1Amounts.get(0))
    );
    this.klayKsdFarm = new FarmInfo(
        "KLAY",
        "KSD",
        kokoaBalanceDto.getTokens().getKlayKsdPool().getAddress(),
        kokoaBalanceDto.getTokens().getKlayKsdLp().getAddress(),
        Amount.of(kokoaBalanceDto.getTokens().getKlayKsdPool().getBalance()),
        Amount.of(kokoaBalanceDto.getKokoaClaimableRewards().getKlayKsdPool().getBalance()),
        Objects.isNull(token0Amounts) ? Amount.zero() : Amount.of(token0Amounts.get(1)),
        Objects.isNull(token1Amounts) ? Amount.zero() : Amount.of(token1Amounts.get(1))
    );
    this.ksdKdaiFarm = new FarmInfo(
        "KSD",
        "KDAI",
        kokoaBalanceDto.getTokens().getKsdKdaiPool().getAddress(),
        kokoaBalanceDto.getTokens().getKsdKdaiLp().getAddress(),
        Amount.of(kokoaBalanceDto.getTokens().getKsdKdaiPool().getBalance()),
        Amount.of(kokoaBalanceDto.getKokoaClaimableRewards().getKsdKdaiPool().getBalance()),
        Objects.isNull(token0Amounts) ? Amount.zero() : Amount.of(token0Amounts.get(2)),
        Objects.isNull(token1Amounts) ? Amount.zero() : Amount.of(token1Amounts.get(2))
    );
    this.ksdKusdtFarm = new FarmInfo(
        "KSD",
        "KUSDT",
        kokoaBalanceDto.getTokens().getKsdKusdtPool().getAddress(),
        kokoaBalanceDto.getTokens().getKsdKusdtLp().getAddress(),
        Amount.of(kokoaBalanceDto.getTokens().getKsdKusdtPool().getBalance()),
        Amount.of(kokoaBalanceDto.getKokoaClaimableRewards().getKsdKusdtPool().getBalance()),
        Objects.isNull(token0Amounts) ? Amount.zero() : Amount.of(token0Amounts.get(3)),
        Objects.isNull(token1Amounts) ? Amount.zero() : Amount.of(token1Amounts.get(3))
    );
    this.aKlayKokoaFarm = new FarmInfo(
        "AKLAY",
        "KOKOA",
        kokoaBalanceDto.getTokens().getAKlayKokoaPool().getAddress(),
        kokoaBalanceDto.getTokens().getAKlayKokoaLp().getAddress(),
        Amount.of(kokoaBalanceDto.getTokens().getAKlayKokoaPool().getBalance()),
        Amount.of(kokoaBalanceDto.getKokoaClaimableRewards().getAKlayKokoaPool().getBalance()),
        Objects.isNull(token0Amounts) ? Amount.zero() : Amount.of(token0Amounts.get(4)),
        Objects.isNull(token1Amounts) ? Amount.zero() : Amount.of(token1Amounts.get(4))
    );
    this.aKlayKsdFarm = new FarmInfo(
        "AKLAY",
        "KSD",
        kokoaBalanceDto.getTokens().getAKlayKsdPool().getAddress(),
        kokoaBalanceDto.getTokens().getAKlayKsdLp().getAddress(),
        Amount.of(kokoaBalanceDto.getTokens().getAKlayKsdPool().getBalance()),
        Amount.of(kokoaBalanceDto.getKokoaClaimableRewards().getAKlayKsdPool().getBalance()),
        Objects.isNull(token0Amounts) ? Amount.zero() : Amount.of(token0Amounts.get(5)),
        Objects.isNull(token1Amounts) ? Amount.zero() : Amount.of(token1Amounts.get(5))
    );
    this.kWbtcKsdFarm = new FarmInfo(
        "KWBTC",
        "KSD",
        kokoaBalanceDto.getTokens().getKWbtcKsdPool().getAddress(),
        kokoaBalanceDto.getTokens().getKWbtcKsdLp().getAddress(),
        Amount.of(kokoaBalanceDto.getTokens().getKWbtcKsdPool().getBalance()),
        Amount.of(kokoaBalanceDto.getKokoaClaimableRewards().getKWbtcKsdPool().getBalance()),
        Objects.isNull(token0Amounts) ? Amount.zero() : Amount.of(token0Amounts.get(6)),
        Objects.isNull(token1Amounts) ? Amount.zero() : Amount.of(token1Amounts.get(6))
    );
    this.kEthKsdFarm = new FarmInfo(
        "KETH",
        "KSD",
        kokoaBalanceDto.getTokens().getKEthKsdPool().getAddress(),
        kokoaBalanceDto.getTokens().getKEthKsdLp().getAddress(),
        Amount.of(kokoaBalanceDto.getTokens().getKEthKsdPool().getBalance()),
        Amount.of(kokoaBalanceDto.getKokoaClaimableRewards().getKEthKsdPool().getBalance()),
        Objects.isNull(token0Amounts) ? Amount.zero() : Amount.of(token0Amounts.get(7)),
        Objects.isNull(token1Amounts) ? Amount.zero() : Amount.of(token1Amounts.get(7))
    );
    this.ksdKusdcFarm = new FarmInfo(
        "KSD",
        "KUSDC",
        kokoaBalanceDto.getTokens().getKsdKusdcPool().getAddress(),
        kokoaBalanceDto.getTokens().getKsdKusdcLp().getAddress(),
        Amount.of(kokoaBalanceDto.getTokens().getKsdKusdcPool().getBalance()),
        Amount.of(kokoaBalanceDto.getKokoaClaimableRewards().getKsdKusdcPool().getBalance()),
        Objects.isNull(token0Amounts) ? Amount.zero() : Amount.of(token0Amounts.get(8)),
        Objects.isNull(token1Amounts) ? Amount.zero() : Amount.of(token1Amounts.get(8))
    );
    this.kspKsdFarm = new FarmInfo(
        "KSP",
        "KSD",
        kokoaBalanceDto.getTokens().getKspKsdPool().getAddress(),
        kokoaBalanceDto.getTokens().getKspKsdLp().getAddress(),
        Amount.of(kokoaBalanceDto.getTokens().getKspKsdPool().getBalance()),
        Amount.of(kokoaBalanceDto.getKokoaClaimableRewards().getKspKsdPool().getBalance()),
        Objects.isNull(token0Amounts) ? Amount.zero() : Amount.of(token0Amounts.get(9)),
        Objects.isNull(token1Amounts) ? Amount.zero() : Amount.of(token1Amounts.get(9))
    );
    this.kXrpKsdFarm = new FarmInfo(
        "KXRP",
        "KSD",
        kokoaBalanceDto.getTokens().getKXrpKsdPool().getAddress(),
        kokoaBalanceDto.getTokens().getKXrpKsdLp().getAddress(),
        Amount.of(kokoaBalanceDto.getTokens().getKXrpKsdPool().getBalance()),
        Amount.of(kokoaBalanceDto.getKokoaClaimableRewards().getKXrpKsdPool().getBalance()),
        Objects.isNull(token0Amounts) ? Amount.zero() : Amount.of(token0Amounts.get(10)),
        Objects.isNull(token1Amounts) ? Amount.zero() : Amount.of(token1Amounts.get(10))
    );
  }

  public List<FarmInfo> getFarmInfos() {
    return Arrays.asList(
        this.klayKokoaFarm,
        this.klayKsdFarm,
        this.ksdKdaiFarm,
        this.ksdKusdtFarm,
        this.aKlayKokoaFarm,
        this.aKlayKsdFarm,
        this.kWbtcKsdFarm,
        this.kEthKsdFarm,
        this.ksdKusdcFarm,
        this.kspKsdFarm,
        this.kXrpKsdFarm
    );
  }

  @Getter
  public static class BorrowInfo {

    private List<CollateralInfo> collateralInfos;
    private BigInteger reward;
    private boolean borrowed;

    public BorrowInfo(List<CollateralInfo> collateralInfos, BigInteger reward) {
      this.collateralInfos = collateralInfos;
      this.reward = reward;
      this.borrowed = !collateralInfos.stream()
          .map(collateralInfo -> collateralInfo.getLoan())
          .reduce(BigInteger.ZERO, BigInteger::add)
          .equals(BigInteger.ZERO);
    }

    @Getter
    public static class CollateralInfo {

      private String collateralType; // KLAY, AKLAY
      private BigInteger lockedCollateral;
      private BigInteger loan;

      public CollateralInfo(KokoaBalanceDto.CollateralInfo collateralInfo) {
        this.collateralType = collateralInfo.getCollateralType();
        this.lockedCollateral = collateralInfo.getLockedCollateral();
        this.loan = collateralInfo.getLoan();
      }
    }
  }

  @Getter
  public static class GovernInfo {

    private BigInteger kokoaReward;
    private BigInteger ksdReward;
    private BigInteger stakedKokoaBalance;
    private boolean governed;

    public GovernInfo(BigInteger kokoaReward, BigInteger ksdReward,
        BigInteger stakedKokoaBalance) {
      this.kokoaReward = kokoaReward;
      this.ksdReward = ksdReward;
      this.stakedKokoaBalance = stakedKokoaBalance;
      this.governed = !stakedKokoaBalance.equals(BigInteger.ZERO);
    }
  }

  @Getter
  public static class EarnInfo {

    private BigInteger depositedKsdBalance;
    private boolean earned;

    public EarnInfo(BigInteger depositedKsdBalance) {
      this.depositedKsdBalance = depositedKsdBalance;
      this.earned = !depositedKsdBalance.equals(BigInteger.ZERO);
    }

    @Getter
    public static class FarmInfo {

      private String tokenA;
      private String tokenB;
      private String farmAddress;
      private String lpAddress;
      private Amount lpAmount;
      private Amount kokoaReward;
      private Amount tokenAamount;
      private Amount tokenBamount;

      public FarmInfo(String tokenA, String tokenB, String farmAddress, String lpAddress,
          Amount lpAmount, Amount kokoaReward, Amount tokenAamount, Amount tokenBamount) {
        this.tokenA = tokenA;
        this.tokenB = tokenB;
        this.farmAddress = farmAddress;
        this.lpAddress = lpAddress;
        this.lpAmount = lpAmount;
        this.kokoaReward = kokoaReward;
        this.tokenAamount = tokenAamount;
        this.tokenBamount = tokenBamount;
      }

      public String getName() {
        return this.tokenA + "-" + this.tokenB;
      }
    }
  }
}
