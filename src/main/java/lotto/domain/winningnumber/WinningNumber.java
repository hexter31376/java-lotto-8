package lotto.domain.winningnumber;

import java.util.HashSet;
import java.util.List;

public class WinningNumber {

    private static final int LOTTO_NUMBER_SIZE = 6;
    private static final int MIN_LOTTO_NUMBER = 1;
    private static final int MAX_LOTTO_NUMBER = 45;

    private final List<Integer> numbers;

    private WinningNumber(List<Integer> numbers) {
        this.numbers = numbers.stream()
                .sorted()
                .toList();
    }

    public static WinningNumber from(List<Integer> numbers) {
        validate(numbers);
        return new WinningNumber(numbers);
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    private static void validate(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("당첨 번호는 비어 있을 수 없습니다.");
        }
        if (hasDuplicate(numbers)) {
            throw new IllegalArgumentException("당첨 번호는 중복될 수 없습니다.");
        }
        if (numbers.size() != LOTTO_NUMBER_SIZE) {
            throw new IllegalArgumentException("당첨 번호는 6개여야 합니다.");
        }
        numbers.forEach(number -> {
            if (number < MIN_LOTTO_NUMBER || number > MAX_LOTTO_NUMBER) {
                throw new IllegalArgumentException(String.format("당첨 번호는 %d에서 %d 사이의 정수여야 합니다.",
                        MIN_LOTTO_NUMBER, MAX_LOTTO_NUMBER));
            }
        });
    }

    private static boolean hasDuplicate(List<Integer> numbers) {
        return numbers.size() != new HashSet<>(numbers).size();
    }
}
