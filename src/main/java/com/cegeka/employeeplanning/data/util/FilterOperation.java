package com.cegeka.employeeplanning.data.util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


public enum FilterOperation
{
    // @formatter:off
    LESS_EQUAL_THAN("<=", (b, k, v) -> b.lessThanOrEqualTo(k, v.toString())),
    GREATER_EQUAL_THAN(">=", (b, k, v) -> b.greaterThanOrEqualTo(k, v.toString())),
    CONTAINS(":>", (b, k, v) -> b.like(k, b.literal("%" + v + "%"))),
    GREATER_THAN(">", (b, k, v) -> b.greaterThan(k, v.toString())),
    LESS_THAN("<", (b, k, v) -> b.lessThan(k, v.toString())),
    EQUALS("::", (b, k, v) -> b.equal(k, v));
    // @formatter:on

    private final String operationName;
    private final FilterPredicateFunction operation;

    FilterOperation(String operationName, FilterPredicateFunction operation) {
        this.operationName = operationName;
        this.operation = operation;
    }

    public String getOperationName() {
        return operationName;
    }

    public Predicate build(CriteriaBuilder builder, Root<?> entity, String key, Object value) {
        return operation.predicate(builder, entity.get(key), castToRequiredType(entity.get(key).getJavaType(),value.toString()));
    }

    static FilterOperation parse(String str) {
        for (FilterOperation filter : FilterOperation.values()) {
            if (str.equals(filter.getOperationName())) {
                return filter;
            }
        }

        throw new RuntimeException(String.format("Filter operation '%s' not found", str));
//        throw new WrongFilterException(String.format("Filter operation '%s' not found", str));
    }

    private Object castToRequiredType(Class fieldType, String value) {
        if(fieldType.isAssignableFrom(Double.class)) {
          return Double.valueOf(value);
        } else if(fieldType.isAssignableFrom(Integer.class)) {
          return Integer.valueOf(value);
        } else if(Enum.class.isAssignableFrom(fieldType)) {
          return Enum.valueOf(fieldType, value);
        }
        return null;
    }

//    private Object castToRequiredType(Class fieldType, List<String> value) {
//        List<Object> lists = new ArrayList<>();
//        for (String s : value) {
//          lists.add(castToRequiredType(fieldType, s));
//        }
//        return lists;
//    }

    @FunctionalInterface
    interface FilterPredicateFunction {
        Predicate predicate(CriteriaBuilder builder, Path<String> key, Object value);
    }
}
