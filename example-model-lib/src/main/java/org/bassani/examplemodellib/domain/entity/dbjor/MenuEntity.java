package org.bassani.examplemodellib.domain.entity.dbjor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "TB_MENU")
@Getter
@Setter
@Where(clause = "FL_ATIVO = true")
public class MenuEntity implements Serializable {

    @Id
    @Column(name = "CD_MENU", nullable = false)
    private Integer id;

    @Column(name = "DS_MENU", nullable = false, length = 50)
    private String name;

    @Column(name = "NM_ICONE")
    private String icon;

    @Column(name = "FL_ATIVO")
    private Boolean active = true;

    @ToString.Exclude
    @OneToMany(
            mappedBy = "key.menu",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MenuItemEntity> items;

}
