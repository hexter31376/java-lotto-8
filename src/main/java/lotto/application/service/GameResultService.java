package lotto.application.service;

import lotto.application.dto.response.LottoGameResultResponse;
import lotto.application.service.enums.Rank;
import lotto.domain.bonusnumber.BonusNumber;
import lotto.domain.lotto.Lotto;
import lotto.domain.lotto.Lottos;
import lotto.domain.purchase.PurchaseAmount;
import lotto.domain.winningnumber.WinningNumber;
import lotto.infrastructure.repository.BonusNumberRepository;
import lotto.infrastructure.repository.LottoRepository;
import lotto.infrastructure.repository.PurchaseAmountRepository;
import lotto.infrastructure.repository.WinningNumberRepository;

import java.math.BigInteger;
import java.util.*;

public class GameResultService {

    private static final int FIRST_PRIZE_MATCH_NUMBERS = 6;
    private static final int SECOND_PRIZE_MATCH_NUMBERS = 5;
    private static final int THIRD_PRIZE_MATCH_NUMBERS = 5;
    private static final int FOURTH_PRIZE_MATCH_NUMBERS = 4;
    private static final int FIFTH_PRIZE_MATCH_NUMBERS = 3;

    private final PurchaseAmountRepository purchaseAmountRepository;
    private final LottoRepository lottoRepository;
    private final WinningNumberRepository winningNumberRepository;
    private final BonusNumberRepository bonusNumberRepository;


    public GameResultService(PurchaseAmountRepository purchaseAmountRepository,
                             LottoRepository lottoRepository,
                             WinningNumberRepository winningNumberRepository,
                             BonusNumberRepository bonusNumberRepository) {
        this.purchaseAmountRepository = purchaseAmountRepository;
        this.lottoRepository = lottoRepository;
        this.winningNumberRepository = winningNumberRepository;
        this.bonusNumberRepository = bonusNumberRepository;
    }

    /**
     * 로또 게임 통계 조회
     * @return 로또 게임 결과 통계 응답
     */
    public LottoGameResultResponse getLottoGameStatistics() {
        // 1. 레파지토리 항목 조회
        PurchaseAmount purchaseAmount = purchaseAmountRepository.findById(1L);
        Lottos lottos = Lottos.from(lottoRepository.findAll());
        WinningNumber winningNumber = winningNumberRepository.findById(1L);
        BonusNumber bonusNumber = bonusNumberRepository.findById(1L);
        // 2. 당첨 개수 집계
        Map<Rank, Integer> winningPrizeCount = winningPrizeCount(
                lottos.getLottos(),
                winningNumber.getNumbers(),
                bonusNumber.get()
        );
        // 3. 수익률 계산
        double yield = calculateYield(winningPrizeCount, purchaseAmount.getAmount());

        // 4. 로또 게임 결과 통계 응답 생성 및 반환
        return new LottoGameResultResponse(winningPrizeCount, yield);
    }

    /**
     * 당첨 개수 집계
     * @param lottos 구매한 로또 목록
     * @param winningNumbers 당첨 번호 목록
     * @param bonusNumber 보너스 번호
     * @return 랭크 기반 당첨 개수 맵
     */
    private Map<Rank, Integer> winningPrizeCount(
            List<Lotto> lottos,
            List<Integer> winningNumbers,
            Integer bonusNumber
    ) {
        // 당첨 번호 Set 생성
        final Set<Integer> winningNumberMatcher = new HashSet<>(winningNumbers);
        // 반환할 랭크 기반 당첨 개수 리스트 목록
        EnumMap<Rank, Integer> winningPrizeCount =
                lottos.stream()
                        .map(lotto -> decideRank(lotto.getNumbers(), winningNumberMatcher, bonusNumber))
                        .filter(rank -> rank != Rank.NONE)
                        .collect(
                                () -> new EnumMap<>(Rank.class), // supplier
                                (map, rank) -> map.put(rank, map.getOrDefault(rank, 0) + 1), // accumulator
                                EnumMap::putAll // combiner
                        );
        return winningPrizeCount;
    }

    /**
     * 랭크 결정
     * @param ticket 로또 티켓 번호 목록
     * @param winningSet 당첨 번호 Set
     * @param bonusNumber 보너스 번호
     * @return 결정된 랭크
     */
    private Rank decideRank(List<Integer> ticket, Set<Integer> winningSet, int bonusNumber) {
        long match = ticket.stream().filter(winningSet::contains).count();
        boolean bonus = ticket.contains(bonusNumber);

        if (match == FIRST_PRIZE_MATCH_NUMBERS) return Rank.FIRST;
        if (match == SECOND_PRIZE_MATCH_NUMBERS && bonus) return Rank.SECOND;
        if (match == THIRD_PRIZE_MATCH_NUMBERS) return Rank.THIRD;
        if (match == FOURTH_PRIZE_MATCH_NUMBERS) return Rank.FOURTH;
        if (match == FIFTH_PRIZE_MATCH_NUMBERS) return Rank.FIFTH;
        return Rank.NONE;
    }

    /**
     * 수익률 계산
     * @param winningPrizeCount 랭크 기반 당첨 개수 맵
     * @param totalPurchaseAmount 총 구매 금액
     * @return 수익률
     */
    private double calculateYield(Map<Rank, Integer> winningPrizeCount, long totalPurchaseAmount) {
        BigInteger totalWinningAmount = BigInteger.ZERO;

        for (Map.Entry<Rank, Integer> entry : winningPrizeCount.entrySet()) {
            Rank rank = entry.getKey(); // 엔트리에서 해당 랭크 획득
            Integer count = entry.getValue(); // 엔트리에서 해당 랭크의 당첨 개수 획득

            BigInteger prizeAmount = BigInteger.valueOf(rank.getPrizeAmount()); // 랭크의 상금 금액을 BigInteger로 변환
            BigInteger winningAmount = prizeAmount.multiply(BigInteger.valueOf(count)); // 상금 금액에 당첨 개수를 곱하여 해당 랭크의 총 당첨 금액 계산
            totalWinningAmount = totalWinningAmount.add(winningAmount); // 총 당첨 금액에 해당 랭크의 총 당첨 금액을 누적
        }
        return totalWinningAmount.doubleValue() / totalPurchaseAmount * 100;
    }
}
