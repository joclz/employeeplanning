package com.cegeka.employeeplanning.data.util;

public enum FilterOperation
{
    // @formatter:off
    LESS_EQUAL_THAN("<="),
    GREATER_EQUAL_THAN(">="),
    CONTAINS(":>"),
    GREATER_THAN(">"),
    LESS_THAN("<"),
    EQUALS("::");
    // @formatter:on

    private final String operationName;

    FilterOperation(String operationName /*, FilterPredicateFunction operation*/) {
        this.operationName = operationName;
    }

    public String getOperationName() {
        return operationName;
    }

}
