package org.bassani.examplebff.service;

import org.bassani.examplebff.client.NotifyClient;
import org.bassani.examplemodellib.domain.request.RecipientMessageReadUnReadRequest;
import org.bassani.examplemodellib.exception.NullParameterException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.bassani.examplebff.mock.RecipientMessageReadUnReadRequestMock.getRecipientMessageReadUnReadRequest_complete;
import static org.bassani.examplebff.mock.RecipientMessageReadUnReadRequestMock.getRecipientMessageReadUnReadRequest_idsEmpty;
import static org.bassani.examplebff.mock.RecipientMessageReadUnReadRequestMock.getRecipientMessageReadUnReadRequest_withoutFlagRead;
import static org.bassani.examplebff.mock.RecipientMessageReadUnReadRequestMock.getRecipientMessageReadUnReadRequest_withoutIds;
import static org.bassani.examplebff.mock.RecipientMessageReadUnReadRequestMock.getRecipientMessageReadUnReadRequest_withoutKCUserID;
import static org.bassani.examplemodellib.exception.NullParameterException.DESCRIPTION;
import static org.bassani.examplemodellib.exception.NullParameterException.MESSAGE;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RecipientServiceTest {

    @InjectMocks private RecipientService service;

    @Mock private NotifyClient repository;

    @Test
    public void testDefineMessageReadUnread_whenRequestIsCorrect_thenUpdate() {
        //scenario
        RecipientMessageReadUnReadRequest request = getRecipientMessageReadUnReadRequest_complete();
        Mockito.doNothing().when(repository).defineMessageReadUnread(any(RecipientMessageReadUnReadRequest.class));

        //action
        service.defineMessageReadUnread(request);

        //validation
        verify(repository).defineMessageReadUnread(any(RecipientMessageReadUnReadRequest.class));

        verifyNoMoreInteractions(repository);
    }

    @Test
    public void testDefineMessageReadUnread_whenIDsNull_thenThrowException() {
        //scenario
        RecipientMessageReadUnReadRequest request = getRecipientMessageReadUnReadRequest_withoutIds();

        //action
        NullParameterException exception = assertThrows(NullParameterException.class,
                () -> service.defineMessageReadUnread(request));

        //validation
        assertEquals(exception.getMessage(), MESSAGE);
        assertEquals(exception.getDescription(), DESCRIPTION);

        verify(repository, never()).defineMessageReadUnread(any(RecipientMessageReadUnReadRequest.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void testDefineMessageReadUnread_whenIDsEmpty_thenThrowException() {
        //scenario
        RecipientMessageReadUnReadRequest request = getRecipientMessageReadUnReadRequest_idsEmpty();

        //action
        NullParameterException exception = assertThrows(NullParameterException.class,
                () -> service.defineMessageReadUnread(request));

        //validation
        assertEquals(exception.getMessage(), MESSAGE);
        assertEquals(exception.getDescription(), DESCRIPTION);

        verify(repository, never()).defineMessageReadUnread(any(RecipientMessageReadUnReadRequest.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void testDefineMessageReadUnread_whenKCUserIDIsNull_thenThrowException() {
        //scenario
        RecipientMessageReadUnReadRequest request = getRecipientMessageReadUnReadRequest_withoutKCUserID();

        //action
        NullParameterException exception = assertThrows(NullParameterException.class,
                () -> service.defineMessageReadUnread(request));

        //validation
        assertEquals(exception.getMessage(), MESSAGE);
        assertEquals(exception.getDescription(), DESCRIPTION);

        verify(repository, never()).defineMessageReadUnread(any(RecipientMessageReadUnReadRequest.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void testDefineMessageReadUnread_whenFlagReadIsNull_thenThrowException() {
        //scenario
        RecipientMessageReadUnReadRequest request = getRecipientMessageReadUnReadRequest_withoutFlagRead();

        //action
        NullParameterException exception = assertThrows(NullParameterException.class,
                () -> service.defineMessageReadUnread(request));

        //validation
        assertEquals(exception.getMessage(), MESSAGE);
        assertEquals(exception.getDescription(), DESCRIPTION);

        verify(repository, never()).defineMessageReadUnread(any(RecipientMessageReadUnReadRequest.class));
        verifyNoMoreInteractions(repository);
    }

}