package com.premonition.lc.issuance;

import com.premonition.lc.issuance.ui.views.MainView;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class LCIssuanceUI extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        ApplicationContextInitializer<GenericApplicationContext> initializer = ac -> {
            ac.registerBean(Application.class, () -> LCIssuanceUI.this);
            ac.registerBean(Parameters.class, this::getParameters);
            ac.registerBean(HostServices.class, this::getHostServices);
        };
        context = new SpringApplicationBuilder()
                .sources(Main.class)
                .initializers(initializer)
                .build()
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage stage) {
        final MainView view = this.context.getBean(MainView.class);
        view.show(stage);
    }

    @Override
    public void stop() {
        context.close();
        Platform.exit();
    }
}
