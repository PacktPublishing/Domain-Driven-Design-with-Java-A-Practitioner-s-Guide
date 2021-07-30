package com.premonition.lc.ch06.ui.views;

import com.premonition.lc.ch06.ui.utils.UIUtils;
import com.premonition.lc.ch06.ui.viewmodels.MainViewModel;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class MainView implements FxmlView<MainViewModel> {

    @InjectViewModel
    private MainViewModel viewModel;

    public void create(ActionEvent event) {
        Stage stage = UIUtils.getStage(event);
        stage.setTitle("LC Issuance");
        final Parent parent = FluentViewLoader.fxmlView(StartLCView.class)
                .providedScopes(viewModel.getUserScope())
                .load()
                .getView();
        stage.setScene(new Scene(parent));
        stage.show();
    }

}
