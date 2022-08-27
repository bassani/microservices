package org.bassani.examplebff.mock;

import org.bassani.examplemodellib.domain.request.RecipientMessageReadUnReadRequest;

import java.util.Arrays;
import java.util.Collections;

public class RecipientMessageReadUnReadRequestMock {


    public static RecipientMessageReadUnReadRequest getRecipientMessageReadUnReadRequest_complete() {
        return RecipientMessageReadUnReadRequest.builder()
                .ids(Arrays.asList(1L, 2L, 3L))
                .read(true)
                .keycloakUserId("553046d0-6365-46ca-999a-8c6a27c87cb8")
                .build();
    }

    public static RecipientMessageReadUnReadRequest getRecipientMessageReadUnReadRequest_withoutIds() {
        return RecipientMessageReadUnReadRequest.builder()
                .ids(null)
                .read(true)
                .keycloakUserId("553046d0-6365-46ca-999a-8c6a27c87cb8")
                .build();

    }

    public static RecipientMessageReadUnReadRequest getRecipientMessageReadUnReadRequest_idsEmpty() {
        return RecipientMessageReadUnReadRequest.builder()
                .ids(Collections.emptyList())
                .read(true)
                .keycloakUserId("553046d0-6365-46ca-999a-8c6a27c87cb8")
                .build();
    }
    public static RecipientMessageReadUnReadRequest getRecipientMessageReadUnReadRequest_withoutKCUserID() {
        return RecipientMessageReadUnReadRequest.builder()
                .ids(Arrays.asList(1L, 2L, 3L))
                .read(true)
                .keycloakUserId(null)
                .build();
    }
    public static RecipientMessageReadUnReadRequest getRecipientMessageReadUnReadRequest_withoutFlagRead() {
        return RecipientMessageReadUnReadRequest.builder()
                .ids(Arrays.asList(1L, 2L, 3L))
                .read(null)
                .keycloakUserId("553046d0-6365-46ca-999a-8c6a27c87cb8")
                .build();
    }

}
