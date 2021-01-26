package com.cegeka.employeeplanning.data.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MitarbeiterEinsatzDate {
    Integer maIntEinsatz;
    Integer maExtEinsatz;
    Integer maIntOhneEinsatz;
    Integer maExtOhneEinsatz;

    Integer actualMonth;
}
