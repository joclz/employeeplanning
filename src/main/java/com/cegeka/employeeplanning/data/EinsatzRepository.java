package com.cegeka.employeeplanning.data;

import com.cegeka.employeeplanning.data.enums.Enums;
import org.springframework.data.repository.CrudRepository;

public interface EinsatzRepository extends CrudRepository<Einsatz, Integer> {

    Iterable<Einsatz> findEinsaetzeByEinsatzStatus(Enums.EinsatzStatus status);

}
