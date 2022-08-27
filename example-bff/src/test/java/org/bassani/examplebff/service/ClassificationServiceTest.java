package org.bassani.examplebff.service;

import org.bassani.examplebff.mock.ClassificationMocks;
import org.bassani.examplebff.repository.ClassificationRepository;
import org.bassani.examplemodellib.domain.request.ClassificationRequest;
import org.bassani.examplemodellib.domain.response.ClassificationResponse;
import org.bassani.examplemodellib.exception.PurchaseOrderClassificationBadRequestException;
import feign.FeignException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClassificationServiceTest {
    @Mock
    private ClassificationRepository classificationTypeRepository;

    @InjectMocks
    private ClassificationService classificationTypeService;

    @Test
    void givenClassificationTypesHasList_whenGetAllIsPerformed_thenAllClassificationTypesAreReturned() {
        Pageable pageable = PageRequest.of(1, 20);
        Page<ClassificationResponse> expected = new PageImpl<>(ClassificationMocks.mockedClassificationTypes());

        Page<ClassificationResponse> page = new PageImpl<>(expected.getContent());

        when(classificationTypeRepository.getAll(new ClassificationRequest(), pageable)).thenReturn(page);
        Page<ClassificationResponse> actual = classificationTypeService.getAll(ClassificationRequest.builder().build(), pageable);

        assertIterableEquals(expected.getContent(), actual);
    }

    @Test
    void givenClassificationTypesHasList_whenGetAllByQueryIsPerformed_thenAllClassificationTypesAreReturned() {
        Pageable pageable = PageRequest.of(1, 20);
        Page<ClassificationResponse> expected = new PageImpl<>(
                ClassificationMocks.mockedClassificationTypes().stream().filter(c -> c.getName().toLowerCase().contains("COVID".toLowerCase())).collect(Collectors.toList()));

        Page<ClassificationResponse> page = new PageImpl<>(expected.getContent());

        when(classificationTypeRepository.getAll(new ClassificationRequest().builder().query("covid").build(), pageable)).thenReturn(page);
        Page<ClassificationResponse> actual = classificationTypeService.getAll(ClassificationRequest.builder().query("covid").build(), pageable);

        assertIterableEquals(expected.getContent(), actual);
    }

    @Test
    void givenClassificationTypesHasList_whenGetAllByIdIsPerformed_thenAllClassificationTypesAreReturned() {
        Pageable pageable = PageRequest.of(1, 20);
        Page<ClassificationResponse> expected = new PageImpl<>(
                ClassificationMocks.mockedClassificationTypes().stream().filter(c -> c.getId().toString().contains("1")).collect(Collectors.toList()));

        Page<ClassificationResponse> page = new PageImpl<>(expected.getContent());

        when(classificationTypeRepository.getAll(new ClassificationRequest().builder().id(1L).build(), pageable)).thenReturn(page);
        Page<ClassificationResponse> actual = classificationTypeService.getAll(ClassificationRequest.builder().id(1L).build(), pageable);

        assertEquals(expected.getContent().size(), actual.getContent().size());
    }
    
    @Test
	@DisplayName("Testing save method - Return OK")
	void whenCallSave_return_OK() {
		ClassificationRequest request = new ClassificationRequest();
		
		doReturn(ClassificationResponse.builder().name("teste").active("habilitado").build()).when(classificationTypeRepository).save(Mockito.any());
		
		assertNotNull(classificationTypeService.save(request));
	}
	
	@Test
	@DisplayName("Testing update method - Return OK")
	void whenCallUpdate_return_OK() {
		ClassificationRequest request = new ClassificationRequest();
		
		doReturn(ClassificationResponse.builder().name("teste").active("habilitado").build()).when(classificationTypeRepository).update(1L, request);
		
		assertNotNull(classificationTypeService.update(1L, request));
	}
	
	@Test
	@DisplayName("Testing save method - Return Exception")
	void whenCallServiceSave_return_Exception() {
		ClassificationRequest request = new ClassificationRequest();
	
		doThrow(FeignException.BadRequest.class).when(classificationTypeRepository).save(request);
		
		assertThrows(PurchaseOrderClassificationBadRequestException.class, ()-> classificationTypeService.save(request));
	}
	
	@Test
	@DisplayName("Testing update method - Return Exception")
	void whenCallServiceUpdate_return_Exception() {
		ClassificationRequest request = new ClassificationRequest();
	
		doThrow(FeignException.BadRequest.class).when(classificationTypeRepository).update(1L, request);
		
		assertThrows(PurchaseOrderClassificationBadRequestException.class, ()-> classificationTypeService.update(1l, request));
	}

    @Test
    void givenDBWithAtLeastOneClassification_whenCsvIsRequested_thenCsvContainingAllClassificationsIsReturned() {
        Resource expectedCsvResource = ClassificationMocks.mockedCsvAsResource();

        when(classificationTypeRepository.getSpreadsheet()).thenReturn(expectedCsvResource);
        Resource actualCsvResource = classificationTypeService.getSpreadsheet();

        assertEquals(expectedCsvResource, actualCsvResource);
    }

    @Test
    void givenDBWithNoClassifications_whenCsvIsRequested_thenCsvContainingOnlyTheHeaderIsReturned() {
        Resource expectedCsvResource = ClassificationMocks.mockedCsvHeaderAsResource();

        when(classificationTypeRepository.getSpreadsheet()).thenReturn(expectedCsvResource);
        Resource actualCsvResource = classificationTypeService.getSpreadsheet();

        assertEquals(expectedCsvResource, actualCsvResource);
    }

    @Test
    void givenCsvCannotBeGenerated_whenCsvIsRequest_thenInternalServerErrorIsReturned() {
        when(classificationTypeRepository.getSpreadsheet())
                .thenThrow(new FeignException.InternalServerError("", new byte[0]));

        FeignException.InternalServerError ex = assertThrows(FeignException.InternalServerError.class,
                () -> classificationTypeService.getSpreadsheet());
    }
}
