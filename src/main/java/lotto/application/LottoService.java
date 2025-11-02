package lotto.application;

import lotto.application.dto.request.BonusNumberRequest;
import lotto.application.dto.request.PurchaseRequest;
import lotto.application.dto.request.WinningNumberRequest;
import lotto.application.dto.response.LottoGameResultResponse;
import lotto.application.dto.response.PurchasedLottoResponse;
import lotto.application.service.GameResultService;
import lotto.application.service.PurchaseLottoService;
import lotto.application.service.WinningNumberService;

public class LottoService {

    private final PurchaseLottoService purchaseLottoService;
    private final WinningNumberService winningNumberService;
    private final GameResultService gameResultService;

    public LottoService(PurchaseLottoService purchaseLottoService,
                        WinningNumberService winningNumberService,
                        GameResultService gameResultService) {
        this.purchaseLottoService = purchaseLottoService;
        this.winningNumberService = winningNumberService;
        this.gameResultService = gameResultService;
    }

    // 1. 로또 구매
    public PurchasedLottoResponse purchaseLottos(PurchaseRequest request) {
        return purchaseLottoService.purchaseLottos(request);
    }

    // 2. 당첨 번호 저장
    public void saveWinningNumbers(WinningNumberRequest request) {
        winningNumberService.saveWinningNumbers(request);
    }

    // 3. 보너스 번호 저장
    public void saveBonusNumber(BonusNumberRequest request) {
        winningNumberService.saveBonusNumber(request);
    }

    // 4. 로또 게임 통계 조회
    public LottoGameResultResponse getLottoGameStatistics() {
        return gameResultService.getLottoGameStatistics();
    }
}
