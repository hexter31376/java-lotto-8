package lotto.domain.purchase;

import java.text.NumberFormat;

public class PurchaseAmount {

    private static final int UNIT_CONSTANT = 1000;

    private final int amount;

    private PurchaseAmount(int amount) {
        this.amount = amount;
    }

    public static PurchaseAmount from(int amount) {
        validate(amount);
        return new PurchaseAmount(amount);
    }

    public int getAmount() {
        return amount;
    }

    public int getQuantity() {
        return amount / UNIT_CONSTANT;
    }

    private static void validate(int amount) {
        if (isNotPositiveInteger(amount)) {
            throw new IllegalArgumentException(String.format("구입 금액은 1 이상 양수여야 합니다. 현재 금액: %d원", amount));
        }
        if (amount > 100000) {
            String formattedAmount = NumberFormat.getInstance().format(amount);
            throw new IllegalArgumentException(
                    String.format("구입 금액은 최대 100,000원까지 가능합니다. 현재 금액: %s원", formattedAmount)
            );
        }
        if (isNotUnitMultiple(amount)) {
            String formattedUnit = NumberFormat.getInstance().format(UNIT_CONSTANT);
            String formattedAmount = NumberFormat.getInstance().format(amount);
            throw new IllegalArgumentException(
                    String.format("구입 금액은 %s원 단위여야 합니다. 현재 금액: %s원",
                            formattedUnit, formattedAmount)
            );
        }
    }

    private static boolean isNotUnitMultiple(int amount) {
        return amount % UNIT_CONSTANT != 0;
    }

    private static boolean isNotPositiveInteger(int amount) {
        return !(amount >= 1);
    }
}