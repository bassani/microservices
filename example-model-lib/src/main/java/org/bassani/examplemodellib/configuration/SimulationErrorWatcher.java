package org.bassani.examplemodellib.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * O método anotado terá uma possível exceção capturada por
 * {@link br.com.example.purchasesimulatorcomposition.configuration.SimulationErrorHandler SimulationErrorHandler}.
 * <p>
 * Apenas métodos contendo {@link br.com.example.purchasesimulatormodellib.domain.dto.SimulationDTO SimulationDTO} como
 * parâmetro podem ser anotados.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SimulationErrorWatcher {

}
