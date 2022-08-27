package org.bassani.examplemodellib.enums;

import br.com.example.communicationcommon.domain.dto.templates.email.PurchaseSimulatorApprovalApproved;
import br.com.example.communicationcommon.domain.dto.templates.email.PurchaseSimulatorApprovalDraftNearExpiration;
import br.com.example.communicationcommon.domain.dto.templates.email.PurchaseSimulatorPending;
import br.com.example.communicationcommon.domain.dto.templates.email.PurchaseSimulatorRefused;
import br.com.example.purchasesimulatormodellib.domain.dto.MessageDTO;

public enum TemplateEnum {
    INTERNAL_PURCHASE_SIMULATOR_REFUSED("INTERNAL_PURCHASE_SIMULATOR_REFUSED"){
        @Override
        public PurchaseSimulatorRefused fill(MessageDTO messageDTO) {
            return PurchaseSimulatorRefused.builder()
                    .subject(messageDTO.getSimulationStatus().getName())
                    .simulationId(messageDTO.getSimulationId().toString())
                    .parentSupplierName(messageDTO.getParentSupplierName())
                    .classification(messageDTO.getClassification().getName())
                    .completedBy(messageDTO.getCompletedBy().getName())
                    .reason(messageDTO.getReason())
                    .build();
        }
    },
    INTERNAL_PURCHASE_SIMULATOR_PENDING("INTERNAL_PURCHASE_SIMULATOR_PENDING"){
        @Override
        public PurchaseSimulatorPending fill(MessageDTO messageDTO) {

            return PurchaseSimulatorPending.builder()
                    .subject(messageDTO.getSimulationStatus().getName())
                    .simulationId(messageDTO.getSimulationId().toString())
                    .parentSupplierName(messageDTO.getParentSupplierName())
                    .classification(messageDTO.getClassification().getName())
                    .amount(messageDTO.getAmount().toString())
                    .completedBy(messageDTO.getCompletedBy().getName())
                    .build();
        }
    },
    INTERNAL_PURCHASE_SIMULATOR_APPROVAL_APPROVED("INTERNAL_PURCHASE_SIMULATOR_APPROVAL_APPROVED"){
        @Override
        public PurchaseSimulatorApprovalApproved fill(MessageDTO messageDTO) {

            return PurchaseSimulatorApprovalApproved.builder()
                    .subject(messageDTO.getSimulationStatus().getName())
                    .simulationId(messageDTO.getSimulationId().toString())
                    .parentSupplierName(messageDTO.getParentSupplierName())
                    .classification(messageDTO.getClassification().getName())
                    .completedBy(messageDTO.getCompletedBy().getName())
                    .build();
        }
    },
    INTERNAL_PURCHASE_SIMULATOR_APPROVAL_DRAFT_NEAR_EXP("INTERNAL_PURCHASE_SIMULATOR_APPROVAL_DRAFT_NEAR_EXP"){
        @Override
        public PurchaseSimulatorApprovalDraftNearExpiration fill(MessageDTO messageDTO) {

            return PurchaseSimulatorApprovalDraftNearExpiration.builder()
                    .subject(messageDTO.getSimulationStatus().getName())
                    .simulationId(messageDTO.getSimulationId().toString())
                    .parentSupplierName(messageDTO.getParentSupplierName())
                    .classification(messageDTO.getClassification().getName())
                    .expiresAt(messageDTO.getExpirationDate().toString())
                    .build();
        }
    },
    INTERNAL_PURCHASE_SIMULATOR_APPROVAL_NEAR_EXP("INTERNAL_PURCHASE_SIMULATOR_APPROVAL_NEAR_EXP"){
        @Override
        public PurchaseSimulatorApprovalDraftNearExpiration fill(MessageDTO messageDTO) {

            return PurchaseSimulatorApprovalDraftNearExpiration.builder()
                    .subject(messageDTO.getSimulationStatus().getName())
                    .simulationId(messageDTO.getSimulationId().toString())
                    .parentSupplierName(messageDTO.getParentSupplierName())
                    .classification(messageDTO.getClassification().getName())
                    .expiresAt(messageDTO.getExpirationDate().toString())
                    .build();
        }
    };

    TemplateEnum(String templateName) {
        this.templateName = templateName;
    }

    private String templateName;
    public abstract <T> T fill(MessageDTO messageDTO);

}
