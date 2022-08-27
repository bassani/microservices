package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.PaymentTermService;
import org.bassani.examplemodellib.domain.request.PaymentTermRequest;
import org.bassani.examplemodellib.domain.response.PaymentTermInstallmentResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequestMapping("/purchases/paymentTerms")
@Api(tags = { "Payment Term" })
@RequiredArgsConstructor
@RestController
public class PaymentTermController {

	private final PaymentTermService paymentTermService;

    @PreAuthorize("hasRole('ROLE_PAYMENTTERM_GETALL')")
	@GetMapping
	@ApiOperation(value = "Obtém todas as Condições de Pagamento", authorizations = {
			@Authorization(value = "OAuth2") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
			@ApiResponse(code = 401, message = "Acesso não autorizado"),
			@ApiResponse(code = 500, message = "Erro desconhecido") })
	public ResponseEntity<Page<PaymentTermInstallmentResponse>> getAll(
			PaymentTermRequest request, Pageable pageable) {
		return ResponseEntity.ok(paymentTermService.getAllPaymentTerms(request, pageable));
	}

}
