package org.bassani.examplemodellib.domain.entity.pk;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MenuItemPrimaryKey implements Serializable {
    private static final long serialVersionUID = -1255585051237890671L;
    @Column(name = "CD_ITEM", nullable = false)
    private Integer itemId;
    @Column(name = "CD_MENU", nullable = false)
    private Integer menuId;

}