package com.premonition.lc.ch07.ui.views;

import com.premonition.lc.ch07.domain.queries.AllQuery;
import com.premonition.lc.ch07.domain.queries.LCView;
import com.premonition.lc.ch07.ui.utils.UIUtils;
import com.premonition.lc.ch07.ui.viewmodels.MainViewModel;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Component;

@Component
public class MainView implements FxmlView<MainViewModel> {

    private final QueryGateway gateway;

    @InjectViewModel
    private MainViewModel viewModel;

    public MainView(QueryGateway gateway) {
        this.gateway = gateway;
    }

    public void create(ActionEvent event) {
        Stage stage = UIUtils.getStage(event);
        stage.titleProperty().bind(viewModel.stageTitleProperty());
        final Parent parent = FluentViewLoader.fxmlView(StartLCView.class)
                .providedScopes(viewModel.getUserScope())
                .load()
                .getView();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public void search(ActionEvent event) {
        gateway.query(new AllQuery(), ResponseTypes.multipleInstancesOf(LCView.class))
                .thenAccept(System.out::println);
    }
}
