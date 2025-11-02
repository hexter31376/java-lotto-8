package lotto.application.service.enums;

public enum Rank {
    FIRST(6, 2_000_000_000, false),
    SECOND(5, 30_000_000, true),
    THIRD(5, 1_500_000, false),
    FOURTH(4, 50_000, false),
    FIFTH(3, 5_000, false),
    NONE(0, 0, false);

    private final int matchCount;
    private final long prizeAmount;
    private final boolean isBonusRequire;

    Rank(int matchCount, long prizeAmount, boolean isBonusRequire) {
        this.matchCount = matchCount;
        this.prizeAmount = prizeAmount;
        this.isBonusRequire = isBonusRequire;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public long getPrizeAmount() {
        return prizeAmount;
    }

    public boolean isBonusRequire() {
        return isBonusRequire;
    }
}
