package io.coin.ccbc.domain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DexTradeFinder {

  private final BlockchainClient blockchainClient;
  private final DexPairRepository dexPairRepository;

  public DexTradeFinder(
      BlockchainClient blockchainClient,
      DexPairRepository dexPairRepository
  ) {

    this.blockchainClient = blockchainClient;
    this.dexPairRepository = dexPairRepository;
  }


  public List<Trade> getTradeExactAmountIn(
      Coin startCoin, //A
      Coin endCoin, // B
      BigInteger startAmountIn,
      int maxPathNum
  ) {
    List<Trade> estimateResult = this.blockchainClient
        .estimateAmountOutFromAmountIn(startAmountIn,
            this.getSwapPath(startCoin, endCoin, maxPathNum));
    return estimateResult.stream()
        .sorted(Comparator.comparing(Trade::getToAmount).reversed())
        .collect(Collectors.toList());
  }

  public List<Trade> getTradeExactAmountOut(
      Coin startCoin, //A
      Coin endCoin, // B
      BigInteger endAmountOut,
      int maxPathNum
  ) {
    List<Trade> estimateResult = this.blockchainClient
        .estimateAmountInFromAmountOut(endAmountOut,
            this.getSwapPath(startCoin, endCoin, maxPathNum));
    return estimateResult.stream()
        .sorted(Comparator.comparing(Trade::getFromAmount))
        .collect(Collectors.toList());
  }

  public List<List<Path>> getSwapPath(Coin startCoin, Coin endCoin, int maxPathNum) {
    Map<Address, List<DexPair>> coinPoolsMap = new HashMap<>();
    this.dexPairRepository.findAll()
        .forEach(
            pool -> {
              coinPoolsMap.computeIfAbsent(
                  pool.getCoin0().getAddress(),
                  key -> new ArrayList<>()
              ).add(pool);
              coinPoolsMap.computeIfAbsent(
                  pool.getCoin1().getAddress(),
                  key -> new ArrayList<>()
              ).add(pool);
            });
    List<List<Path>> candidates = new ArrayList<>();
    List<List<Path>> answers = new ArrayList<>();
    for (int i = 0; i < maxPathNum; i++) {
      if (i == 0) {
        List<Path> depth1Paths = new ArrayList<>();

        // 시작 코인(A) 에서 연결된 모든 풀(A-B, A-C, A-D ...) 맨처음 등록.
        coinPoolsMap.computeIfAbsent(startCoin.getAddress(), key -> {
          throw new IllegalArgumentException(
              String.format("cannot find path with '%s'", startCoin.getSymbol())
          );
        }).forEach(pool ->
            depth1Paths.add(Path.of(startCoin, pool.getOppositeCoin(startCoin), pool))
        );

        // A->B 인풀은 바로 answer 에 넣어줌
        depth1Paths.stream()
            .filter(
                path -> {
                  if (path.getToCoin().getAddress().equals(endCoin.getAddress())) {
                    answers.add(List.of(path));
                    return false;
                  }
                  return true;
                })
            .map(List::of)
            .forEach(candidates::add);
        continue;
      }

      // 각 candidate 의 맨 끝 path 에서 이어질 수 있는 path 를 찾아 추가한다.
      List<List<Path>> newCandidate = new ArrayList<>();
      candidates.forEach(
          candidate -> {
            Path lastPath = candidate.get(candidate.size() - 1);
            Coin lastToCoin = lastPath.getToCoin();

            // 맨 끝 path 에서 이어질 수 있는 pool 목록을 찾는다
            List<DexPair> candidateKlayswapPoolList = coinPoolsMap.get(
                lastPath.getToCoin().getAddress());

            // 경로중 이미 사용한 풀은 사용하면 안된다 [e.g. (A-B) -> (B-C) -> (C-A) -> !(A-B)! ]
            List<Address> alreadyExistPoolList = candidate
                .stream()
                .map(c -> c.getTargetDexPair().getAddress())
                .collect(Collectors.toList());

            // pool 목록을 추가한 새로운 candidates 를 만든다.
            candidateKlayswapPoolList.stream()
                .filter(pool -> !alreadyExistPoolList.contains(pool.getAddress()))
                .forEach(pool -> {
                      List<Path> newCandidatePath = new ArrayList<>(List.copyOf(candidate));
                      newCandidatePath.add(Path.of(lastToCoin, pool.getOppositeCoin(lastToCoin), pool));
                      newCandidate.add(newCandidatePath);
                    }
                );
          }
      );

      // 다음 뎁스 돌기전에 완성된 경로는 정답에 넣어준다.
      newCandidate.removeIf(c -> {
            Path lastPath = c.get(c.size() - 1);
            if (lastPath.getToCoin().getAddress().equals(endCoin.getAddress())) {
              answers.add(c);
              return true;
            }
            return false;
          }
      );
      candidates = newCandidate;
    }
    return answers;
  }
}
