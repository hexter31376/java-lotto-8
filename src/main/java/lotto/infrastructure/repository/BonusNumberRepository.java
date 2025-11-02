package lotto.infrastructure.repository;

import lotto.domain.bonusnumber.BonusNumber;

import java.util.LinkedHashMap;
import java.util.Map;

public class BonusNumberRepository {

    private static final Map<Long, BonusNumber> bonusNumbers = new LinkedHashMap<>();

    public void save(BonusNumber bonusNumber) {
        long id = bonusNumbers.size() + 1L;
        bonusNumbers.put(id, bonusNumber);
    }

    public BonusNumber findById(Long id) {
        return bonusNumbers.get(id);
    }
}
