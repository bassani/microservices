package org.bassani.examplebff.service;

import org.bassani.examplebff.client.ReportClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SimulationReportServiceTest {

    @Mock ReportClient reportClient;
    @InjectMocks SimulationReportService service;

    @Test
    @DisplayName("SimulationReportService.getSpreadsheet()")
    void shouldCheckByteArraySize() {
    	
        Long simulationId = 1L;
        
        byte[] resourceMock = new byte[10];
        
        Resource resourceExpected = new ByteArrayResource(resourceMock);
        
        when(reportClient.getSpreadsheet(simulationId)).thenReturn(resourceExpected);

        Resource resource = service.getSpreadsheet(simulationId);

        assertNotNull(resource);
    }
}
