package com.mascara.oyo_booking_backend.specifications;

import com.mascara.oyo_booking_backend.entities.accommodation.AccomPlace;
import com.mascara.oyo_booking_backend.specifications.criteria.SearchCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 10/12/2023
 * Time      : 3:43 CH
 * Filename  : AccomPlaceSpecification
 */
public class AccomPlaceSpecification {
    public static Specification<AccomPlace> columnEqual(List<SearchCriteria> filterDTOList) {
        return new Specification<AccomPlace>() {
            @Override
            public Predicate toPredicate(Root<AccomPlace> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                filterDTOList.forEach(filter -> {
                    Predicate predicate;
                    switch (filter.getOperation()) {
                        case EQUAL:
                            predicate = criteriaBuilder.equal(root.get(filter.getKey()), filter.getValue());
                            break;
                        case LIKE:
                            predicate = criteriaBuilder.like(root.get(filter.getKey()), "%" + filter.getValue() + "%");
                            break;
                        case LESS_THAN:
                            predicate = criteriaBuilder.lt(root.get(filter.getKey()), (Number) filter.getValue());
                            break;
                        case GREATER_THAN:
                            predicate = criteriaBuilder.gt(root.get(filter.getKey()), (Number) filter.getValue());
                            break;
                        case IN:
                            if (filter.getValue() instanceof List) {
                                List<Object> values = (List<Object>) filter.getValue();
                                List<Predicate> predicateFacility = new ArrayList<>();
                                for (Object value : values) {
                                    predicateFacility.add(criteriaBuilder.equal(root.get(filter.getKey()), value));
                                }
                                predicate= criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
                            } else {
                                throw new RuntimeException("Value for IN operation must be a list");
                            }
                            break;
                        default:
                            throw new RuntimeException("Operation not supported");
                    }
                    predicates.add(predicate);
                });
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
