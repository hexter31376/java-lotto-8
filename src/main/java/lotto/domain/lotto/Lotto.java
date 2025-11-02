package lotto.domain.lotto;

import java.util.List;

public class Lotto {

    private final List<Integer> numbers;

    // 기본 테스트코드 일치성을 위해 정적 팩토리 메소드화 및 로또 번호 상수화는 미진행
    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    private static void validate(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("로또 번호는 비어 있을 수 없습니다.");
        }
        if (hasDuplicate(numbers)) {
            throw new IllegalArgumentException("로또 번호는 중복될 수 없습니다.");
        }
        if (numbers.size() != 6) {
            throw new IllegalArgumentException("로또 번호는 6개여야 합니다.");
        }
        numbers.forEach(number -> {
            if (number < 1 || number > 45) {
                throw new IllegalArgumentException(String.format("로또 번호는 %d에서 %d 사이의 정수여야 합니다.", 1, 45));
            }
        });
    }

    private static boolean hasDuplicate(List<Integer> numbers) {
        long uniqueNumbers = numbers.stream()
                .distinct()
                .count();
        return uniqueNumbers != numbers.size();
    }
}
