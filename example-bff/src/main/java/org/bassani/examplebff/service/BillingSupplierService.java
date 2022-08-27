package org.bassani.examplebff.service;

import org.bassani.examplebff.repository.BillingSupplierRepository;
import org.bassani.examplemodellib.domain.request.ManufacturerRequest;
import org.bassani.examplemodellib.domain.response.BillingSupplierToPurchaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BillingSupplierService {

	private final BillingSupplierRepository billingSupplierRepository;

    public List<BillingSupplierToPurchaseResponse> getAllBillingSupplier(ManufacturerRequest manufacturerRequest, Pageable page)  {
         return billingSupplierRepository.getAllBillingSupplier(page, manufacturerRequest);


    }

}