package com.michael_delivery.backend.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class GenericSpecification<T> {

    public Specification<T> contains(String fieldName, String value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (StringUtils.hasText(value)) {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(fieldName)),
                        "%" + value.toLowerCase() + "%"
                );
            }
            return null;
        };
    }

    public Specification<T> equals(String fieldName, Object value) {
        return (root, query, criteriaBuilder) -> {
            if (value != null) {
                return criteriaBuilder.equal(root.get(fieldName), value);
            }
            return null;
        };
    }

    public Specification<T> greaterThan(String fieldName, Comparable value) {
        return (root, query, criteriaBuilder) -> {
            if (value != null) {
                return criteriaBuilder.greaterThan(root.get(fieldName), value);
            }
            return null;
        };
    }

    public Specification<T> lessThan(String fieldName, Comparable value) {
        return (root, query, criteriaBuilder) -> {
            if (value != null) {
                return criteriaBuilder.lessThan(root.get(fieldName), value);
            }
            return null;
        };
    }

    public Specification<T> in(String fieldName, Iterable<?> values) {
        return (root, query, criteriaBuilder) -> {
            if (values != null && values.iterator().hasNext()) {
                return root.get(fieldName).in(values);
            }
            return null;
        };
    }

    public Specification<T> dateAfter(String fieldName, Object date) {
        return (root, query, criteriaBuilder) -> {
            if (date == null) {
                return null;
            }

            if (date instanceof Date) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(fieldName), (Date) date);
            } else if (date instanceof LocalDate) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(fieldName), (LocalDate) date);
            } else if (date instanceof LocalDateTime) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(fieldName), (LocalDateTime) date);
            }

            throw new IllegalArgumentException("Date type not supported: " + date.getClass());
        };
    }

    public Specification<T> dateBefore(String fieldName, Object date) {
        return (root, query, criteriaBuilder) -> {
            if (date == null) {
                return null;
            }

            if (date instanceof Date) {
                return criteriaBuilder.lessThanOrEqualTo(root.get(fieldName), (Date) date);
            } else if (date instanceof LocalDate) {
                return criteriaBuilder.lessThanOrEqualTo(root.get(fieldName), (LocalDate) date);
            } else if (date instanceof LocalDateTime) {
                return criteriaBuilder.lessThanOrEqualTo(root.get(fieldName), (LocalDateTime) date);
            }

            throw new IllegalArgumentException("Date type not supported: " + date.getClass());
        };
    }

    public Specification<T> dateBetween(String fieldName, Object startDate, Object endDate) {
        return Specification.where(dateAfter(fieldName, startDate))
                .and(dateBefore(fieldName, endDate));
    }

    public Specification<T> dateEquals(String fieldName, Object date) {
        return (root, query, criteriaBuilder) -> {
            if (date == null) {
                return null;
            }

            if (date instanceof LocalDate) {
                LocalDate localDate = (LocalDate) date;
                LocalDateTime startOfDay = localDate.atStartOfDay();
                LocalDateTime endOfDay = localDate.plusDays(1).atStartOfDay().minusNanos(1);

                return criteriaBuilder.and(
                        criteriaBuilder.greaterThanOrEqualTo(root.get(fieldName), startOfDay),
                        criteriaBuilder.lessThanOrEqualTo(root.get(fieldName), endOfDay)
                );
            } else if (date instanceof Date) {
                return criteriaBuilder.equal(root.get(fieldName), date);
            } else if (date instanceof LocalDateTime) {
                return criteriaBuilder.equal(root.get(fieldName), date);
            }
            throw new IllegalArgumentException("Date type not supported: " + date.getClass());
        };
    }
}