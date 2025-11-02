package lotto.application.service;

import lotto.application.dto.request.BonusNumberRequest;
import lotto.application.dto.request.WinningNumberRequest;
import lotto.domain.bonusnumber.BonusNumber;
import lotto.domain.winningnumber.WinningNumber;
import lotto.infrastructure.repository.BonusNumberRepository;
import lotto.infrastructure.repository.WinningNumberRepository;

import java.util.Arrays;
import java.util.List;

public class WinningNumberService {

    private static final String COMMA_REGEX = ",";

    private final WinningNumberRepository winningNumberRepository;
    private final BonusNumberRepository bonusNumberRepository;

    public WinningNumberService(WinningNumberRepository winningNumberRepository,
                                BonusNumberRepository bonusNumberRepository) {
        this.winningNumberRepository = winningNumberRepository;
        this.bonusNumberRepository = bonusNumberRepository;
    }

    /**
     * 당첨 번호 저장
     * @param request 당첨 번호 등록 요청 정보
     */
    public void saveWinningNumbers(WinningNumberRequest request) {
        // 1. 당첨 번호 저장
        String[] parts = request.winningNumber().split(COMMA_REGEX);

        List<Integer> winningNumbers = Arrays.stream(parts)
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();

        WinningNumber winningNumber = WinningNumber.from(winningNumbers);
        winningNumberRepository.save(winningNumber);
    }

    /**
     * 보너스 번호 저장
     * @param request 보너스 번호 등록 요청 정보
     */
    public void saveBonusNumber(BonusNumberRequest request) {
        // 2. 보너스 번호 저장
        BonusNumber bonusNumber = BonusNumber.from(Integer.parseInt(request.bonusNumber()));
        bonusNumberRepository.save(bonusNumber);
    }
}
