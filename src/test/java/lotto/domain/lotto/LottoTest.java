package lotto.domain.lotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoTest {
    @DisplayName("로또 번호의 개수가 6개가 넘어가면 예외가 발생한다.")
    @Test
    void 로또_번호의_개수가_6개가_넘어가면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 6, 7)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    @Test
    void 로또_번호에_중복된_숫자가_있으면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호가 null이면 예외가 발생한다.")
    @Test
    void throwsExceptionWhenNumbersAreNull() {
        assertThatThrownBy(() -> new Lotto(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호가 비어 있으면 예외가 발생한다.")
    @Test
    void throwsExceptionWhenNumbersAreEmpty() {
        assertThatThrownBy(() -> new Lotto(List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호가 범위를 벗어나면 예외가 발생한다.")
    @Test
    void throwsExceptionWhenNumbersOutOfRange() {
        assertThatThrownBy(() -> new Lotto(List.of(0, 2, 3, 4, 5, 6)))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 46)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("정상적인 로또 생성 시 내부 숫자 반환 확인")
    @Test
    void returnsNumbersOnValidCreation() {
        List<Integer> nums = List.of(1, 2, 3, 4, 5, 6);
        Lotto lotto = new Lotto(nums);
        assertThat(lotto.getNumbers()).containsExactlyElementsOf(nums);
    }

    @DisplayName("경계값(1, 45)을 포함한 로또 생성은 정상이다")
    @Test
    void validWhenContainingBoundaryValues() {
        List<Integer> nums = List.of(1, 10, 20, 30, 44, 45);
        Lotto lotto = new Lotto(nums);
        assertThat(lotto.getNumbers()).containsExactlyElementsOf(nums);
    }

    @DisplayName("로또 번호가 6개보다 적으면 예외가 발생한다.")
    @Test
    void throwsExceptionWhenLessThanSixNumbers() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
