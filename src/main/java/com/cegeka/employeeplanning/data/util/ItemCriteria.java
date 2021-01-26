package com.cegeka.employeeplanning.data.util;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemCriteria
{
    private int page = 0;
    private int size = 25;
    private String sortActive = "";
    private String sortDirection = "";
    private List<String> filter;
}
