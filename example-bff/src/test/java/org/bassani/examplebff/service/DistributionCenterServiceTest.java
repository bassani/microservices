package org.bassani.examplebff.service;

import org.bassani.examplebff.mock.DistributionCenterMocks;
import org.bassani.examplebff.repository.DistributionCenterRepository;
import org.bassani.examplemodellib.domain.request.DistributionCenterRequest;
import org.bassani.examplemodellib.domain.response.DistributionCenterResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DistributionCenterServiceTest  {

	@Mock
	private DistributionCenterRepository repository;

	@InjectMocks
	private DistributionCenterService service;

	private static final Pageable DEFAULT_PAGEABLE = PageRequest.of(1, 20);

	@Test
	void shallReturnAllDistributionCenters() {
		Page<DistributionCenterResponse> expected = new PageImpl<>(DistributionCenterMocks.mockedResponse());

		DistributionCenterRequest request = new DistributionCenterRequest(null, null);
		
		when(repository.searchAllDistributionCenters(request, DEFAULT_PAGEABLE)).thenReturn(expected);

		Page<DistributionCenterResponse> response = service.getAllDistributionCenters(DistributionCenterRequest.builder().build(), DEFAULT_PAGEABLE);

		assertIterableEquals(expected, response);
	}

	@Test
	void shouldNotReturnDistributionCentersWhenTheListIsEmpty() {
		
		Page<DistributionCenterResponse> expected = new PageImpl<>(Collections.emptyList());

		DistributionCenterRequest request = new DistributionCenterRequest(null, null);
		
		when(repository.searchAllDistributionCenters(request,DEFAULT_PAGEABLE)).thenReturn(expected);

		Page<DistributionCenterResponse> response = service.getAllDistributionCenters(DistributionCenterRequest.builder().build(), DEFAULT_PAGEABLE);

		assertIterableEquals(expected, response);
	}

	@Test
	void shallReturnAllDistributionCentersFindAllById() {
		Page<DistributionCenterResponse> expected = new PageImpl<>(DistributionCenterMocks.mockedResponse());

		DistributionCenterRequest request = DistributionCenterMocks.mockedRequestID();
		
		when(repository.searchAllDistributionCenters(request, DEFAULT_PAGEABLE)).thenReturn(expected);

		Page<DistributionCenterResponse> response = service.getAllDistributionCenters(
                DistributionCenterMocks.mockedRequestID(), DEFAULT_PAGEABLE);

		assertIterableEquals(expected, response);
	}

	@Test
	void shallReturnAllDistributionCentersFindAllByName() {
		Page<DistributionCenterResponse> expected = new PageImpl<>(DistributionCenterMocks.mockedResponse());

		DistributionCenterRequest request = DistributionCenterMocks.mockedRequesName();
		
		when(repository.searchAllDistributionCenters(request, DEFAULT_PAGEABLE)).thenReturn(expected);

		Page<DistributionCenterResponse> response = service.getAllDistributionCenters(
                DistributionCenterMocks.mockedRequesName(), DEFAULT_PAGEABLE);

		assertIterableEquals(expected, response);
	}

	@Test
	void shallReturnDistributionCenterFindById() {
		//scenario
		DistributionCenterResponse expected = DistributionCenterMocks.mockedDistributionCenterResponse();
		when(repository.searchDistributionCenterByID(expected.getId())).thenReturn(expected);

		//action
		DistributionCenterResponse response = service.getDistributionCenterById(expected.getId());

		//validation
		assertEquals(expected, response);

		Mockito.verify(repository).searchDistributionCenterByID(anyLong());
		Mockito.verifyNoMoreInteractions(repository);
	}

}