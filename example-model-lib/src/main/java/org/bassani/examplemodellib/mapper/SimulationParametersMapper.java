package org.bassani.examplemodellib.mapper;

import br.com.example.purchasesimulatormodellib.domain.dto.SimulationDTO;
import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.AnticipationTypeSimulationEntity;
import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.BudgetTypeEntity;
import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.DiscountTypeEntity;
import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.NewPaymentTermSimParamDCEntity;
import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.NewPaymentTermTypeEntity;
import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.SimulationParametersEntity;
import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.SimulationStatusEntity;
import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.SimulationTypeEntity;
import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.StockCoverageTypeSimulationEntity;
import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.ValueTypeSimulationEntity;
import br.com.example.purchasesimulatormodellib.domain.request.SimNewPaymentTermDCRequest;
import br.com.example.purchasesimulatormodellib.domain.response.SimulationFollowUpResponse;
import br.com.example.purchasesimulatormodellib.enums.BudgetTypeEnum;
import br.com.example.purchasesimulatormodellib.enums.DiscountTypeEnum;
import br.com.example.purchasesimulatormodellib.enums.NewPaymentTermTypeEnum;
import br.com.example.purchasesimulatormodellib.enums.SalesByPeriodEnum;
import br.com.example.purchasesimulatormodellib.enums.SimulationStatusEnum;
import br.com.example.purchasesimulatormodellib.enums.SimulationTypeEnum;
import br.com.example.purchasesimulatormodellib.enums.TemporaryInactiveEnum;
import br.com.example.purchasesimulatormodellib.exception.PurchaseValueNullException;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Mapper(builder = @Builder(disableBuilder = true))
public interface SimulationParametersMapper {

    SimulationParametersMapper INSTANCE = Mappers.getMapper(SimulationParametersMapper.class);

    static SimulationParametersMapper simulationParametersMapper() {
        return INSTANCE;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "saleDay", source = "calculationBasis.days.days")
    @Mapping(target = "registerDate", ignore = true)
    @Mapping(target = "quoted", source = "productFilter.quoted")
    @Mapping(target = "promoPack", source = "productFilter.promoPack")
    @Mapping(target = "onlyInactive", source = "productFilter.inactive")
    @Mapping(target = "newPaymentTerm.type", source = "newPaymentTerm.newPaymentTermType.entity")
    @Mapping(target = "newPaymentTerm.newPaymentTermGeneral", source = "newPaymentTerm.newPaymentTermGeneral")
    @Mapping(target = "newPaymentTerm.newPaymentTermDCs", source = "newPaymentTerm.newPaymentTermDCs")
    @Mapping(target = "orderTypeParam", source = "orderType")
    @Mapping(target = "orderTypeParam.id", source = "orderType.id")
    @Mapping(target = "temporaryInactiveCode", source = "productFilter.temporaryInactiveCode.code")
    @Mapping(target = "anticipation", expression = "java(this.mapperAnticipationDate(dto.getAnticipationDate()))")
    @Mapping(target = "value", expression = "java(this.mapperValue(dto.getAmount(), dto.getSimulationType().getId()))")
    @Mapping(target = "stockCoverage", expression = "java(this.mapperStockCoverageDC(dto.getStockCoverageDC()))")
    @Mapping(target = "budget", source = "budget")
    SimulationParametersEntity toEntity(SimulationDTO dto);

