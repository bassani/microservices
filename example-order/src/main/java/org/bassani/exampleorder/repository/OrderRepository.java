package org.bassani.exampleorder.repository;

import org.bassani.examplemodellib.domain.entity.direct.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long>,
        JpaSpecificationExecutor<OrderEntity> {

    @Query(value = "SELECT SQ_ID_PEDIDO_COMPRA.nextval FROM dual", nativeQuery = true)
    Long getSequenceOrderId();

    @Query(value = "SELECT SQ_NR_PEDIDO_COMPRA.nextval FROM dual", nativeQuery = true)
    Long getSequenceOrderNumber();

    @Query(value = "SELECT SQ_NR_RELAT_COMPRAS.nextval FROM dual", nativeQuery = true)
    Long getSequencePurchaseReportNumber();


}
