package lotto.domain.winningnumber;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WinningNumberTest {

    @DisplayName("당첨 번호가 null이면 예외가 발생한다")
    @Test
    void throwsExceptionWhenNull() {
        assertThatThrownBy(() -> WinningNumber.from(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("당첨 번호가 비어 있으면 예외가 발생한다")
    @Test
    void throwsExceptionWhenEmpty() {
        assertThatThrownBy(() -> WinningNumber.from(List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("당첨 번호에 중복이 있으면 예외가 발생한다")
    @Test
    void throwsExceptionWhenDuplicate() {
        assertThatThrownBy(() -> WinningNumber.from(List.of(1, 2, 3, 4, 5, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("당첨 번호의 개수가 6개가 아니면 예외가 발생한다")
    @Test
    void throwsExceptionWhenSizeNotSix() {
        assertThatThrownBy(() -> WinningNumber.from(List.of(1, 2, 3, 4, 5)))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> WinningNumber.from(List.of(1, 2, 3, 4, 5, 6, 7)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("당첨 번호가 범위를 벗어나면 예외가 발생한다")
    @Test
    void throwsExceptionWhenOutOfRange() {
        assertThatThrownBy(() -> WinningNumber.from(List.of(0, 2, 3, 4, 5, 6)))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> WinningNumber.from(List.of(1, 2, 3, 4, 5, 46)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("정상 입력이면 내부 번호는 정렬되어 보관된다")
    @Test
    void validCreationAndSorted() {
        WinningNumber wn = WinningNumber.from(List.of(6, 1, 4, 3, 5, 2));
        assertThat(wn.getNumbers()).containsExactly(1, 2, 3, 4, 5, 6);
    }
}