    @Mapping(target = "stockCoverageDC", source = "stockCoverage.stockCoverageDays")
    @Mapping(target = "amount", source = "value.totalValue")
    @Mapping(target = "anticipationDate", source = "anticipation.anticipationDate")
    @Mapping(target = "calculationBasis.days", source = "saleDay")
    @Mapping(target = "calculationBasis.id", source = "calculationBasis.id")
    @Mapping(target = "generalDiscount", source = "generalDiscount")
    @Mapping(target = "orderType", source = "orderTypeParam")
    @Mapping(target = "orderType.id", source = "orderTypeParam.id")
    @Mapping(target = "newPaymentTerm.newPaymentTermType", source = "newPaymentTerm.type")
    @Mapping(target = "productFilter", ignore = true)
    @Mapping(target = "productFilter.quoted", source = "quoted")
    @Mapping(target = "productFilter.promoPack", source = "promoPack")
    @Mapping(target = "productFilter.onlyActive", source = "active")
    @Mapping(target = "productDiscounts", source = "productDiscounts")
    @Mapping(target = "simulationStatus", source = "status")
    @Mapping(target = "productFilter.temporaryInactiveCode", source = "temporaryInactiveCode")
    @Mapping(target = "budget", source = "budget")
    SimulationDTO toDto(SimulationParametersEntity entity);

    SimulationFollowUpResponse toResponse(SimulationParametersEntity entity);

    default NewPaymentTermTypeEnum toEnum(NewPaymentTermTypeEntity newPaymentTermTypeEntity){
        return Objects.isNull(newPaymentTermTypeEntity) ? null : NewPaymentTermTypeEnum.find(newPaymentTermTypeEntity.getId());
    }

    default SalesByPeriodEnum toEnum(Long saleDay) {
        return Objects.isNull(saleDay) ? null : SalesByPeriodEnum.fromCode(saleDay.intValue());
    }

    default SimulationTypeEnum toEnum(SimulationTypeEntity entity) {
        return SimulationTypeEnum.fromId(entity.getId());
    }

    default SimulationStatusEnum toEnum(SimulationStatusEntity entity) {
        return SimulationStatusEnum.find(entity.getId());
    }

    default DiscountTypeEnum toEnum(DiscountTypeEntity entity) {
        return DiscountTypeEnum.fromId(entity.getId());
    }

    default TemporaryInactiveEnum toEnumTemporaryInactive(Long code) {
        if (code != null) {
            return TemporaryInactiveEnum.fromCode(code.longValue());
        }
        return null;
    }

    default BudgetTypeEnum toEnum(BudgetTypeEntity entity) {
        return BudgetTypeEnum.fromId(entity.getId());
    }
    
    default AnticipationTypeSimulationEntity mapperAnticipationDate(LocalDate anticipationDate) {
    	if(anticipationDate != null) {
    		return AnticipationTypeSimulationEntity.builder().anticipationDate(anticipationDate).build();
    	}
		return null;
    }

    default ValueTypeSimulationEntity mapperValue(BigDecimal totalValue, Long simulationType) throws PurchaseValueNullException {
        if (SimulationTypeEnum.VALOR.getId() == simulationType) {
            if (totalValue != null && totalValue.compareTo(BigDecimal.ZERO) > 0) {
                return ValueTypeSimulationEntity.builder()
                        .totalValue(totalValue)
                        .build();
            } else {
                throw new PurchaseValueNullException();
            }

        }
        return null;
    }

    default StockCoverageTypeSimulationEntity mapperStockCoverageDC(Long stockCoverageDC) {
    	if(stockCoverageDC != null) {
    		return StockCoverageTypeSimulationEntity.builder().stockCoverageDays(stockCoverageDC).build();
    	}
		return null;
    }

    @Mapping(target = "distributionCenterId", source = "distributionCenter")
    NewPaymentTermSimParamDCEntity listRequestToListEntity(SimNewPaymentTermDCRequest simNewPaymentTermDCRequest);

    List<NewPaymentTermSimParamDCEntity> listRequestToListEntity(List<SimNewPaymentTermDCRequest> simNewPaymentTermDCRequestList);

    @Mapping(target = "distributionCenter", source = "distributionCenterId")
    SimNewPaymentTermDCRequest listEntityToListRequest(NewPaymentTermSimParamDCEntity newPaymentTermSimParamDCEntity);

    List<SimNewPaymentTermDCRequest> listListEntityToListRequest(List<NewPaymentTermSimParamDCEntity> simNewPaymentTermDCRequestList);


}