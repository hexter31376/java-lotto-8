package lotto.infrastructure.repository;

import lotto.domain.winningnumber.WinningNumber;

import java.util.LinkedHashMap;
import java.util.Map;

public class WinningNumberRepository {
    private static final Map<Long, WinningNumber> winningNumbers = new LinkedHashMap<>();

    public void save(WinningNumber winningNumber) {
        long id = winningNumbers.size() + 1L;
        winningNumbers.put(id, winningNumber);
    }

    public WinningNumber findById(Long id) {
        return winningNumbers.get(id);
    }
}
