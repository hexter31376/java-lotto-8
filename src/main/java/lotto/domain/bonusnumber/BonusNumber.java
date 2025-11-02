package lotto.domain.bonusnumber;

public class BonusNumber {

    private static final int MIN_LOTTO_NUMBER = 1;
    private static final int MAX_LOTTO_NUMBER = 45;

    private final Integer bonusNumber;

    private BonusNumber(Integer bonusNumber) {
        this.bonusNumber = bonusNumber;
    }

    public static BonusNumber from(Integer bonusNumber) {
        validate(bonusNumber);
        return new BonusNumber(bonusNumber);
    }

    public Integer get() {
        return bonusNumber;
    }

    private static void validate(Integer bonusNumber) {
        if (bonusNumber == null) {
            throw new IllegalArgumentException("보너스 번호는 null일 수 없습니다.");
        }
        if (bonusNumber < MIN_LOTTO_NUMBER || bonusNumber > MAX_LOTTO_NUMBER) {
            throw new IllegalArgumentException(String.format("보너스 번호는 %d에서 %d 사이(포함)의 정수여야 합니다.",
                    MIN_LOTTO_NUMBER, MAX_LOTTO_NUMBER));
        }
    }
}
