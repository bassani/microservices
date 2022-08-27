package org.bassani.examplebff.service;

import org.bassani.examplebff.repository.ClassificationRepository;
import org.bassani.examplemodellib.domain.request.ClassificationRequest;
import org.bassani.examplemodellib.domain.response.ClassificationResponse;
import org.bassani.examplemodellib.exception.PurchaseOrderClassificationBadRequestException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class ClassificationService {
    private final ClassificationRepository classificationRepository;

    public Page<ClassificationResponse> getAll(ClassificationRequest request, Pageable page){
        Page<ClassificationResponse> response = classificationRepository.getAll(request, page);
        return new PageImpl<>(response.getContent(), page, response.getTotalElements());
    }

    public Resource getSpreadsheet() {
        return classificationRepository.getSpreadsheet();
    }
    
    public ClassificationResponse save(@Valid ClassificationRequest request) {
		try {
			return classificationRepository.save(request);
		} catch (FeignException.BadRequest e) {
			throw new PurchaseOrderClassificationBadRequestException();
		}
	}
    
    public ClassificationResponse update(Long id, @Valid ClassificationRequest request) {
		try {
			return classificationRepository.update(id, request);
		} catch (FeignException.BadRequest e) {
			throw new PurchaseOrderClassificationBadRequestException();
		}
	}
}
