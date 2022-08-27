package org.bassani.exampleorder.repository.specification;

import org.bassani.examplemodellib.domain.entity.dbjor.CategorySimParamEntity;
import org.bassani.examplemodellib.domain.entity.dbjor.SupplierSimParamEntity;
import org.bassani.examplemodellib.domain.entity.direct.OrderEntity;
import org.bassani.examplemodellib.domain.request.OrderParamRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

@AllArgsConstructor
public class OrderSpecification implements Specification<OrderEntity> {

    private static final long serialVersionUID = 5017605602023939839L;

    private OrderParamRequest orderRequest;

    private void add(Predicate predicate, Expression expression) {
        predicate.getExpressions().add(expression);
    }

    @Override
    public Predicate toPredicate(Root<OrderEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        Predicate predicate = cb.conjunction();

        if (orderRequest.getFinalDate() != null) {
            add(predicate, cb.between(root.get("initialDate"),
                    orderRequest.getInitialDate().atStartOfDay(),
                            orderRequest.getFinalDate().atTime(23, 59, 59)));
        } else if (orderRequest.getInitialDate() != null) {
            add(predicate, cb.between(root.get("initialDate"),
                    orderRequest.getInitialDate().atStartOfDay(),
                    orderRequest.getInitialDate().atTime(23, 59, 59)));
        }

        Optional.ofNullable(orderRequest.getOrderId()).ifPresent(orderId ->
                add(predicate, cb.equal(root.get("orderId"), orderId))
        );

        Optional.ofNullable(orderRequest.getOrderNumber()).ifPresent(orderNumber ->
                add(predicate, cb.equal(root.get("orderNumber"), orderNumber))
        );

        if(!CollectionUtils.isEmpty(orderRequest.getOperatorIds())) {
            Optional.of(orderRequest.getOperatorIds())
                    .ifPresent(operatorIds -> add(predicate, cb.in(root.get("operatorId")).value(operatorIds)));
        }

        if (!CollectionUtils.isEmpty(orderRequest.getParentSupplierIds())) {
            Optional.of(orderRequest.getParentSupplierIds()).ifPresent(parentSupplierId -> {
                Join<OrderEntity, SupplierSimParamEntity> joinSupplierSimParam = root.join("suppliers");
                add(predicate, cb.in(joinSupplierSimParam.get("parentSupplierIds")).value(parentSupplierId));
            });
        }

        if (!CollectionUtils.isEmpty(orderRequest.getSupplierIds())) {
            Optional.of(orderRequest.getSupplierIds()).ifPresent(supplierIds -> {
                Join<OrderEntity, SupplierSimParamEntity> joinSupplierSimParam = root.join("suppliers");
                add(predicate, cb.in(joinSupplierSimParam.get("id")).value(supplierIds));
            });
        }

        if (!CollectionUtils.isEmpty(orderRequest.getCategoryIds())) {
            Optional.of(orderRequest.getCategoryIds()).ifPresent(categoryIds -> {
                Join<OrderEntity, CategorySimParamEntity> joinCategorySimParam = root.join("categories");
                add(predicate, cb.in(joinCategorySimParam.get("id")).value(categoryIds));
            });
        }

        return predicate;
    }
}
