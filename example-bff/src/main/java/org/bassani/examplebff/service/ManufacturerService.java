package org.bassani.examplebff.service;

import org.bassani.examplebff.repository.ManufacturerRepository;
import org.bassani.examplemodellib.domain.request.ManufacturerRequest;
import org.bassani.examplemodellib.domain.response.ManufacturerResponse;
import org.bassani.examplemodellib.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ManufacturerService {

	private final ManufacturerRepository manufacturerRepository;

    public Page<ManufacturerResponse> getAll(ManufacturerRequest manufacturer, Pageable page)  {
        
    	Page<ManufacturerResponse> manufactorerEntities;
        
        if (manufacturer != null) {
        	manufactorerEntities =  manufacturerRepository.getAll(page, manufacturer);
        } else
        	manufactorerEntities = manufacturerRepository.getAll(page);
        
        return new PageImpl<>(manufactorerEntities.getContent(), page, manufactorerEntities.getTotalElements());
    }

    public Page<ManufacturerResponse> getAllParentsManufacturer(ManufacturerRequest manufacturerRequest, Pageable page)  {

        Page<ManufacturerResponse> manufactorerEntities = new PageImpl(new ArrayList());

        if (Optional.ofNullable(manufacturerRequest).isPresent()) {
            manufactorerEntities = manufacturerRepository.getAllParentsManufacturers(page, manufacturerRequest);
        }

        return new PageImpl<>(manufactorerEntities.getContent(), page, manufactorerEntities.getTotalElements());
    }

    public ManufacturerResponse getManufacturer(Integer manufacturerId) {
        
    	if (manufacturerId == null || manufacturerId <= 0 )
        	throw new BusinessException(HttpStatus.BAD_REQUEST,
        								"Field Id of manufacturer incorrect",
        								"Field Id of manufacturer has that to be positive and not null");

    	return manufacturerRepository.getManufacturer(manufacturerId);
    }    
}