package lotto.presentation;

import lotto.View.Output.OutputView;
import lotto.View.input.InputView;
import lotto.application.LottoService;
import lotto.application.dto.request.BonusNumberRequest;
import lotto.application.dto.request.PurchaseRequest;
import lotto.application.dto.request.WinningNumberRequest;
import lotto.application.dto.response.LottoGameResultResponse;
import lotto.application.dto.response.PurchasedLottoResponse;
import lotto.application.service.enums.Rank;
import lotto.util.ControllerHandler;

import java.util.Arrays;
import java.util.Map;

public class LottoController {

    private static final String INTEGER_NUMBER_REGEX = "\\d+";
    private static final String SPLIT_REGEX = ",";

    private final InputView inputView;
    private final OutputView outputView;
    private final LottoService lottoService;

    public LottoController(InputView inputView, OutputView outputView, LottoService lottoService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.lottoService = lottoService;
    }

    public void run() {
        ControllerHandler.retryAtException(this::purchaseLottoTickets);
        ControllerHandler.retryAtException(this::registerWinningNumbers);
        ControllerHandler.retryAtException(this::registerBonusNumber);
        displayLottoGameStatistics();
    }

    private void purchaseLottoTickets() {
        String price = inputView.readPurchasePrice();
        validateInputNumber(price);
        PurchaseRequest purchaseRequest = new PurchaseRequest(price);
        PurchasedLottoResponse purchasedLottoResponse = lottoService.purchaseLottos(purchaseRequest);
        outputView.printLottoTickets(purchasedLottoResponse.lottoNumbers());
    }

    private void registerWinningNumbers() {
        String winningNumbers = inputView.readWinningNumbers();
        validateInputString(winningNumbers);
        lottoService.saveWinningNumbers(new WinningNumberRequest(winningNumbers));
    }

    private void registerBonusNumber() {
        String bonusNumber = inputView.readBonusNumber();
        validateInputNumber(bonusNumber);
        lottoService.saveBonusNumber(new BonusNumberRequest(bonusNumber));
    }

    private void displayLottoGameStatistics() {
        LottoGameResultResponse lottoGameResultResponse = lottoService.getLottoGameStatistics();
        Map<Rank, Integer> winningNumbers = lottoGameResultResponse.rankWinningCounts();
        double profitRate = lottoGameResultResponse.yield();
        outputView.printGameStatistics(winningNumbers, profitRate);
    }

    private void validateInputString(String input) {
        if (isEmptyValue(input)) {
            throw new IllegalArgumentException("입력값은 null이거나 공백일 수 없습니다.");
        }
        Arrays.stream(input.trim()
                .split(SPLIT_REGEX))
                .forEach(s -> validateInputNumber(s.trim()));
    }

    private void validateInputNumber(String input) {
        if (isEmptyValue(input)) {
            throw new IllegalArgumentException("입력값은 null이거나 공백일 수 없습니다.");
        }
        if (isNotNumberFormat(input)) {
            throw new IllegalArgumentException("입력값은 정수형 숫자 형식이어야 합니다. 입력값 : " + input);
        }
    }

    private static boolean isNotNumberFormat(String input) {
        return !input.matches(INTEGER_NUMBER_REGEX);
    }

    private static boolean isEmptyValue(String input) {
        return input == null || input.isBlank();
    }
}
