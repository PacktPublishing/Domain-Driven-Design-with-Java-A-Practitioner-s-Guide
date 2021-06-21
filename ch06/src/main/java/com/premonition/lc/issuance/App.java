package com.premonition.lc.issuance;

import com.premonition.lc.issuance.ui.views.MainView;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.spring.MvvmfxSpringApplication;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.zalando.jackson.datatype.money.MoneyModule;

@SpringBootApplication
public class App extends MvvmfxSpringApplication {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void startMvvmfx(Stage stage) throws Exception {
        stage.setTitle("LC Issuance");

        final Parent parent = FluentViewLoader.fxmlView(MainView.class).load().getView();

        stage.setScene(new Scene(parent));
        stage.show();
    }

    @Bean
    MoneyModule moneyModule() {
        return new MoneyModule().withAmountFieldName("value");
    }

}