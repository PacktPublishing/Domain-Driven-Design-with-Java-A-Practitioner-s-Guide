package com.premonition.lc.issuance.ui.views;

import com.premonition.lc.issuance.utilities.UITest;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;

import static org.testfx.api.FxAssert.verifyThat;

@UITest
public class CreateLCViewTests {

    @Autowired
    private ApplicationContext context;

    @Value("classpath:/ui/create_lc_view.fxml")
    private Resource ui;

    @Start
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(ui.getURL());
        loader.setControllerFactory(context::getBean);
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    @BeforeAll
    static void beforeAll() {
        System.setProperty("testfx.robot", "glass");
        System.setProperty("java.awt.headless", "true");
        System.setProperty("testfx.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.verbose", "sw");
    }

    @Test
    void createShouldBeDisabledWhenWhenClientReferenceIsBlank(FxRobot robot) {
        final String createButton = ".button";
        robot.lookup(".text-field").queryAs(TextField.class).setText("");

        verifyThat(createButton, LabeledMatchers.hasText("Create"));
        verifyThat(createButton, NodeMatchers.isDisabled());
    }

    @Test
    void createShouldBeEnabledWhenWhenClientReferenceIsFourCharactersLong(FxRobot robot) {
        final String createButton = ".button";
        robot.lookup(".text-field").queryAs(TextField.class).setText("Test");

        verifyThat(createButton, LabeledMatchers.hasText("Create"));
        verifyThat(createButton, NodeMatchers.isEnabled());
    }
}
