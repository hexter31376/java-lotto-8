package lotto.domain.bonusnumber;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BonusNumberTest {

    @DisplayName("보너스 번호가 null이면 예외가 발생한다")
    @Test
    void throwsExceptionWhenNull() {
        assertThatThrownBy(() -> BonusNumber.from(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("보너스 번호가 범위를 벗어나면 예외가 발생한다")
    @Test
    void throwsExceptionWhenOutOfRange() {
        assertThatThrownBy(() -> BonusNumber.from(0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BonusNumber.from(46))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("정상 보너스 번호는 생성되고 get으로 값을 반환한다")
    @Test
    void validCreationAndGet() {
        assertThat(BonusNumber.from(1).get()).isEqualTo(1);
        assertThat(BonusNumber.from(45).get()).isEqualTo(45);
    }
}
