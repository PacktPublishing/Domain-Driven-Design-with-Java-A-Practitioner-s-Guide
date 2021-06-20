package com.premonition.lc.issuance;

import com.premonition.lc.issuance.ui.views.MainView;
import de.saxsys.mvvmfx.FluentViewLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("LC Issuance");

        final Parent parent = FluentViewLoader.fxmlView(MainView.class).load().getView();

        stage.setScene(new Scene(parent));
        stage.show();
    }

}