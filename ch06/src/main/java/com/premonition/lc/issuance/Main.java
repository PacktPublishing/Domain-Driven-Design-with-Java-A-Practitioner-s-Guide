package com.premonition.lc.issuance;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.zalando.jackson.datatype.money.MoneyModule;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
//        Application.launch(LCIssuanceUI.class, args);
    }

    @Bean
    MoneyModule moneyModule() {
        return new MoneyModule().withAmountFieldName("value");
    }

}
