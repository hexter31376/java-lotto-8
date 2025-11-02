package lotto.application.dto.response;

import lotto.application.service.enums.Rank;

import java.util.Map;

public record LottoGameResultResponse(
        Map<Rank, Integer> rankWinningCounts,
        double yield
) {
}
