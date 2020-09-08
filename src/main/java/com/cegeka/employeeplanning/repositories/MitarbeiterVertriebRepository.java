package com.cegeka.employeeplanning.repositories;

import com.cegeka.employeeplanning.data.MitarbeiterVertrieb;
import org.springframework.data.repository.CrudRepository;

public interface MitarbeiterVertriebRepository extends CrudRepository<MitarbeiterVertrieb, Integer> {
    Iterable<MitarbeiterVertrieb> findMitarbeiterVertriebByOrderByName();
}
