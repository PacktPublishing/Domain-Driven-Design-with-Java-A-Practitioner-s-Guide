package com.premonition.lc.issuance.utilities;

import javafx.application.Platform;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;

public class SafeApplicationExtension extends ApplicationExtension implements BeforeAllCallback, InvocationInterceptor {
    @Override
    public void interceptTestMethod(Invocation<Void> invocation,
                                    ReflectiveInvocationContext<Method> invocationContext,
                                    ExtensionContext extensionContext) throws Throwable {
        if (Platform.isFxApplicationThread()) {
            invocation.proceed();
        } else {
            AtomicReference<Throwable> throwable = new AtomicReference<>();
            Platform.runLater(() -> {
                try {
                    invocation.proceed();

                } catch (Throwable t) {
                    throwable.set(t);
                }
            });
            WaitForAsyncUtils.waitForFxEvents();
            final Throwable t = throwable.get();
            if (t != null) {
                throw t;
            }
        }
    }

    @Override
    public void beforeAll(ExtensionContext context) {
        final Boolean headless = context.getTestClass()
                .map(c -> c.getAnnotation(UITest.class))
                .map(UITest::headless)
                .orElse(true);

        if (headless) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("java.awt.headless", "true");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
        }
    }
}
