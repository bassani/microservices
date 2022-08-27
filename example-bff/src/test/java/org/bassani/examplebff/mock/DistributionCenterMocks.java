package org.bassani.examplebff.mock;

import org.bassani.examplemodellib.domain.request.DistributionCenterRequest;
import org.bassani.examplemodellib.domain.response.DistributionCenterResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Slf4j
public class DistributionCenterMocks {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
	public static List<DistributionCenterResponse> mockedResponse() {

		DistributionCenterResponse CD1 = DistributionCenterResponse.builder().id(Long.valueOf(900)).name("CD EMBU - SP").build();
		DistributionCenterResponse CD2 = DistributionCenterResponse.builder().id(Long.valueOf(905)).name("CD PARANA").build();
		DistributionCenterResponse CD3 = DistributionCenterResponse.builder().id(Long.valueOf(903)).name("CD RIO JANEIRO").build();
		DistributionCenterResponse CD4 = DistributionCenterResponse.builder().id(Long.valueOf(1445)).name("CD MINAS GERAIS").build();
		DistributionCenterResponse CD5 = DistributionCenterResponse.builder().id(Long.valueOf(2023)).name("CD PERNAMBUCO").build();

		return List.of(CD1, CD2, CD3, CD4, CD5);
	}

	public static DistributionCenterResponse mockedDistributionCenterResponse() {
		return DistributionCenterResponse.builder()
				.id(Long.valueOf(1446))
				.name("myAmazingName")
				.build();
	}
	public static DistributionCenterRequest mockedDistributionCenterRequest() {
		return DistributionCenterRequest.builder()
				.id(Long.valueOf(1446))
				.name("myAmazingName")
				.build();
	}

	public static DistributionCenterResponse mockedResponseID() {
		return DistributionCenterResponse.builder().id(Long.valueOf(900)).build();
	}

	public static DistributionCenterRequest mockedRequestID() {
        return DistributionCenterRequest.builder().id(Long.valueOf(900)).build();
	}

	public static DistributionCenterRequest mockedRequesName() {
		DistributionCenterRequest request = DistributionCenterRequest.builder().name("CD").build();
		return request;
	}
	
    public static Page<DistributionCenterResponse> mockedDistributionCenter(List<DistributionCenterResponse> allOrderTypes, Pageable pageable) {
        allOrderTypes = mockedResponse();
        return new PageImpl<>(allOrderTypes, pageable, allOrderTypes.size());
    }
	
    private static String safeWriteValueAsString(Object value) {
        String json = "";
        try {
            json = OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error(e.toString());
        }
        return json;
    }

	public static String mockedDistributionCenterAsJson(List<DistributionCenterResponse> mockedResponse, Pageable pageable) {
        return safeWriteValueAsString(mockedDistributionCenter(mockedResponse, pageable));
	}

}
