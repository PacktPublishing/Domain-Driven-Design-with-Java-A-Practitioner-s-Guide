package com.premonition.lc.issuance;

import com.premonition.lc.issuance.ui.viewmodels.UserScope;
import com.premonition.lc.issuance.ui.views.MainView;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.spring.MvvmfxSpringApplication;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.zalando.jackson.datatype.money.MoneyModule;

import java.io.InputStream;
import java.util.Objects;

@SpringBootApplication
public class App extends MvvmfxSpringApplication {

    private static final InputStream icon = Objects.requireNonNull(App.class.getResourceAsStream("/lc-icon.png"));

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void startMvvmfx(Stage stage) {
        stage.setTitle("LC Issuance");

        final Parent parent = FluentViewLoader.fxmlView(MainView.class)
                .providedScopes(new UserScope("admin"))
                .load().getView();

        final Image icon = new Image(App.icon);
        stage.getIcons().add(icon);
        final Scene scene = new Scene(parent);
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                stage.close();
            }
        });
        stage.setScene(scene);
        stage.show();
    }

    @Bean
    MoneyModule moneyModule() {
        return new MoneyModule().withAmountFieldName("value");
    }

}