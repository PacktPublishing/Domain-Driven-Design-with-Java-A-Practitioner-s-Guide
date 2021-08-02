package com.premonition.lc.ch06.ui.views;

import com.premonition.lc.ch06.domain.LCApplicationId;
import com.premonition.lc.ch06.ui.scopes.LCScope;
import com.premonition.lc.ch06.ui.services.BackendService;
import com.premonition.lc.ch06.ui.viewmodels.LoggedInUserScope;
import com.premonition.lc.ch06.utilities.UITest;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.MvvmFX;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Init;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import static org.testfx.api.FxAssert.verifyThat;

@Log4j2
@UITest
public class LCDetailsViewTests {

    @MockBean
    private BackendService service;

    @Autowired
    private ApplicationContext context;
    private LCApplicationId lcApplicationId = LCApplicationId.randomId();

    @Init
    public void init() {
        MvvmFX.setCustomDependencyInjector(context::getBean);
    }

    @Start
    public void start(Stage stage) {
        final Parent parent = FluentViewLoader.fxmlView(LCDetailsView.class)
                .providedScopes(new LoggedInUserScope("admin"), new LCScope(lcApplicationId, "Test"))
                .load().getView();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @Test
    void shouldLaunchScreenWithClientReferenceAndApplicationIdfromLCScope(FxRobot robot) {
        verifyThat("#client-reference", LabeledMatchers.hasText("Test"));
    }

    @AfterEach
    public void afterAll(FxRobot robot) {
        Platform.runLater(() -> {
            ((Stage)robot.window(0)).close();
        });
    }

}