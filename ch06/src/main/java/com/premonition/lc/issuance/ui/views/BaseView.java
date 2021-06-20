package com.premonition.lc.issuance.ui.views;

import com.premonition.lc.issuance.ui.viewmodels.ViewModel;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class BaseView<T extends ViewModel> implements Initializable {
    private final Resource ui;
    private final ApplicationContext context;
    private Stage stage;

    protected BaseView(ApplicationContext context, Resource ui) {
        this.ui = ui;
        this.context = context;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private void showStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @SneakyThrows
    private Parent load() {
        FXMLLoader loader = new FXMLLoader(ui.getURL());
        loader.setControllerFactory(context::getBean);
        return loader.load();
    }

    protected Stage getStage() {
        return stage;
    }

    public void show(Stage stage, T viewModel) {
        this.stage = stage;
        final Parent view = load();
        showStage(view);
        if (viewModel != null) {
            setupViewModel(viewModel);
        }
    }

    protected void setupViewModel(T viewModel) {

    }

    public void show(Stage stage) {
        show(stage, null);
    }
}
