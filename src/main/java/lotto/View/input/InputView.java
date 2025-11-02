package lotto.View.input;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    private static final String LOTTO_PERCHASE_VIEW_TEMPLATE = "구입금액을 입력해 주세요.";
    private static final String LOTTO_LUCKY_NUMBER_VIEW_TEMPLATE = "당첨 번호를 입력해 주세요.";
    private static final String LOTTO_BONUS_NUMBER_VIEW_TEMPLATE = "보너스 번호를 입력해 주세요.";
    private static final String NEW_LINE = System.lineSeparator();

    /**
     * 사용자로부터 로또 구입 금액을 입력받는 메서드
     *
     * @return 사용자가 입력한 로또 구입 금액 문자열 ex) "5000"
     */
    public String readPurchasePrice() {
        System.out.println(LOTTO_PERCHASE_VIEW_TEMPLATE);
        return Console.readLine();
    }

    /**
     * 사용자로부터 당첨 번호를 입력받는 메서드
     *
     * @return 사용자가 입력한 당첨 번호 문자열 ex) "1, 2, 3, 4, 5, 6"
     */
    public String readWinningNumbers() {
        StringBuilder output = new StringBuilder(256)
                .append(NEW_LINE)
                .append(LOTTO_LUCKY_NUMBER_VIEW_TEMPLATE);
        System.out.println(output.toString());
        return Console.readLine();
    }

    /**
     * 사용자로부터 보너스 번호를 입력받는 메서드
     *
     * @return 사용자가 입력한 보너스 번호 문자열 ex) "7"
     */
    public String readBonusNumber() {
        StringBuilder output = new StringBuilder(256)
                .append(NEW_LINE)
                .append(LOTTO_BONUS_NUMBER_VIEW_TEMPLATE);
        System.out.println(output.toString());
        return Console.readLine();
    }
}
