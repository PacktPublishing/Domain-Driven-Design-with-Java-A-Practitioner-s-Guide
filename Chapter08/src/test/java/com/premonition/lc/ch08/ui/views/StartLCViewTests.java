package com.premonition.lc.ch08.ui.views;

import com.premonition.lc.ch08.domain.ApplicantId;
import com.premonition.lc.ch08.domain.LCApplicationId;
import com.premonition.lc.ch08.ui.scopes.LoggedInUserScope;
import com.premonition.lc.ch08.ui.services.BackendService;
import com.premonition.lc.ch08.utilities.RunInUiThread;
import com.premonition.lc.ch08.utilities.UITest;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.MvvmFX;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Init;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;

import static org.mockito.Mockito.when;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

@Log4j2
@UITest
public class StartLCViewTests {

    @MockBean
    private BackendService service;

    @Autowired
    private ApplicationContext context;

    @Init
    public void init() {
        MvvmFX.setCustomDependencyInjector(context::getBean);
    }

    @Start
    public void start(Stage stage) {
        final Parent parent = FluentViewLoader.fxmlView(StartLCView.class)
                .providedScopes(new LoggedInUserScope("admin"))
                .load().getView();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @Test
    void startButtonShouldBeDisabledForBlankValue(FxRobot robot) {
        final String startButton = "#start-button";
        robot.lookup("#client-reference").queryAs(TextField.class).setText("");

        verifyThat(startButton, LabeledMatchers.hasText("Start"));
        verifyThat(startButton, NodeMatchers.isDisabled());
    }

    @Test
    void startButtonShouldBeEnabledForValidValue(FxRobot robot) {
        final String startButton = "#start-button";
        robot.lookup("#client-reference").queryAs(TextField.class).setText("Test");
        verifyThat(startButton, LabeledMatchers.hasText("Start"));
        verifyThat(startButton, NodeMatchers.isEnabled());
    }

    @Test
    @RunInUiThread(false)
    void shouldLaunchLCDetailsWhenCreationIsSuccessful(FxRobot robot) {
        LCApplicationId lcApplicationId = LCApplicationId.randomId();
        final ApplicantId applicantId = ApplicantId.from("admin");
        final String clientReference = "Test";
        when(service.startNewLC(applicantId, clientReference)).thenReturn(lcApplicationId);
        robot.lookup("#client-reference").queryAs(TextField.class).setText(clientReference);
        robot.clickOn("#start-button");

        Mockito.verify(service).startNewLC(applicantId, clientReference);

        verifyThat("#lc-details", isVisible());
        Assertions.assertTrue(((Stage) robot.window(0)).getTitle().contains(lcApplicationId.toString()));
    }

    @Test
    @RunInUiThread(false)
    void shouldStayOnCreateLCScreenOnCreationFailure(FxRobot robot) {
        final String clientReference = "Test";
        when(service.startNewLC(ApplicantId.from("admin"), clientReference)).thenThrow(new RuntimeException("Failed!!"));
        robot.lookup(".text-field").queryAs(TextField.class).setText(clientReference);
        robot.clickOn(".button");
        verifyThat("#start-lc", isVisible());
    }
}
