package com.premonition.lc.issuance.ui.viewmodels;

import com.premonition.lc.issuance.ui.services.BackendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

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

//    @Test
//    void shouldInvokeServiceOnSave() {
//        final String clientReference = "My awesome LC";
//        viewModel.setClientReference(clientReference);
//
//        viewModel.createLC();
//
//        verify(service).createLC("admin", clientReference);
//    }

//    @Test
//    void shouldNotInvokeServiceIfCreateDisabled() {
//        final String clientReference = "123";
//        viewModel.setClientReference(clientReference);
//
//        assertThrows(IllegalStateException.class, () -> viewModel.createLC(service));
//        verifyNoInteractions(service);
//    }
}