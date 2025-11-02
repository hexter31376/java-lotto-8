package lotto.application;

import lotto.application.dto.request.BonusNumberRequest;
import lotto.application.dto.request.PurchaseRequest;
import lotto.application.dto.request.WinningNumberRequest;
import lotto.application.dto.response.LottoGameResultResponse;
import lotto.application.dto.response.PurchasedLottoResponse;
import lotto.application.service.GameResultService;
import lotto.application.service.PurchaseLottoService;
import lotto.application.service.WinningNumberService;
import lotto.infrastructure.repository.BonusNumberRepository;
import lotto.infrastructure.repository.LottoRepository;
import lotto.infrastructure.repository.PurchaseAmountRepository;
import lotto.infrastructure.repository.WinningNumberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LottoServiceTest {

    // 공통 필드로 옮겨 중복 제거
    private PurchaseAmountRepository purchaseRepo;
    private LottoRepository lottoRepo;
    private WinningNumberRepository winningRepo;
    private BonusNumberRepository bonusRepo;

    private PurchaseLottoService purchaseService;
    private WinningNumberService winningService;
    private GameResultService gameService;
    private LottoService lottoService;

    @BeforeEach
    void setUp() {
        purchaseRepo = new PurchaseAmountRepository();
        lottoRepo = new LottoRepository();
        winningRepo = new WinningNumberRepository();
        bonusRepo = new BonusNumberRepository();

        purchaseService = new PurchaseLottoService(purchaseRepo, lottoRepo);
        winningService = new WinningNumberService(winningRepo, bonusRepo);
        gameService = new GameResultService(purchaseRepo, lottoRepo, winningRepo, bonusRepo);
        lottoService = new LottoService(purchaseService, winningService, gameService);
    }

    @DisplayName("구입: 응답이 반환된다")
    @Test
    void purchaseLottos_returnsResponse() {
        PurchasedLottoResponse purchased = lottoService.purchaseLottos(new PurchaseRequest("1000"));
        assertThat(purchased.lottoNumbers()).hasSize(1);
    }

    @DisplayName("당첨번호 저장: 저장소에 저장된다")
    @Test
    void saveWinningNumbers_persists() {
        lottoService.saveWinningNumbers(new WinningNumberRequest("1, 2, 3, 4, 5, 6"));
        assertThat(winningRepo.findById(1L)).isNotNull();
    }

    @DisplayName("보너스 저장: 저장소에 저장된다")
    @Test
    void saveBonusNumber_persists() {
        lottoService.saveBonusNumber(new BonusNumberRequest("7"));
        assertThat(bonusRepo.findById(1L)).isNotNull();
    }

    @DisplayName("통계 조회: 결과가 반환된다")
    @Test
    void getLottoGameStatistics_returnsResult() {
        // 준비: 구매, 로또, 당첨/보너스 저장
        purchaseRepo.save(lotto.domain.purchase.PurchaseAmount.from(1000));
        lottoRepo.save(new lotto.domain.lotto.Lotto(java.util.List.of(1,2,3,4,5,6)));
        winningRepo.save(lotto.domain.winningnumber.WinningNumber.from(java.util.List.of(1,2,3,4,5,6)));
        bonusRepo.save(lotto.domain.bonusnumber.BonusNumber.from(7));

        LottoGameResultResponse stats = lottoService.getLottoGameStatistics();
        assertThat(stats).isNotNull();
    }
}
