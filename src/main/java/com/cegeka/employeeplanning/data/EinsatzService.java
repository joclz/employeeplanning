package com.cegeka.employeeplanning.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EinsatzService {
    @Autowired
    private EinsatzRepository einsatzRepository;

    public <S extends Einsatz> S save(S s) {
        return einsatzRepository.save(s);
    }
}
