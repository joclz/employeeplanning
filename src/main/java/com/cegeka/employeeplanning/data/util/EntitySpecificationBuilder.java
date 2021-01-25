package com.cegeka.employeeplanning.data.util;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;

public class EntitySpecificationBuilder<T>
{
    public static <T> Optional<Specification<T>> parse(List<String> filters) {
        if (filters == null || filters.isEmpty()) {
            return Optional.empty();
        }

        List<Specification> criterias = mapSpecifications(filters);
        if (criterias.isEmpty()) {
            return Optional.empty();
        }

        Specification<T> root = Specification.where(criterias.get(0));
        for (int index = 1; index < criterias.size(); index++) {
            root = Specification.where(root).and(criterias.get(index));
        }
        return Optional.of(root);
    }

    @SuppressWarnings("rawtypes")
    private static <T> List<Specification> mapSpecifications(List<String> filters) {
        return filters.stream().map(str -> {
            for (FilterOperation op : FilterOperation.values()) {
                int index = str.indexOf(op.getOperationName());
                if (index > 0) {
                    String key = str.substring(0, index);
                    String value = str.substring(index + op.getOperationName().length());
                    String operation = op.getOperationName();
                    {
                        // TODO. Joins

                        switch (operation){
                        // TODO DAtums

                        case "::":
                           return (Specification<T>) (root, query, cb) ->
                              cb.equal(root.get(key),
                               castToRequiredType(root.get(key).getJavaType(),
                                                  value.toString()));

                        case ">":
                           return (Specification<T>) (root, query, cb) ->
                              cb.gt(root.get(key),
                               (Number) castToRequiredType(
                                      root.get(key).getJavaType(),
                                      value.toString()));

                        case ">=":
                            return (Specification<T>) (root, query, cb) ->
                               cb.ge(root.get(key),
                                (Number) castToRequiredType(
                                       root.get(key).getJavaType(),
                                       value.toString()));

                        case "<":
                           return (Specification<T>) (root, query, cb) ->
                              cb.lt(root.get(key),
                               (Number) castToRequiredType(
                                      root.get(key).getJavaType(),
                                      value.toString()));

                        case "<=":
                            return (Specification<T>) (root, query, cb) ->
                               cb.le(root.get(key),
                                (Number) castToRequiredType(
                                       root.get(key).getJavaType(),
                                       value.toString()));

                        case ":>":
                          return (Specification<T>) (root, query, cb) ->
                              cb.like(root.get(key),
                                              "%"+value.toString()+"%");


                        default:
                          throw new RuntimeException("Operation not supported yet");
                        }
                    }


//                    return (Specification<T>) (root, query, cb) -> op.build(cb, root, key, value);
                }
            }

            return null;
        }).filter(s -> s != null).collect(Collectors.toList());
    }

    private static Object castToRequiredType(Class fieldType, String value) {
        if(fieldType.isAssignableFrom(Double.class)) {
          return Double.valueOf(value);
        } else if(fieldType.isAssignableFrom(Integer.class)) {
          return Integer.valueOf(value);
        } else if(Enum.class.isAssignableFrom(fieldType)) {
          return Enum.valueOf(fieldType, value);
        }
        return null;
    }

}
