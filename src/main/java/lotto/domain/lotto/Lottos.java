package lotto.domain.lotto;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Lottos {

    private final List<Lotto> lottos;

    private Lottos(List<Lotto> lottos) {
        validate(lottos);
        this.lottos = List.copyOf(lottos);
    }

    public static Lottos from(List<Lotto> lottoList) {
        return new Lottos(lottoList);
    }

    public List<Lotto> getLottos() {
        return lottos;
    }

    private static void validate(List<Lotto> lottoList) {
        if (lottoList == null || lottoList.isEmpty()) {
            throw new IllegalArgumentException("로또 목록은 비어 있을 수 없습니다.");
        }
        if (lottoList.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("로또 목록에 null이 포함될 수 없습니다.");
        }
        Set<List<Integer>> unique = lottoList.stream()
                 .map(Lotto::getNumbers)
                 .collect(Collectors.toSet());
        if (unique.size() != lottoList.size()) {
             throw new IllegalArgumentException("중복된 로또가 존재합니다.");
        }
    }
}
