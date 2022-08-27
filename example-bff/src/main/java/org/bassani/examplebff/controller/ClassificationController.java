package org.bassani.examplebff.controller;

import org.bassani.examplebff.service.ClassificationService;
import org.bassani.examplemodellib.domain.request.ClassificationRequest;
import org.bassani.examplemodellib.domain.response.ClassificationResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchases/classifications")
@RequiredArgsConstructor
@Validated
@Api(tags = { "Classifications" })
public class ClassificationController {
    private final ClassificationService classificationService;

    @PreAuthorize("hasRole('ROLE_CLASSIFICATION_GETALL')")
    @GetMapping
    @ApiOperation(value = "Obtém todos os tipos de classificação", authorizations = { @Authorization(value = "OAuth2") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 500, message = "Erro desconhecido") })
    public ResponseEntity<Page<ClassificationResponse>> getAll(ClassificationRequest request, Pageable page){
        return ResponseEntity.ok(classificationService.getAll(request, page));

    }

    @PreAuthorize("hasRole('ROLE_CLASSIFICATION_SAVE')")
    @PostMapping
    @ApiOperation(value = "Salvar uma classificação", authorizations = { @Authorization(value = "OAuth2") })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 500, message = "Erro desconhecido")})
	public ResponseEntity<ClassificationResponse> save(@RequestBody ClassificationRequest classification){
		return ResponseEntity.status(HttpStatus.CREATED).body(classificationService.save(classification));
	}

    @PreAuthorize("hasRole('ROLE_CLASSIFICATION_UPDATE')")
	@PutMapping("/{id}")
	@ApiOperation(value = "Atualizar uma classificação", authorizations = { @Authorization(value = "OAuth2") })
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 500, message = "Erro desconhecido")})
	public ResponseEntity<ClassificationResponse> update(@PathVariable Long id,  @RequestBody ClassificationRequest classification){
		return ResponseEntity.status(HttpStatus.CREATED).body(classificationService.update(id, classification));
	}

    @PreAuthorize("hasRole('ROLE_CLASSIFICATION_EXPORT')")
    @GetMapping("/export")
    @ApiOperation(value = "Obtém arquivo CSV com todas as classificações",
            authorizations = {@Authorization(value = "OAuth2")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Chamada realizada com sucesso"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 500, message = "Erro desconhecido")})
    public ResponseEntity<Resource> export(){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(classificationService.getSpreadsheet());
    }
}
