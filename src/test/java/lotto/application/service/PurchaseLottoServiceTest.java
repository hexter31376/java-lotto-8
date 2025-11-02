package lotto.application.service;

import lotto.application.dto.request.PurchaseRequest;
import lotto.application.dto.response.PurchasedLottoResponse;
import lotto.infrastructure.repository.LottoRepository;
import lotto.infrastructure.repository.PurchaseAmountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PurchaseLottoServiceTest {

    private PurchaseAmountRepository purchaseRepo;
    private LottoRepository lottoRepo;
    private PurchaseLottoService service;

    @BeforeEach
    void setUp() {
        purchaseRepo = new PurchaseAmountRepository();
        lottoRepo = new LottoRepository();
        service = new PurchaseLottoService(purchaseRepo, lottoRepo);
    }

    @DisplayName("정상 구입 시 응답과 저장소에 로또 및 구입금액이 저장된다")
    @Test
    void purchaseLottos_success() {
        PurchaseRequest request = new PurchaseRequest("1000");
        PurchasedLottoResponse response = service.purchaseLottos(request);

        // 응답은 1개의 로또 문자열을 포함해야 함
        assertThat(response.lottoNumbers()).hasSize(1);
        // 레파지토리에 저장된 로또 개수는 1
        assertThat(lottoRepo.findAll()).hasSize(1);
        // 구매 금액이 저장되었는지 확인
        assertThat(purchaseRepo.findById(1L).getAmount()).isEqualTo(1000);
    }

    @DisplayName("잘못된 구입 금액(단위 불일치)이면 예외가 발생한다")
    @Test
    void purchaseLottos_invalidAmount() {
        PurchaseRequest request = new PurchaseRequest("1500");
        assertThatThrownBy(() -> service.purchaseLottos(request))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
