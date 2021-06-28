package com.premonition.lc.issuance.ui.views;

import com.premonition.lc.issuance.ui.services.BackendService;
import com.premonition.lc.issuance.ui.viewmodels.CreateLCViewModel;
import com.premonition.lc.issuance.ui.viewmodels.UserScope;
import com.premonition.lc.issuance.utilities.RunInUiThread;
import com.premonition.lc.issuance.utilities.UITest;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.MvvmFX;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Init;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

@Log4j2
@UITest
public class CreateLCViewTests {

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
        final Parent parent = FluentViewLoader.fxmlView(CreateLCView.class)
                .providedScopes(new UserScope("admin"))
                .load().getView();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @Test
    void createShouldUpdateCreateButtonStatusBasedOnClientReferenceAsBlank(FxRobot robot) {
        final String createButton = "#create-button";
        robot.lookup("#client-reference").queryAs(TextField.class).setText("");

        verifyThat(createButton, LabeledMatchers.hasText("Create"));
        verifyThat(createButton, NodeMatchers.isDisabled());
    }

    @Test
    void createShouldUpdateCreateButtonStatusBasedOnClientReferenceAsFilledIn(FxRobot robot) {
        final String createButton = "#create-button";
        robot.lookup("#client-reference").queryAs(TextField.class).setText("Test");
        verifyThat(createButton, LabeledMatchers.hasText("Create"));
        verifyThat(createButton, NodeMatchers.isEnabled());
    }

    @Test
    @RunInUiThread(false)
    void shouldLaunchLCDetailsWhenCreationIsSuccessful(FxRobot robot) {
        final String clientReference = "Test";
        robot.lookup("#client-reference").queryAs(TextField.class).setText(clientReference);
        robot.clickOn("#create-button");
        Mockito.verify(service).createLC("admin", clientReference);
        verifyThat("#lc-details", isVisible());
    }

    @Test
    @RunInUiThread(false)
    void shouldStayOnCreateLCScreenOnCreationFailure(FxRobot robot) {
        final String clientReference = "Test";
        Mockito.when(service.createLC("admin", clientReference)).thenThrow(new RuntimeException("Failed!!"));
        robot.lookup(".text-field").queryAs(TextField.class).setText(clientReference);
        robot.clickOn(".button");
        verifyThat("#create-lc", isVisible());
    }
}
