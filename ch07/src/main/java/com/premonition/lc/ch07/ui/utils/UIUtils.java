package com.premonition.lc.ch07.ui.utils;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UIUtils {
    private UIUtils() {

    }

    public static Stage getStage(ActionEvent event) {
        return (Stage) getScene(event).getWindow();
    }

    public static Scene getScene(ActionEvent event) {
        return ((Node) event.getSource()).getScene();
    }
}
