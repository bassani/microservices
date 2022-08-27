package org.bassani.examplemodellib.domain.entity.dbjor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

//user roles
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "TB_PERFIL")
public class ProfileEntity implements Serializable {
	private static final long serialVersionUID = 8223762236192924651L;

	@Id
	@Column(name = "CD_PERFIL")
	private Long id;

	@Column(name = "DS_PERFIL")
	private String name;

}
