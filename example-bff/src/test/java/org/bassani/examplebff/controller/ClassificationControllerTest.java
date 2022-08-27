package org.bassani.examplebff.controller;

import org.bassani.examplebff.mock.ClassificationMocks;
import org.bassani.examplebff.service.ClassificationService;
import org.bassani.examplebff.service.KeyCloakService;
import org.bassani.examplemodellib.domain.request.ClassificationRequest;
import org.bassani.examplemodellib.domain.response.ClassificationResponse;
import org.bassani.examplemodellib.exception.ExceptionResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ClassificationController.class)
class ClassificationControllerTest  {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ClassificationService classificationService;
    @MockBean private KeyCloakService keyCloakService;

    public static final String ENDPOINT = "/purchases/classifications";
    private static final String SPREADSHEET_ENDPOINT = ENDPOINT + "/export";

    @WithMockUser(roles="CLASSIFICATION_GETALL")
    @Test
    void shouldFetchAllClassificationTypes() throws Exception {
        Pageable pageable = PageRequest.of(1, 20);
        Page<ClassificationResponse> pageList = new PageImpl<>(ClassificationMocks.mockedClassificationTypes());

        when(classificationService.getAll(new ClassificationRequest(), pageable)).thenReturn(pageList);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles="CLASSIFICATION_GETALL")
    @Test
    void shouldFetchAllClassificationTypesById() throws Exception {
        Pageable pageable = PageRequest.of(1, 20);
        Page<ClassificationResponse> pageList = new PageImpl<>(ClassificationMocks.mockedClassificationTypes());

        given(classificationService.getAll(new ClassificationRequest().builder().id(1L).build(), pageable)).willReturn(pageList);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles="CLASSIFICATION_GETALL")
    @Test
    void shouldFetchAllClassificationTypesByName() throws Exception {
        Pageable pageable = PageRequest.of(1, 20);
        Page<ClassificationResponse> pageList = new PageImpl<>(ClassificationMocks.mockedClassificationTypes());

        given(classificationService.getAll(new ClassificationRequest().builder().name("so").build(), pageable)).willReturn(pageList);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles="CLASSIFICATION_GETALL")
    @Test
    void shouldFetchAllClassificationTypesByQuery() throws Exception {
        Pageable pageable = PageRequest.of(1, 20);
        Page<ClassificationResponse> pageList = new PageImpl<>(
                ClassificationMocks.mockedClassificationTypes().stream().filter(c -> c.getName().toLowerCase().contains("ca")).collect(Collectors.toList()));

        given(classificationService.getAll(new ClassificationRequest().builder().query("ca").build(), pageable)).willReturn(pageList);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles="CLASSIFICATION_SAVE")
    @Test
	@DisplayName("POST - /classifications - OK")
	void whenPOST_returnWithRequestOk() throws Exception {
		ClassificationRequest request = new ClassificationRequest();
		
		doReturn(ClassificationResponse.builder().active("habilitado").id(1L).name("TESTE").build()).when(classificationService).save(request);
		
		this.mockMvc.perform(
				post(ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(request)))
				.andExpect(status().isCreated());
	}

    @WithMockUser(roles="CLASSIFICATION_UPDATE")
	@Test
	@DisplayName("PUT - /purchaseorderclassifications - OK")
	void whenPUT_returnWithRequestOk() throws Exception {
		ClassificationRequest request = new ClassificationRequest();
		
		doReturn(ClassificationResponse.builder().active("habilitado").id(1L).name("TESTE").build()).when(classificationService).update(1L, request);
		
		this.mockMvc.perform(
				put(ENDPOINT+"/"+1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(request)))
				.andExpect(status().isCreated());
	}

    @WithMockUser(roles="CLASSIFICATION_EXPORT")
    @Test
    @DisplayName("1+ Elements - GET /classifications/export - OK")
    void givenDBWithAtLeastOneClassification_whenCsvIsRequested_thenCsvContainingAllClassificationsIsReturned()
            throws Exception {
        String csvAsString = ClassificationMocks.mockedCsvAsString();
        byte[] csvAsBytes = csvAsString.getBytes(StandardCharsets.UTF_8);

        when(classificationService.getSpreadsheet()).thenReturn(new ByteArrayResource(csvAsBytes));

        mockMvc.perform(get(SPREADSHEET_ENDPOINT)
                .accept(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(content().bytes(csvAsBytes))
                .andExpect(content().string(csvAsString));
    }

    @WithMockUser(roles="CLASSIFICATION_EXPORT")
    @Test
    @DisplayName("0 Elements - GET /classifications/export - OK")
    void givenDBWithNoClassifications_whenCsvIsRequested_thenCsvContainingOnlyTheHeaderIsReturned()
            throws Exception {
        String csvAsString = ClassificationMocks.mockedCsvAsString();
        byte[] csvAsBytes = csvAsString.getBytes(StandardCharsets.UTF_8);

        when(classificationService.getSpreadsheet()).thenReturn(new ByteArrayResource(csvAsBytes));

        mockMvc.perform(get(SPREADSHEET_ENDPOINT)
                .accept(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(content().bytes(csvAsBytes))
                .andExpect(content().string(csvAsString));
    }

    @WithMockUser(roles="CLASSIFICATION_EXPORT")
    @Test
    @DisplayName("GET /classifications/export - INTERNAL_SERVER_ERROR")
    void givenCsvCannotBeGenerated_whenCsvIsRequested_thenInternalServerErrorIsReturned() throws Exception {
        FeignException.InternalServerError exception =
                new FeignException.InternalServerError("Some message", new byte[0]);

        when(classificationService.getSpreadsheet()).thenThrow(exception);

        mockMvc.perform(get(SPREADSHEET_ENDPOINT))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value(exception.getMessage()))
                .andExpect(jsonPath("$.description").value(ExceptionResolver.getRootException(exception)));
    }
}
