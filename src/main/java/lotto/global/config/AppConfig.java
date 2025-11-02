package lotto.global.config;

import lotto.View.Output.OutputView;
import lotto.View.input.InputView;
import lotto.application.LottoService;
import lotto.application.service.GameResultService;
import lotto.application.service.PurchaseLottoService;
import lotto.application.service.WinningNumberService;
import lotto.infrastructure.repository.BonusNumberRepository;
import lotto.infrastructure.repository.LottoRepository;
import lotto.infrastructure.repository.PurchaseAmountRepository;
import lotto.infrastructure.repository.WinningNumberRepository;
import lotto.presentation.LottoController;

public class AppConfig {
    // Repository Beans
    private final BonusNumberRepository bonusNumberRepository = new BonusNumberRepository();
    private final LottoRepository lottoRepository = new LottoRepository();
    private final PurchaseAmountRepository purchaseAmountRepository = new PurchaseAmountRepository();
    private final WinningNumberRepository winningNumberRepository = new WinningNumberRepository();

    // Service Beans
    WinningNumberService winningNumberService = new WinningNumberService(
            winningNumberRepository,
            bonusNumberRepository
    );
    PurchaseLottoService purchaseLottoService = new PurchaseLottoService(
            purchaseAmountRepository,
            lottoRepository
    );
    GameResultService gameResultService = new GameResultService(
            purchaseAmountRepository,
            lottoRepository,
            winningNumberRepository,
            bonusNumberRepository
    );

    LottoService lottoService = new LottoService(
            purchaseLottoService,
            winningNumberService,
            gameResultService
    );

    InputView inputView = new InputView();
    OutputView outputView = new OutputView();

    LottoController lottoController = new LottoController(inputView, outputView, lottoService);

    public LottoController getLottoController() {
        return lottoController;
    }
}
