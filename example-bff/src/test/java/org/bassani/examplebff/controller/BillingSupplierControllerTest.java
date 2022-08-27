package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.BillingSupplierService;
import org.bassani.examplebff.service.KeyCloakService;
import org.bassani.examplemodellib.domain.request.ManufacturerRequest;
import org.bassani.examplemodellib.domain.response.BillingSupplierToPurchaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(BillingSupplierController.class)
@AutoConfigureMockMvc(addFilters = false)
class BillingSupplierControllerTest {
	
	private final Integer PAGE = 0;
	private final Integer SIZE = 25;
	private final Sort    SORT = Sort.by("name").ascending();

	@Autowired
	private MockMvc mvc;

	@MockBean
	private BillingSupplierService service;

	@MockBean
	private KeyCloakService keyCloakService;

	BillingSupplierToPurchaseResponse manufacturer = BillingSupplierToPurchaseResponse.builder().id(2L).name("ACHE").build();

	List<BillingSupplierToPurchaseResponse> manufacturers = new ArrayList<>();

	@WithMockUser(roles="BILLING_SUPPLIER_GETALL")
	@Test
	@DisplayName("GET - /billing - OK")
	void whenGETToPurchase_returnWithRequestOk() throws Exception {
		ManufacturerRequest request = new ManufacturerRequest(2L, null, null, null, null, null, true);
		manufacturers.add(manufacturer);

		doReturn(manufacturers)
				.when(service)
				.getAllBillingSupplier(request, PageRequest.of(PAGE, SIZE, SORT));

		mvc.perform(
				get("/purchases/supplier/billing")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(request)))
				.andExpect(status().isOk());
	}
}