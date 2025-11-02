package lotto.View.Output;

import lotto.application.service.enums.Rank;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class OutputView {

    private static final String PURCHASE_COUNT_TEMPLATE = "개를 구매했습니다.";
    private static final String LOTTO_TICKET_VIEW_TEMPLATE = "%s";
    private static final String LOTTO_STATISTICS_VIEW_TEMPLATE = "당첨 통계";
    private static final String EM_DASH = "---";
    private static final String FINAL_PROFIT_RATE_VIEW_TEMPLATE = "총 수익률은 %,.1f%%입니다.";
    public static final String ERROR_TAG = "[ERROR] ";
    private static final String NEW_LINE = System.lineSeparator();

    /**
     * 로또 티켓 문자열 리스트를 받아 출력하는 메서드
     *
     * @param lottoTickets 로또 티켓 문자열 리스트 ex) "[1, 2, 3, 4, 5, 6]"
     */
    public void printLottoTickets(List<String> lottoTickets) {
        StringBuilder output = new StringBuilder(256)
                .append(NEW_LINE)
                .append(lottoTickets.size()).append(PURCHASE_COUNT_TEMPLATE).append(NEW_LINE);

        for (String ticket : lottoTickets) {
            output.append(String.format(LOTTO_TICKET_VIEW_TEMPLATE, ticket)).append(NEW_LINE);
        }
        System.out.print(output.toString());
    }

    /**
     * 로또 통계 출력 메서드
     *
     * @param winningCounts 각 등수별 당첨 개수 맵 ex) { RankTemplate.FIRST: 2, RankTemplate.SECOND: 0, ... }
     * @param profitRate    수익률 문자열 ex) "60.2"
     */
    //todo : 로직 가독성 증가 필요
    public void printGameStatistics(Map<Rank, Integer> winningCounts, double profitRate) {
        StringBuilder output = new StringBuilder(256)
                .append(NEW_LINE)
                .append(LOTTO_STATISTICS_VIEW_TEMPLATE).append(NEW_LINE)
                .append(EM_DASH).append(NEW_LINE);

        Arrays.stream(Rank.values())
                .filter(rank -> rank != Rank.NONE)
                .sorted(Comparator.comparingInt(Rank::getMatchCount) // 매치 개수 내림차순으로 정렬하고
                        .thenComparing(Comparator.comparing(Rank::isBonusRequire))) // 매치 개수가 같으면 보너스 필요가 있는 템플릿이 먼저 오도록 함
                .forEach(rank -> {
                    int count = winningCounts.getOrDefault(rank, 0); // 널러블 방지 및 0개 처리
                    // Rank에서 RankTemplate 매핑은 별도 헬퍼로 캡슐화
                    String line = getTemplateForRank(rank).format(count, rank.getPrizeAmount());
                    output.append(line).append(NEW_LINE);
                });
        output.append(NEW_LINE)
              .append(String.format(FINAL_PROFIT_RATE_VIEW_TEMPLATE, profitRate));
        System.out.print(output.toString());
    }

    /**
     * Rank에 해당하는 RankTemplate을 반환하는 헬퍼 메서드
     *
     * @param rank Rank 열거형 값
     * @return 해당 Rank에 매핑되는 RankTemplate
     */
    private RankTemplate getTemplateForRank(Rank rank) {
        try {
            return RankTemplate.valueOf(rank.name());
        } catch (Exception e) {
            throw new IllegalStateException("출력 불가 랭크가 발생했습니다. 랭크 : " + rank);
        }
    }
}
