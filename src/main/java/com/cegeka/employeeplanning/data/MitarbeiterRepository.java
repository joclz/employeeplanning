package com.cegeka.employeeplanning.data;

import com.cegeka.employeeplanning.data.enums.Enums;
import org.springframework.data.repository.CrudRepository;

public interface MitarbeiterRepository extends CrudRepository<Mitarbeiter, Integer> {
    Iterable<Mitarbeiter> findMitarbeiterByMitarbeiterStatus(Enums.MitarbeiterStatus mitarbeiterStatus);
}
