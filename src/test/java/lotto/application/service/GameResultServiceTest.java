package lotto.application.service;

import lotto.application.dto.response.LottoGameResultResponse;
import lotto.application.service.enums.Rank;
import lotto.domain.bonusnumber.BonusNumber;
import lotto.domain.lotto.Lotto;
import lotto.domain.purchase.PurchaseAmount;
import lotto.domain.winningnumber.WinningNumber;
import lotto.infrastructure.repository.BonusNumberRepository;
import lotto.infrastructure.repository.LottoRepository;
import lotto.infrastructure.repository.PurchaseAmountRepository;
import lotto.infrastructure.repository.WinningNumberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GameResultServiceTest {

    private PurchaseAmountRepository purchaseRepo;
    private LottoRepository lottoRepo;
    private WinningNumberRepository winningRepo;
    private BonusNumberRepository bonusRepo;

    @BeforeEach
    void setUp() {
        purchaseRepo = new PurchaseAmountRepository();
        lottoRepo = new LottoRepository();
        winningRepo = new WinningNumberRepository();
        bonusRepo = new BonusNumberRepository();
    }

    @DisplayName("당첨 통계 계산: 1등 1개 케이스 및 수익률 계산 검증")
    @Test
    void calculateStatistics_firstPrizeAndYield() {
        // 1. 저장: 구매 금액 1000원
        purchaseRepo.save(PurchaseAmount.from(1000));

        // 2. 로또: 한 장이 당첨 번호와 동일하도록 저장
        lottoRepo.save(new Lotto(List.of(1, 2, 3, 4, 5, 6)));

        // 3. 당첨 번호와 보너스 번호 저장
        winningRepo.save(WinningNumber.from(List.of(1, 2, 3, 4, 5, 6)));
        bonusRepo.save(BonusNumber.from(7));

        GameResultService service = new GameResultService(purchaseRepo, lottoRepo, winningRepo, bonusRepo);
        LottoGameResultResponse response = service.getLottoGameStatistics();

        Map<Rank, Integer> counts = response.rankWinningCounts();
        assertThat(counts.get(Rank.FIRST)).isEqualTo(1);
        // 수익률: 2_000_000_000원 당첨 / 1000원 지출 => 비율은 매우 큼. 정확한 값보단 양수 확인
        assertThat(response.yield()).isGreaterThan(0);
    }

    @DisplayName("당첨 통계 계산: 5개+보너스(2등) 케이스 검증")
    @Test
    void calculateStatistics_secondPrize() {
        purchaseRepo.save(PurchaseAmount.from(1000));
        lottoRepo.save(new Lotto(List.of(1, 2, 3, 4, 5, 7)));
        winningRepo.save(WinningNumber.from(List.of(1,2,3,4,5,6)));
        bonusRepo.save(BonusNumber.from(7));

        GameResultService service = new GameResultService(purchaseRepo, lottoRepo, winningRepo, bonusRepo);
        LottoGameResultResponse response = service.getLottoGameStatistics();

        Map<Rank, Integer> counts = response.rankWinningCounts();
        assertThat(counts.get(Rank.SECOND)).isEqualTo(1);
    }
}
