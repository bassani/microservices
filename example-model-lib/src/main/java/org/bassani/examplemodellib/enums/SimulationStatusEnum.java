package org.bassani.examplemodellib.enums;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.SimulationStatusEntity;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

@Getter
public enum SimulationStatusEnum implements Serializable {
    CANCEL(0L, "Cancelado"),
    PROCESSING(1L, "Processando"),
    DRAFT(2L, "Rascunho"),
    ERROR(3L, "Erro"),
    FINISHED(4L, "Finalizado"),
    ENVIADO_PARA_APROVACAO(5L,  "Enviado para Aprovação", SimulationApprovalFlowStep.STARTED_APPROVAL),
    PENDENTE_APROVACAO_COORDENADOR_COMERCIAL(6L,  "Pendente Aprovação Coordenador Comercial", "Coordenador"),
    PENDENTE_APROVACAO_COORDENADOR_ABASTECIMENTO(7L,  "Pendente Aprovação Coordenador Abastecimento", "Coordenador"),
    PENDENTE_APROVACAO_GERENTE(8L,  "Pendente Aprovação Gerente", "Gerente"),
    PENDENTE_APROVACAO_DIRETOR_ADJUNTO(9L,  "Pendente Aprovação Diretor Adjunto", "Diretor Adjunto"),
    PENDENTE_APROVACAO_DIRETOR_EXECUTIVO(10L, "Pendente Aprovação Diretor Executivo", "Diretor Executivo"),
    PENDENTE_APROVACAO_VICE_PRESIDENTE(11L, "Pendente Aprovação Vice-Presidente", "Vice-presidente"),
    APROVADO(12L, "Aprovado", SimulationApprovalFlowStep.FINISHED_APPROVAL),
    REPROVADO(13L, "Reprovado", SimulationApprovalFlowStep.FINISHED_APPROVAL),
    EXPIRADO(14L, "Expirado", SimulationApprovalFlowStep.FINISHED_APPROVAL),
    EMISSAO_DE_PEDIDO_EM_ANDAMENTO(15l, "Emissão de Pedido em Andamento"),
    EMISSAO_DE_PEDIDO_FINALIZADO(16l, "Emissão de Pedido Finalizado"),
    ENVIANDO_PEDIDOS(17L, "Enviando Pedidos", SimulationApprovalFlowStep.FINISHED_APPROVAL),
    PEDIDOS_ENVIADOS(18L, "Pedidos Enviados", SimulationApprovalFlowStep.FINISHED_APPROVAL),
    ERRO_NO_ENVIO_DOS_PEDIDOS(19L, "Erro no Envio dos Pedidos", SimulationApprovalFlowStep.FINISHED_APPROVAL);

    private final Long id;
    private final String name;
    private final SimulationApprovalFlowStep simulationApprovalFlowStep;
    private final String approvalGroupName;

    SimulationStatusEnum(Long id, String name, String approvalGroupName) {
        this.id = id;
        this.name = name;
        this.simulationApprovalFlowStep = SimulationApprovalFlowStep.PENDING_APPROVAL;
        this.approvalGroupName = approvalGroupName;
    }


    SimulationStatusEnum(Long id, String name, SimulationApprovalFlowStep simulationApprovalFlowStep) {
        this.id = id;
        this.name = name;
        this.simulationApprovalFlowStep = simulationApprovalFlowStep;
        this.approvalGroupName = null;
    }

    SimulationStatusEnum(Long id, String name) {
        this.id = id;
        this.name = name;
        this.simulationApprovalFlowStep = SimulationApprovalFlowStep.UNSTARTED_APPROVAL;
        this.approvalGroupName = null;
    }

    public static SimulationStatusEnum find(Long id) {
        for (SimulationStatusEnum value : SimulationStatusEnum.values()) {
            if (value.id.equals(id))
                return value;
        }
        throw new IllegalArgumentException(String.format("Unsupported SimulationStatusEnum %d.", id));
    }

    public SimulationStatusEntity getEntity(){
        return SimulationStatusEntity.builder().id(this.getId()).name(this.getName()).build();
    }

    public boolean isCancel(){return CANCEL.getId().equals(this.getId()); }
    public boolean isError(){return ERROR.getId().equals(this.getId()); }
    public boolean isProcessing(){return PROCESSING.getId().equals(this.getId()); }
    public boolean isReprovado() {return REPROVADO.getId().equals(this.getId()); }
    public boolean isApproved() {return APROVADO.getId().equals(this.getId()); }
    public boolean shouldNotify(){return this.getSimulationApprovalFlowStep().shouldNotify(); }
    public boolean isApprovalEnded(){return SimulationApprovalFlowStep.FINISHED_APPROVAL.equals(this.getSimulationApprovalFlowStep()); }
    public boolean isPendingApproval(){return !isNull(find(this.getId()).approvalGroupName) ; }

    public static boolean isCancel(Long id){return CANCEL.getId().equals(id); }
    public static boolean isProcessing(Long id){return PROCESSING.getId().equals(id); }
    public static boolean isDraft(Long id){return DRAFT.getId().equals(id); }
    public static boolean isPendingApproval(Long id){return !isNull(find(id).approvalGroupName) ; }
    public static boolean isError(Long id){return ERROR.getId().equals(id); }
    public static boolean isFinished(Long id){return FINISHED.getId().equals(id); }
    public static List<Long> getPendingApprovalIds() {
        return Arrays.stream(SimulationStatusEnum.values())
                .filter(SimulationStatusEnum::isPendingApproval)
                .map(SimulationStatusEnum::getId).collect(Collectors.toList());
    }

    private enum SimulationApprovalFlowStep {
        UNSTARTED_APPROVAL(false),
        STARTED_APPROVAL(false),
        PENDING_APPROVAL(true),
        FINISHED_APPROVAL(true);

        private boolean notify;

        SimulationApprovalFlowStep(boolean notify) {
            this.notify = notify;
        }

        public boolean shouldNotify() {
            return this.notify;
        }
    }

    public static List<Long> listIdWithApprovalGroupName() {
        return Stream.of(SimulationStatusEnum.values())
                .filter(e -> e.approvalGroupName != null && !e.approvalGroupName.isBlank())
                .map(SimulationStatusEnum::getId)
                .collect(Collectors.toList());
    }

}
