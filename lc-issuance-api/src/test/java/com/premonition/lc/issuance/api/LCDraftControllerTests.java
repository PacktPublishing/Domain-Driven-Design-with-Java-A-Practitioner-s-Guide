package com.premonition.lc.issuance.api;

import com.premonition.lc.issuance.domain.commands.CreateLCDraftCommand;
import org.apache.commons.io.IOUtils;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;

import static com.premonition.lc.issuance.api.LCDraftController.RESOURCE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = LCDraftController.class)
public class LCDraftControllerTests {
    private MockMvc mockMvc;

    @MockBean
    private CommandGateway gateway;

    @BeforeEach
    void setUp(WebApplicationContext context) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @Test
    void shouldInitiateLCDraft() throws Exception {
        mockMvc.perform(post("/lc-drafts")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(asString("create-lc-draft-request.json")))
                .andExpect(status().isCreated())
                .andExpect(header().string(LOCATION, matchesPattern(RESOURCE + "/.*")));

        ArgumentCaptor<CreateLCDraftCommand> captor = forClass(CreateLCDraftCommand.class);
        verify(gateway).sendAndWait(captor.capture());
        assertThat(captor.getValue().getId()).isNotNull();
    }

    private static String asString(String path) throws IOException {
        return IOUtils.resourceToString(asAbsolute(path),
                Charset.defaultCharset());
    }

    private static String asAbsolute(String path) {
        if (path.startsWith("/")) {
            return path;
        }
        return "/" + path;
    }
}
