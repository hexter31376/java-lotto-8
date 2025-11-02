package lotto.infrastructure.repository;

import lotto.domain.lotto.Lotto;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LottoRepository {

    private static final Map<Long, Lotto> lottos = new LinkedHashMap<>();

    public void save(Lotto lotto) {
        long id = lottos.size() + 1L;
        lottos.put(id, lotto);
    }

    public void saveAll(Iterable<Lotto> lottoList) {
        for (Lotto lotto : lottoList) {
            save(lotto);
        }
    }

    public List<Lotto> findAll() {
        return List.copyOf(lottos.values());
    }
}
