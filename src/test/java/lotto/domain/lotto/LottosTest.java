package lotto.domain.lotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

class LottosTest {

    @DisplayName("로또 목록이 null이면 예외가 발생한다")
    @Test
    void throwsExceptionWhenListIsNull() {
        assertThatThrownBy(() -> Lottos.from(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 목록이 비어 있으면 예외가 발생한다")
    @Test
    void throwsExceptionWhenListIsEmpty() {
        assertThatThrownBy(() -> Lottos.from(List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 목록에 null 원소가 있으면 예외가 발생한다")
    @Test
    void throwsExceptionWhenListContainsNull() {
        List<Lotto> listWithNull = new ArrayList<>();
        listWithNull.add(new Lotto(List.of(1, 2, 3, 4, 5, 6)));
        listWithNull.add(null);

        assertThatThrownBy(() -> Lottos.from(listWithNull))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("정상적인 로또 목록 생성 및 불변성 확인")
    @Test
    void validCreationAndImmutability() {
        List<Lotto> original = new ArrayList<>();
        original.add(new Lotto(List.of(1, 2, 3, 4, 5, 6)));
        original.add(new Lotto(List.of(7, 8, 9, 10, 11, 12)));

        Lottos lottos = Lottos.from(original);
        assertThat(lottos.getLottos()).containsExactlyElementsOf(original);

        // 원본 리스트를 변경해도 Lottos 내부는 영향을 받지 않는다
        original.add(new Lotto(List.of(13, 14, 15, 16, 17, 18)));
        assertThat(lottos.getLottos()).hasSize(2);
    }
}
