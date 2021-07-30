package com.premonition.lc.ch06;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
class ApiApplicationTests {

    @Test
    void shouldNotContainErrorLevelLogMessages(CapturedOutput output) {
        assertThat(output.toString()).doesNotContainPattern("(?i)ERROR");
    }

    @Test
    void shouldStartSuccessfully(CapturedOutput output) throws Exception {
        assertThat(output.toString()).containsPattern("JVM running for");
    }
}
