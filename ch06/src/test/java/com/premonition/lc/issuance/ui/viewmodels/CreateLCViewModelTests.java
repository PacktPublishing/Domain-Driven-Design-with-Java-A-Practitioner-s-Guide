package com.premonition.lc.issuance.ui.viewmodels;

import com.premonition.lc.issuance.ui.services.BackendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.stream.Stream;

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

    @Parameters(name = "{index}: For client reference \"{0}\" create disabled should be {1}")
    public static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of(null, true),
                Arguments.of("", true),
                Arguments.of(" ", true),
                Arguments.of("      ", true),
                Arguments.of("123", true),
                Arguments.of("1234", false),
                Arguments.of("12345", false)
        );
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