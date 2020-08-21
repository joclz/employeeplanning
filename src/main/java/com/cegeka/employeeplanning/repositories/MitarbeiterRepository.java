package com.cegeka.employeeplanning.repositories;

import com.cegeka.employeeplanning.data.Mitarbeiter;
import com.cegeka.employeeplanning.data.enums.Enums;
import org.springframework.data.repository.CrudRepository;

public interface MitarbeiterRepository extends CrudRepository<Mitarbeiter, Integer> {
    Iterable<Mitarbeiter> findMitarbeiterByMitarbeiterStatus(Enums.MitarbeiterStatus mitarbeiterStatus);
}
