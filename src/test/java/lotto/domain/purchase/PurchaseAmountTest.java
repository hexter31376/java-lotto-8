package lotto.domain.purchase;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PurchaseAmountTest {

    @DisplayName("구입 금액이 0 또는 음수이면 예외가 발생한다")
    @Test
    void throwsExceptionWhenNonPositive() {
        assertThatThrownBy(() -> PurchaseAmount.from(0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> PurchaseAmount.from(-1000))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("구입 금액이 최대 허용 값을 초과하면 예외가 발생한다")
    @Test
    void throwsExceptionWhenExceedsMax() {
        assertThatThrownBy(() -> PurchaseAmount.from(100001))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> PurchaseAmount.from(200000))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("구입 금액이 1,000원 단위가 아니면 예외가 발생한다")
    @Test
    void throwsExceptionWhenNotMultipleOfUnit() {
        assertThatThrownBy(() -> PurchaseAmount.from(1500))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> PurchaseAmount.from(999))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("정상 금액이면 객체 생성 및 수량 계산이 정확하다")
    @Test
    void validCreationAndQuantity() {
        PurchaseAmount pa = PurchaseAmount.from(1000);
        assertThat(pa.getAmount()).isEqualTo(1000);
        assertThat(pa.getQuantity()).isEqualTo(1);

        PurchaseAmount pa5 = PurchaseAmount.from(5000);
        assertThat(pa5.getQuantity()).isEqualTo(5);

        PurchaseAmount paMax = PurchaseAmount.from(100000);
        assertThat(paMax.getQuantity()).isEqualTo(100);
    }
}
