package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.KeyCloakService;
import org.bassani.examplebff.service.ManufacturerService;
import org.bassani.examplemodellib.domain.request.ManufacturerRequest;
import org.bassani.examplemodellib.domain.response.ManufacturerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(ManufacturerController.class)
@AutoConfigureMockMvc(addFilters = false)
class ManufacturerControllerTest {
	
	private final Integer PAGE = 0;
	private final Integer SIZE = 25;
	private final Sort    SORT = Sort.by("name").ascending();

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ManufacturerService service;
    @MockBean private KeyCloakService keyCloakService;

	ManufacturerResponse manufacturer1 = ManufacturerResponse.builder().id(1L).name("Fabricante 1").build();
	ManufacturerResponse manufacturer2 = ManufacturerResponse.builder().id(2L).name("Fabricante 2").build();
	ManufacturerResponse manufacturer3 = ManufacturerResponse.builder().id(3L).name("Manufacturer 3").build();
	ManufacturerResponse manufacturer4 = ManufacturerResponse.builder().id(4L).name("Manufacturer 4").build();
	ManufacturerResponse manufacturer5 = ManufacturerResponse.builder().id(5L).name("Ink 5").build();
	
	List<ManufacturerResponse> manufacturers = new ArrayList<ManufacturerResponse>();

    @WithMockUser(roles="MANUFACTURER_GETALL")
	@Test
	@DisplayName("GET - /manufacturer - OK")
	void whenGET_returnWithoutRequestOk() throws Exception {
		
		manufacturers.add(manufacturer1);
		manufacturers.add(manufacturer2);
		manufacturers.add(manufacturer3);
		manufacturers.add(manufacturer4);
		manufacturers.add(manufacturer5);
		
		doReturn(new PageImpl<>(manufacturers)).when(service).getAll(null, PageRequest.of(PAGE, SIZE, SORT));
		
		mvc.perform(
				get("/purchases/manufacturers"))
				.andExpect(status().isOk());
		
	}

    @WithMockUser(roles="MANUFACTURER_GETALL")
	@Test
	@DisplayName("GET - /manufacturer - OK")
	void whenGET_returnWithRequestOk() throws Exception {
        ManufacturerRequest request = new ManufacturerRequest(2L, null, null, null, null, null, false);
		manufacturers.add(manufacturer2);
		
		doReturn(new PageImpl<>(manufacturers))
			.when(service)
			.getAll(request, PageRequest.of(PAGE, SIZE, SORT));
		
		mvc.perform(
				get("/purchases/manufacturers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(request)))
				.andExpect(status().isOk());
	}

    @WithMockUser(roles="MANUFACTURER_GETBYID")
	@Test
	@DisplayName("GET - /manufacturer/{id} - BAD_REQUEST")
	void whenGET_returnBadRequestNegative() throws Exception {
		
		mvc.perform(
				get("/purchases/manufacturers/-2"))
				.andExpect(status().isBadRequest());
	}

    @WithMockUser(roles="MANUFACTURER_GETBYID")
	@Test
	@DisplayName("GET - /manufacturer/{id} - BAD_REQUEST")
	void whenGET_returnBadRequestLetter() throws Exception {
		
		mvc.perform(
				get("/purchases/manufacturers/j"))
				.andExpect(status().isBadRequest());
	}

    @WithMockUser(roles="MANUFACTURER_GETBYID")
	@Test
	@DisplayName("GET - /manufacturer/{id} - NOT_FOUND")
	void whenGET_returnNotFound() throws Exception {
	
		mvc.perform(
				get("/manufacturer/6"))
				.andExpect(status().isNotFound());
	}

    @WithMockUser(roles="MANUFACTURER_GETBYID")
	@Test
	@DisplayName("GET - /manufacturer/{id} - OK")
	void whenGET_returnOk() throws Exception {
		
		doReturn(manufacturer5)
		.when(service)
		.getManufacturer(5);
		
		mvc.perform(
				get("/purchases/manufacturers/5"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(5))
				.andExpect(jsonPath("$.name").value("Ink 5"));
	}

    @WithMockUser(roles="MANUFACTURER_GETALL")
    @Test
    @DisplayName("GET - /parents - OK")
    void whenGETParents_returnWithRequestOk() throws Exception {
        ManufacturerRequest request = new ManufacturerRequest(2L, null, null, null, null, 1, false);
        manufacturers.add(manufacturer2);

        doReturn(new PageImpl<>(manufacturers))
                .when(service)
                .getAll(request, PageRequest.of(PAGE, SIZE, SORT));

        mvc.perform(
                        get("/purchases/manufacturers/parents")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}