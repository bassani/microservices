package org.bassani.examplebff.service;

import org.bassani.examplebff.repository.SimulationTypesRepository;
import org.bassani.examplemodellib.domain.request.SimulationTypesRequestParams;
import org.bassani.examplemodellib.domain.response.SimulationTypesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimulationTypesService {
    private final SimulationTypesRepository simulationTypesRepository;

    public List<SimulationTypesResponse> getAll(SimulationTypesRequestParams request){
        return simulationTypesRepository.getAll(request);
    }
}
