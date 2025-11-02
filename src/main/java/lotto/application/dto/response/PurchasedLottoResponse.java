package lotto.application.dto.response;

import java.util.List;

public record PurchasedLottoResponse(
        List<String> lottoNumbers
) {
}
