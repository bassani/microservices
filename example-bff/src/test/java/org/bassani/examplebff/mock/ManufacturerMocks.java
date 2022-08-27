package org.bassani.examplebff.mock;

import org.bassani.examplemodellib.domain.response.ManufacturerResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ManufacturerMocks {
	
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
	static ManufacturerResponse manufacturer1 = ManufacturerResponse.builder().id(1L).name("Fabricante 1").build();
	static ManufacturerResponse manufacturer2 = ManufacturerResponse.builder().id(2L).name("Fabricante 2").build();
	static ManufacturerResponse manufacturer3 = ManufacturerResponse.builder().id(3L).name("Manufacturer 3").build();
	static ManufacturerResponse manufacturer4 = ManufacturerResponse.builder().id(4L).name("Manufacturer 4").build();
	static ManufacturerResponse manufacturer5 = ManufacturerResponse.builder().id(5L).name("Ink 5").build();
	
	private static List<ManufacturerResponse> getList(){
		return List.of(manufacturer1, manufacturer2, manufacturer3, manufacturer4, manufacturer5);
	}
	
	public static Page<ManufacturerResponse> getManufacturers() {
		return new PageImpl<ManufacturerResponse>(getList(), PageRequest.of(0, 25, Sort.by("name").ascending()),getList().size());
	}
	
	public static ManufacturerResponse getManufacturer(int id) {
		 return getList().stream().filter(manufacturer -> manufacturer.getId().equals(id)).findFirst().orElse(null);
			 
	}
	
	public static Page<ManufacturerResponse> getManufacturer(String name) {
		List<ManufacturerResponse> list = getList().stream().filter( manu -> manu.getName().contains(name)).collect(Collectors.toList());
		return new PageImpl<ManufacturerResponse>(list, PageRequest.of(0, 25, Sort.by("name").ascending()), list.size());
	}
	
	public static String getJsonObject(Object object) {
		String json = "";
		try {
			json = OBJECT_MAPPER.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		return json;
	}

}