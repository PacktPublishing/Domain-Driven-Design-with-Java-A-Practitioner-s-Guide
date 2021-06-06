package com.premonition.lc.issuance.ui.views;

import com.premonition.lc.issuance.ui.events.BaseUIEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class BaseView<T extends BaseUIEvent> implements Initializable, ApplicationListener<T> {
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

    @Override
    public void onApplicationEvent(T event) {
        this.stage = event.getStage();
        showView(event);
    }

    protected void setupViewModel(T event) {
    }

    @SneakyThrows
    private void showView(T event) {
        final Parent view = load();
        setupViewModel(event);
        showStage(view);
    }

    private void showStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private Parent load() throws IOException {
        FXMLLoader loader = new FXMLLoader(ui.getURL());
        loader.setControllerFactory(context::getBean);
        return loader.load();
    }

    protected ApplicationContext getContext() {
        return context;
    }

    protected Stage getStage() {
        return stage;
    }

}
