package org.bassani.examplebff.service;

import org.bassani.examplebff.repository.ManufacturerRepository;
import org.bassani.examplemodellib.domain.request.ManufacturerRequest;
import org.bassani.examplemodellib.domain.response.ManufacturerResponse;
import org.bassani.examplemodellib.exception.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class ManufacturerServiceTest {

	private final Integer PAGE = 0;
	private final Integer SIZE = 25;
	private final Sort    SORT = Sort.by("name").ascending();

	PageRequest page = PageRequest.of(PAGE, SIZE, SORT);

	@InjectMocks
	ManufacturerService service;

	@Mock
	ManufacturerRepository repository;

	@Mock
	private ObjectMapper mapper;

	ManufacturerRequest request = ManufacturerRequest.builder().id(1L).name("TESTE").build();


	@Test
	@DisplayName("Testing method service.getAll - Return OK")
	void whenCallFindAllWithoutRequest_return_OK() throws IOException {
		
		doReturn(new PageImpl<ManufacturerResponse>(new ArrayList<ManufacturerResponse>(), PageRequest.of(PAGE, SIZE), 0))
			.when(repository)
			.getAll(PageRequest.of(PAGE, SIZE, SORT));

		assertNotNull(service.getAll(null, PageRequest.of(PAGE, SIZE, SORT)));
	}
	
	@Test
	@DisplayName("Testing method service.getAll - Return OK")
	void whenCallFindAllWithoutRequest_return_OK2() throws IOException {
		
		ManufacturerRequest request = new ManufacturerRequest();
		
		doReturn(new PageImpl<ManufacturerResponse>(new ArrayList<ManufacturerResponse>(), PageRequest.of(PAGE, SIZE), 0))
			.when(repository)
			.getAll(PageRequest.of(PAGE, SIZE, SORT), request);
		
		assertNotNull(service.getAll(request, PageRequest.of(PAGE, SIZE, SORT)));
	}

	@Test
	@DisplayName("Testing method service.getManufacturer(null) - Return BAD_REQUEST")
	void whenCallGetManufacturerNegative_return_BAD_REQUEST() {
		assertThrows(BusinessException.class, () -> service.getManufacturer(null));
	}

	@Test
	@DisplayName("Testing method service.getManufacturer(Negative) - Return BAD_REQUEST")
	void whenCallGetManufacturerNull_return_BAD_REQUEST() {
		assertThrows(BusinessException.class, () -> service.getManufacturer(-2));
	}

	@Test
	@DisplayName("Testing method service.getManufacture(1) - Return OK")
	void whenCallGetManufacturer_return_OK() {

		doReturn(ManufacturerResponse.builder().id(1L).name("Teste").build()).when(repository).getManufacturer(1);

		assertSame(ManufacturerResponse.class, service.getManufacturer(1).getClass());
	}

    @Test
    @DisplayName("Testing method service.getAllParents - Return OK")
    void whenCallFindAllParentsWithRequest_return_OK() throws IOException {

        ManufacturerRequest request = new ManufacturerRequest();
        request.setParent(1);

        doReturn(new PageImpl<ManufacturerResponse>(new ArrayList<ManufacturerResponse>(), PageRequest.of(PAGE, SIZE), 0))
                .when(repository)
                .getAllParentsManufacturers(PageRequest.of(PAGE, SIZE, SORT), request);

        assertNotNull(service.getAllParentsManufacturer(request, PageRequest.of(PAGE, SIZE, SORT)));
    }
}