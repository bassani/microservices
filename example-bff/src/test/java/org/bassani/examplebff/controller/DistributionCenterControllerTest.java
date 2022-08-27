package org.bassani.examplebff.controller;

import org.bassani.examplebff.mock.DistributionCenterMocks;
import org.bassani.examplebff.service.DistributionCenterService;
import org.bassani.examplebff.service.KeyCloakService;
import org.bassani.examplemodellib.domain.request.DistributionCenterRequest;
import org.bassani.examplemodellib.domain.response.DistributionCenterResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(DistributionCenterController.class)
@AutoConfigureMockMvc(addFilters = false)
class DistributionCenterControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private DistributionCenterService service;
    @MockBean private KeyCloakService keyCloakService;

	private static final Pageable DEFAULT_PAGEABLE = PageRequest.of(1, 20);
    private static final String ENDPOINT = "/purchases/distributionCenters";

    @WithMockUser(roles="DISTRIBUTIONCENTER_GETALL")
	@Test
	void shouldReturnError404WhenTryingToSearchAllDistributionCentersWithIncorrectRequestMapping() throws Exception {

		Page<DistributionCenterResponse> expected = new PageImpl<>(DistributionCenterMocks.mockedResponse());

		when(service.getAllDistributionCenters(DistributionCenterRequest.builder().build(), DEFAULT_PAGEABLE)).thenReturn(expected);

		mvc.perform(get("/distribution-centers").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}

    @WithMockUser(roles="DISTRIBUTIONCENTER_GETALL")
	@Test
	void shallReturnAllDistributionCenters() throws Exception {

		Page<DistributionCenterResponse> expected = new PageImpl<>(DistributionCenterMocks.mockedResponse());

		when(service.getAllDistributionCenters(DistributionCenterRequest.builder().build(), DEFAULT_PAGEABLE)).thenReturn(expected);

		mvc.perform(get(ENDPOINT).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

    @WithMockUser(roles="DISTRIBUTIONCENTER_GETALL")
	@Test
	void shallReturnAllDistributionCentersWithRequestId() throws Exception {

		Page<DistributionCenterResponse> expected = new PageImpl<>(DistributionCenterMocks.mockedResponse());

		when(service.getAllDistributionCenters(DistributionCenterMocks.mockedRequestID(), DEFAULT_PAGEABLE)).thenReturn(expected);

		mvc.perform(get(ENDPOINT).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

    @WithMockUser(roles="DISTRIBUTIONCENTER_GETALL")
	@Test
	void shallReturnAllDistributionCentersWithRequestName() throws Exception {

		Page<DistributionCenterResponse> expected = new PageImpl<>(DistributionCenterMocks.mockedResponse());

		when(service.getAllDistributionCenters(DistributionCenterMocks.mockedRequesName(), DEFAULT_PAGEABLE)).thenReturn(expected);

		mvc.perform(get(ENDPOINT).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

    @WithMockUser(roles="DISTRIBUTIONCENTER_GETBYID")
	@Test
	void shallReturnDistributionCentersByID() throws Exception {

		DistributionCenterResponse expected = DistributionCenterMocks.mockedDistributionCenterResponse();

		when(service.getDistributionCenterById(expected.getId())).thenReturn(expected);

		mvc.perform(get(ENDPOINT.concat("/").concat(String.valueOf(expected.getId()))))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(expected.getId()))
				.andExpect(jsonPath("$.name").value(expected.getName()));
	}

}