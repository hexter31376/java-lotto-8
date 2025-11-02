package lotto.application.service;

import lotto.application.dto.request.BonusNumberRequest;
import lotto.application.dto.request.WinningNumberRequest;
import lotto.infrastructure.repository.BonusNumberRepository;
import lotto.infrastructure.repository.WinningNumberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WinningNumberServiceTest {

    private WinningNumberRepository winningRepo;
    private BonusNumberRepository bonusRepo;
    private WinningNumberService service;

    @BeforeEach
    void setUp() {
        winningRepo = new WinningNumberRepository();
        bonusRepo = new BonusNumberRepository();
        service = new WinningNumberService(winningRepo, bonusRepo);
    }

    @DisplayName("정상 당첨 번호 문자열을 저장하면 레파지토리에 저장된다")
    @Test
    void saveWinningNumbers_success() {
        WinningNumberRequest request = new WinningNumberRequest("1, 2, 3, 4, 5, 6");
        service.saveWinningNumbers(request);

        assertThat(winningRepo.findById(1L)).isNotNull();
        assertThat(winningRepo.findById(1L).getNumbers()).containsExactly(1,2,3,4,5,6);
    }

    @DisplayName("잘못된 형식(숫자 아니거나 포맷 오류) 입력 시 예외가 발생한다")
    @Test
    void saveWinningNumbers_invalidFormat() {
        WinningNumberRequest badRequest = new WinningNumberRequest("1, 2, 3, a, 5, 6");
        assertThatThrownBy(() -> service.saveWinningNumbers(badRequest))
                .isInstanceOf(NumberFormatException.class);
    }

    @DisplayName("보너스 번호 저장 시 레파지토리에 저장된다")
    @Test
    void saveBonusNumber_success() {
        BonusNumberRequest request = new BonusNumberRequest("7");
        service.saveBonusNumber(request);

        assertThat(bonusRepo.findById(1L)).isNotNull();
        assertThat(bonusRepo.findById(1L).get()).isEqualTo(7);
    }
}
