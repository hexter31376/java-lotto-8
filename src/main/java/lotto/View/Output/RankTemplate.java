package lotto.View.Output;

public enum RankTemplate {

    FIRST("6개 일치 (%,d원) - %,d개"),
    SECOND("5개 일치, 보너스 볼 일치 (%,d원) - %,d개"),
    THIRD("5개 일치 (%,d원) - %,d개"),
    FOURTH("4개 일치 (%,d원) - %,d개"),
    FIFTH("3개 일치 (%,d원) - %,d개");


    private final String template;

    RankTemplate(String template) {
        this.template = template;
    }

    /**
     * 해당 랭크의 당첨 개수를 포맷팅하여 반환합니다. (역순 포맷)
     *
     * @param winningCount 당첨 개수
     * @return 당첨 개수가 포맷팅된 문자열
     */
    public String format(int winningCount, long prizeMoney) {
        // Use %d in the template; String.format accepts long for %d as well.
        return String.format(template, prizeMoney, winningCount);
    }
}