package org.bassani.examplemodellib.domain.entity.dbjor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "TB_MENU_ITEM", indexes = {@Index(name = "IX_TB_MENU_ITEM_01", columnList = "CD_MENU")})
@Where(clause = "FL_ATIVO = true")
public class MenuItemEntity implements Serializable {
    @ToString.Exclude
	@EmbeddedId
	private PrimaryKey key;

	@Column(name = "DS_ITEM")
	private String name;

	@Column(name = "NM_URL")
	private String url;

	@Column(name = "FL_ATIVO")
	private Boolean active = true;

	@Column(name = "NM_ICONE")
	private String icon;

    @ToString.Exclude
    @ManyToMany(cascade = {javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.MERGE})
    @JoinTable(name = "TB_PERFIL_MENU_ITEM", joinColumns = {@JoinColumn(name = "CD_ITEM"),
            @JoinColumn(name = "CD_MENU")},
            inverseJoinColumns = @JoinColumn(name = "CD_PERFIL", referencedColumnName = "CD_PERFIL"))
    private Set<ProfileEntity> profiles = new HashSet<>();

    @Getter
    @Setter
	@Embeddable
    @EqualsAndHashCode
	public static class PrimaryKey implements Serializable {

		@Column(name = "CD_ITEM")
		private Integer id;

		@ManyToOne(optional = false)
		@JoinColumn(name = "CD_MENU", foreignKey = @ForeignKey(name = "FK_TB_MENU_ITEM_01"))
		private MenuEntity menu;
	}
}
