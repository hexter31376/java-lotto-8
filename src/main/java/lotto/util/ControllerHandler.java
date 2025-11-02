package lotto.util;

import java.util.function.Supplier;

public class ControllerHandler {
    
    private static final String ERROR_TEMPLATE = "[ERROR] ";

    private ControllerHandler() {
    }

    public static void retryAtException(Runnable action) {
        while (true) {
            try {
                action.run();
                return; // 성공하면 반복 종료
            } catch (IllegalArgumentException e) {
                System.out.println(ERROR_TEMPLATE + e.getMessage());
            } catch (Exception e) {
                System.out.println(ERROR_TEMPLATE + "예기치 못한 오류가 발생했습니다.");
                throw e;
            }
        }
    }
}
