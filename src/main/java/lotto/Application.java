package lotto;

import lotto.global.config.AppConfig;

public class Application {
    public static void main(String[] args) {

        AppConfig appConfig = new AppConfig();
        appConfig.getLottoController().run();
    }
}
