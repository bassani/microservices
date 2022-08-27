package org.bassani.examplemodellib.domain.entity.dbjor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "TB_SIMULACAO_PARAMETRO")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@SequenceGenerator(name = "SequenceTbSimulacaoParametro", sequenceName = "SQ_SIMULACAO_PARAMETRO",
        allocationSize = 1, initialValue = 1)
public class SimulationParametersEntity implements Serializable {

    private static final long serialVersionUID = 7960326302972187088L;

    @Id
    @Column(name = "ID_SIMULACAO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceTbSimulacaoParametro")
    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "CD_TIPO_SIMULACAO", foreignKey = @ForeignKey(name = "FK_TB_SIMULACAO_PARAMETRO_02"))
    private SimulationTypeEntity simulationType;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "simulationParameters", optional = false)
    @PrimaryKeyJoinColumn
    private OrderTypeParamEntity orderTypeParam;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "CD_PEDIDO_COMPRA_CLASSIF", foreignKey = @ForeignKey(name = "FK_TB_SIMULACAO_PARAMETRO_01"))
    private ClassificationEntity classification;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "simulationParameters", fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private NewPaymentTermSimParamEntity newPaymentTerm;

    @Column(name = "DS_OBSERVACAO", nullable = false)
    private String note;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "CD_BASE_CALCULO", foreignKey = @ForeignKey(name = "FK_TB_SIMULACAO_PARAMETRO_03"))
    private CalculationBasisEntity calculationBasis;

    @Column(name = "QT_DIA_VENDA")
    private Long saleDay;

    @Column(name = "FL_APENAS_INATIVOS", nullable = false)
    private Boolean onlyInactive;

    @Column(name = "CD_OPERADOR", nullable = false)
    private Long operatorId;

    @Column(name = "ID_KEYCLOAK_USER")
    private String keycloakUserId;

    @CreationTimestamp
    @Column(name = "DT_CADASTRO", nullable = false)
    private LocalDateTime registerDate;

    @Column(name = "DT_EXPIRACAO")
    private LocalDateTime expirationDate;

    @Column(name = "FL_KIT_PROMO")
    private Boolean promoPack;

    @Column(name = "FL_COTADOS")
    private Boolean quoted;

    @Column(name = "FL_INATIVO_TEMPORARIO", nullable = false)
    private Long temporaryInactiveCode;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simulationParameters")
    private List<DistributionCenterSimParamEntity> distributionCenters;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simulationParameters")
    private List<SupplierSimParamEntity> suppliers;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simulationParameters")
    private List<CategorySimParamEntity> categories;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simulationParameters")
    private List<SubCategorySimParamEntity> subcategories;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "simulationParameters", fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private GeneralDiscountEntity generalDiscount;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simulationParameters")
	private List<ProductDiscountEntity> productDiscounts;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "simulationParameters", fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private AnticipationTypeSimulationEntity anticipation;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "simulationParameters", fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private ValueTypeSimulationEntity value;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "simulationParameters", fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private StockCoverageTypeSimulationEntity stockCoverage;
    
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "CD_SIMULACAO_STATUS", foreignKey = @ForeignKey(name = "FK_TB_SIMULACAO_PARAMETRO_04"))
    private SimulationStatusEntity status;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "simulationParameters", fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private BudgetSimParamEntity budget;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "simulationParameters", fetch = FetchType.LAZY)
    private SimulationApprovalCompetencyEntity approvalCompetency;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simulationParameters")
    private List<SimulationApprovalFlowEntity> approvalFlow;

	@PrePersist
    public void updateChildEntities() {
        if (getDistributionCenters() != null) getDistributionCenters().forEach(dc -> dc.setSimulationParameters(this));
        if (getSuppliers() != null) getSuppliers().forEach(supplier -> supplier.setSimulationParameters(this));
        if (getCategories() != null) getCategories().forEach(category -> category.setSimulationParameters(this));
        if (getSubcategories() != null) getSubcategories().forEach(sub -> sub.setSimulationParameters(this));
        if (getGeneralDiscount() != null) getGeneralDiscount().setSimulationParameters(this);
        if (getProductDiscounts() != null) getProductDiscounts().forEach(prod -> prod.setSimulationParameters(this));
        if (getAnticipation() != null) getAnticipation().setSimulationParameters(this);
        if (getValue() != null) getValue().setSimulationParameters(this);
        if (getStockCoverage() != null) getStockCoverage().setSimulationParameters(this);
        if (getOrderTypeParam() != null) getOrderTypeParam().setSimulationParameters(this);
        if (getNewPaymentTerm() != null) getNewPaymentTerm().setSimulationParameters(this);
        if (getBudget() != null) getBudget().setSimulationParameters(this);
    }

    public Boolean isActive() {
        return !onlyInactive;
    }
}