package lotto.application.service;

import camp.nextstep.edu.missionutils.Randoms;
import lotto.application.dto.request.PurchaseRequest;
import lotto.application.dto.response.PurchasedLottoResponse;
import lotto.domain.lotto.Lotto;
import lotto.domain.lotto.Lottos;
import lotto.domain.purchase.PurchaseAmount;
import lotto.infrastructure.repository.LottoRepository;
import lotto.infrastructure.repository.PurchaseAmountRepository;

import java.util.*;

public class PurchaseLottoService {

    private static final int LOTTO_NUMBER_SIZE = 6;
    private static final int MIN_LOTTO_NUMBER = 1;
    private static final int MAX_LOTTO_NUMBER = 45;

    private final PurchaseAmountRepository purchaseAmountRepository;
    private final LottoRepository lottoRepository;

    public PurchaseLottoService(PurchaseAmountRepository purchaseAmountRepository,
                                LottoRepository lottoRepository) {
        this.purchaseAmountRepository = purchaseAmountRepository;
        this.lottoRepository = lottoRepository;
    }

    /**
     * 로또 구입
     * @param request 구입 요청 정보
     * @return 구입한 로또 번호 목록
     */
    public PurchasedLottoResponse purchaseLottos(PurchaseRequest request) {
        // 1. 구입 금액에 따른 로또 수량 계산 및 로또 생성
        PurchaseAmount purchaseAmount = PurchaseAmount.from(Integer.parseInt(request.price()));
        List<Lotto> lottoTickets = generateLottoTicketsByCount(purchaseAmount.getQuantity());
        // 2. 로또 번호 문자열 리스트 생성
        List<String> lottoNumbers = lottoTickets.stream()
                .map(lotto -> lotto.getNumbers().toString())
                .toList();
        // 3. 생성된 로또와 구입 금액 저장
        Lottos lottos = Lottos.from(lottoTickets);
        lottoRepository.saveAll(lottos.getLottos());
        purchaseAmountRepository.save(purchaseAmount);

        // 4. 응답 반환
        return new PurchasedLottoResponse(lottoNumbers);
    }

    /**
     * 주어진 수량만큼의 로또를 생성하여 Lottos 객체로 반환합니다.
     *
     * @param quantity 생성할 로또의 수량
     * @return 생성된 Lotto 객체들의 리스트
     */
    private List<Lotto> generateLottoTicketsByCount(Integer quantity) {
        Set<Lotto> lottoTemps = new LinkedHashSet<>();

        for (int i = 0; i < quantity; i++) {
            lottoTemps.add(generateLotto());
        }

        return lottoTemps.stream().toList();
    }

    /**
     * 무작위로 생성된 로또 번호로 Lotto 객체를 생성하여 반환합니다.
     *
     * @return 생성된 Lotto 객체
     */
    private static Lotto generateLotto() {
        Set<Integer> lottoTemp = new HashSet<>();

        while (lottoTemp.size() < LOTTO_NUMBER_SIZE) {
            List<Integer> number = Randoms.pickUniqueNumbersInRange(MIN_LOTTO_NUMBER, MAX_LOTTO_NUMBER, LOTTO_NUMBER_SIZE);
            lottoTemp.addAll(number);
        }

        return new Lotto(lottoTemp.stream()
                .sorted()
                .toList()
        );
    }
}
