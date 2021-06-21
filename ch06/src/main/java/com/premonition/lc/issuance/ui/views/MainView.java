package com.premonition.lc.issuance.ui.views;

import com.premonition.lc.issuance.ui.scopes.LCScope;
import com.premonition.lc.issuance.ui.viewmodels.MainViewModel;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class MainView implements FxmlView<MainViewModel> {

    @InjectViewModel
    private MainViewModel viewModel;

    public void initialize() {
    }

    public void create(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("LC Issuance");
        final Parent parent = FluentViewLoader.fxmlView(CreateLCView.class)
                .providedScopes(new LCScope())
                .load().getView();
        stage.setScene(new Scene(parent));
        stage.show();
    }

}
