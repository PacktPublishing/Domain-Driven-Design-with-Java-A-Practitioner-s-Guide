package com.premonition.lc.issuance.ui.views;

import com.premonition.lc.issuance.ui.services.CreateLCService;
import com.premonition.lc.issuance.ui.viewmodels.CreateLCViewModel;
import com.premonition.lc.issuance.utilities.UITest;
import de.saxsys.mvvmfx.FluentViewLoader;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.testfx.api.FxAssert.verifyThat;

@UITest
public class CreateLCViewTests {

    @MockBean
    private CreateLCService service;

    @Autowired
    private ApplicationContext context;

//    @Init
//    public void init() throws Exception {
//        FxToolkit.registerStage(() -> new Stage());
//    }

    @Start
    public void start(Stage stage)  {
        final Parent parent = FluentViewLoader.fxmlView(CreateLCView.class).viewModel(context.getBean(CreateLCViewModel.class)).load().getView();
        stage.setScene(new Scene(parent));
        stage.show();
    }

//    @Stop
//    public void stop() throws Exception {
//        FxToolkit.hideStage();
//    }

    @Test
    void createShouldUpdateCreateButtonStatusBasedOnClientReferenceAsBlank(FxRobot robot) {
        final String createButton = ".button";
        robot.lookup(".text-field").queryAs(TextField.class).setText("");

        verifyThat(createButton, LabeledMatchers.hasText("Create"));
        verifyThat(createButton, NodeMatchers.isDisabled());
    }

    @Test
    void createShouldUpdateCreateButtonStatusBasedOnClientReferenceAsFilledIn(FxRobot robot) {
        final String createButton = ".button";
        robot.lookup(".text-field").queryAs(TextField.class).setText("Test");
        verifyThat(createButton, LabeledMatchers.hasText("Create"));
        verifyThat(createButton, NodeMatchers.isEnabled());
    }

    @Test
    void shouldInvokeServiceWhenCreateLCIsPressed(FxRobot robot) {
        final String clientReference = "Test";
        robot.lookup(".text-field").queryAs(TextField.class).setText(clientReference);
        robot.clickOn(".button");
        Platform.runLater(() -> {
            Mockito.verify(service).createLC(clientReference);
            robot.clickOn("OK");
        });
    }

    @Test
    void shouldShowSuccessDialogWhenCreateLCIsCreated(FxRobot robot) {
        final String clientReference = "Test";
        robot.lookup(".text-field").queryAs(TextField.class).setText(clientReference);
        robot.clickOn(".button");
        Platform.runLater(() -> {
            Stage actualAlertDialog = getTopModalStage(robot);
            assertNotNull(actualAlertDialog);
            DialogPane pane = (DialogPane) actualAlertDialog.getScene().getRoot();
            assert(pane.getContentText().contains("Successfully"));
            robot.clickOn("OK");
        });
    }
    private javafx.stage.Stage getTopModalStage(FxRobot robot) {
        // Get a list of windows but ordered from top[0] to bottom[n] ones.
        // It is needed to get the first found modal window.
        final List<Window> allWindows = new ArrayList<>(robot.robotContext().getWindowFinder().listWindows());
        Collections.reverse(allWindows);

        return (javafx.stage.Stage) allWindows
                .stream()
                .filter(window -> window instanceof javafx.stage.Stage)
                .filter(window -> ((javafx.stage.Stage) window).getModality() == Modality.APPLICATION_MODAL)
                .findFirst()
                .orElse(null);
    }

}
