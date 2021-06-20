package com.premonition.lc.issuance.ui.viewmodels;

import com.premonition.lc.issuance.ui.views.CreateLCView;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewModel;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainViewModel implements ViewModel {
    public void create(Stage stage) {
        stage.setTitle("LC Issuance");
        final Parent parent = FluentViewLoader.fxmlView(CreateLCView.class).load().getView();
        stage.setScene(new Scene(parent));
        stage.show();
    }
}
