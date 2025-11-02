package lotto.infrastructure.repository;

import lotto.domain.purchase.PurchaseAmount;

import java.util.LinkedHashMap;
import java.util.Map;

public class PurchaseAmountRepository {
    private static final Map<Long, PurchaseAmount> purchases = new LinkedHashMap<>();

    public void save(PurchaseAmount purchaseAmount) {
        long id = purchases.size() + 1L;
        purchases.put(id, purchaseAmount);
    }

    public PurchaseAmount findById(Long id) {
        return purchases.get(id);
    }
}
