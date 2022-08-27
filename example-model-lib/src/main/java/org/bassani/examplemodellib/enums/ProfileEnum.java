package org.bassani.examplemodellib.enums;

import br.com.example.purchasesimulatormodellib.domain.entity.dbjor.ProfileEntity;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
@Getter
public enum ProfileEnum {
    //Role
    ANALISTA(1L,"Analista"),
    COMPRADOR(2L,"Comprador"),
    COORDENADOR(3L,"Coordenador"),
    GERENTE(4L,"Gerente"),
    DIRETOR_ADJUNTO(5L,"Diretor Adjunto ou Gerente Executivo"),
    DIRETOR_EXECUTIVO(6L,"Diretor Executivo"),
    VICE_PRESIDENTE(7L,"Vice-presidente");

    private final Long id;
    private final String name;

    private static class Holder {
        static Map<Long, ProfileEnum> MAP = new HashMap<>();
    }

    ProfileEnum(Long id, String name) {
        this.id = id;
        this.name = name;
        Holder.MAP.put(id, this);
    }

    public static ProfileEnum find(Long id) {
        ProfileEnum result = Holder.MAP.get(id);
        if (result == null) {
            throw new IllegalArgumentException(String.format("Unsupported ProfileEnum %d.", id));
        }
        return result;
    }

    public ProfileEntity getEntity(){
        return ProfileEntity.builder().id(this.getId()).name(this.getName()).build();
    }

}
