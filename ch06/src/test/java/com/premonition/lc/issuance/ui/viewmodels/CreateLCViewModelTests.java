package com.premonition.lc.issuance.ui.viewmodels;

import com.premonition.lc.issuance.ui.services.BackendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CreateLCViewModelTests {

    @Mock
    private BackendService service;

    private CreateLCViewModel viewModel;

    @BeforeEach
    void before() {
        viewModel = new CreateLCViewModel(4, service);
        viewModel.setUserScope(new UserScope("admin"));
    }

    @Parameters(name = "{index}: For client reference '{0}' create disabled should be {1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {null,    true},
                {"",      true},
                {"123",   true},
                {"1234",  false},
                {"12345", false}
        });
    }

    @ParameterizedTest
    @MethodSource("data")
    void shouldTest(String clientReference, boolean expected) {
        viewModel.setClientReference(clientReference);
        assertThat(viewModel.getCreateDisabled()).isEqualTo(expected);
    }

    @Test
    void shouldNotEnableCreateByDefault() {
        assertThat(viewModel.getCreateDisabled()).isTrue();
    }

    @Test
    void shouldNotEnableCreateIfClientReferenceLesserThanMinimumLength() {
        viewModel.setClientReference("123");
        assertThat(viewModel.getCreateDisabled()).isTrue();
    }

    @Test
    void shouldEnableCreateIfClientReferenceEqualToMinimumLength() {
        viewModel.setClientReference("1234");
        assertThat(viewModel.getCreateDisabled()).isFalse();
    }

    @Test
    void shouldEnableCreateIfClientReferenceGreaterThanMinimumLength() {
        viewModel.setClientReference("12345");
        assertThat(viewModel.getCreateDisabled()).isFalse();
    }
}