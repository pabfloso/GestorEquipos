package com.pruebatecnica.GestorEquipos.specs;


import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class GenericSpec<T> implements Specification<T> {

    private static final long serialVersionUID = 1900581010229669687L;

    private List<SearchCriteria> list;

    public GenericSpec() {
        this.list = new ArrayList<>();
    }

    public GenericSpec(List<SearchCriteria> list) {
        this.list = list;
    }

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        List<Predicate> predicates = new ArrayList<>();

        for (SearchCriteria criteria : list) {
            if (criteria.getValue() == null && CollectionUtils.isEmpty(criteria.getValues()) && criteria.getIgnoreNull()) {
                continue;
            }

            if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
                predicates.add(builder.greaterThan(
                        root.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
                predicates.add(builder.lessThan(
                        root.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
                predicates.add(builder.greaterThanOrEqualTo(
                        root.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
                predicates.add(builder.lessThanOrEqualTo(
                        root.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
                predicates.add(builder.notEqual(
                        root.get(criteria.getKey()), criteria.getValue()));
            } else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                predicates.add(builder.equal(
                        root.get(criteria.getKey()), criteria.getValue()));
            } else if (criteria.getOperation().equals(SearchOperation.NULL_OR_EQUAL)) {
                Predicate predicateNullOrEqual = builder.or(
                        builder.isNull(root.get(criteria.getKey())),
                        builder.equal(root.get(criteria.getKey()), criteria.getValue())
                );
                predicates.add(predicateNullOrEqual);
            } else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
                predicates.add(builder.like(
                        builder.lower(root.get(criteria.getKey())),
                        "%" + criteria.getValue().toString().toLowerCase() + "%"));
            } else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
                predicates.add(builder.like(
                        builder.lower(root.get(criteria.getKey())),
                        criteria.getValue().toString().toLowerCase() + "%"));
            } else if (criteria.getOperation().equals(SearchOperation.IN)) {
                predicates.add(builder.in(
                        root.get(criteria.getKey())).value(criteria.getValues()));
            } else if (criteria.getOperation().equals(SearchOperation.NOT_IN)) {
                predicates.add(builder.not(
                        root.get(criteria.getKey())).in(criteria.getValues()));
            } else if (criteria.getOperation().equals(SearchOperation.NOT_EMPTY)) {
                predicates.add(builder.isNotEmpty(
                        root.get(criteria.getKey())
                ));
                Predicate predicateNotNullAndNotEmpty = builder.and(
                        builder.isNotNull(root.get(criteria.getKey())),
                        builder.isNotEmpty(root.get(criteria.getKey()))
                );
                predicates.add(predicateNotNullAndNotEmpty);
            } else if (criteria.getOperation().equals(SearchOperation.IS_NULL)) {
                predicates.add(builder.isNull(
                        root.get(criteria.getKey())
                ));
            } else if (criteria.getOperation().equals(SearchOperation.IS_NOT_NULL)) {
                predicates.add(builder.isNotNull(
                        root.get(criteria.getKey())
                ));
            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }


}
