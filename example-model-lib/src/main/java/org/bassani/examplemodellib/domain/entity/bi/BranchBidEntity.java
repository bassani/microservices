package org.bassani.examplemodellib.domain.entity.bi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "TB_BID_FILIAL")
public class BranchBidEntity implements Serializable {

    private static final long serialVersionUID = -5429835360393020415L;

    @Id
	@Column(name = "CD_TIPO_FILIAL")
	private Long branchTypCode;

	@Column(name = "CD_FILIAL_R")
	private String branchCodeR;

}
