package com.cegeka.employeeplanning.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cegeka.employeeplanning.data.MitarbeiterVertrieb;
import com.cegeka.employeeplanning.data.dto.MitarbeiterDTO;
import com.cegeka.employeeplanning.exceptions.NoSuchMitarbeiterVertriebException;
import com.cegeka.employeeplanning.repositories.MitarbeiterVertriebRepository;
import com.cegeka.employeeplanning.util.EmployeeplanningUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MitarbeiterVertriebService extends EmployeeplanningUtil {
    @Autowired
    private MitarbeiterVertriebRepository mitarbeiterVertriebRepository;

    public MitarbeiterVertrieb getMitarbeiterVertriebById(Integer id)
    {
        return checkEntityExist(id);
    }

    public void deleteById(Integer id)
    {
        MitarbeiterVertrieb mitarbeiterVertrieb = checkEntityExist(id);
        mitarbeiterVertriebRepository.delete(mitarbeiterVertrieb);
    }

    public void update(MitarbeiterVertrieb mitarbeiterVertrieb)
    {
        checkEntityExist(mitarbeiterVertrieb.getId());
        mitarbeiterVertriebRepository.save(mitarbeiterVertrieb);
    }

    public List<MitarbeiterDTO> getMitarbeiterVertriebListOrderByName() {
        Iterable<MitarbeiterVertrieb> mitarbeiterOrderByName = mitarbeiterVertriebRepository.findMitarbeiterVertriebByOrderByName();
        List<MitarbeiterDTO> mitarbeiterDTOList = new ArrayList<MitarbeiterDTO>();

        for (MitarbeiterVertrieb mitarbeiter : mitarbeiterOrderByName) {
            String name = mitarbeiter.getName() + ", " + mitarbeiter.getVorname();
            MitarbeiterDTO mitarbeiterDTO = new MitarbeiterDTO(name, mitarbeiter.getId().toString());
            mitarbeiterDTOList.add(mitarbeiterDTO);
        }
        return mitarbeiterDTOList;
    }

    private MitarbeiterVertrieb checkEntityExist(Integer id)
    {
        Optional<MitarbeiterVertrieb> mitarbeiterVertrieb = mitarbeiterVertriebRepository.findById(id);
        if (!mitarbeiterVertrieb.isPresent())
        {
            throw new NoSuchMitarbeiterVertriebException();
        }
        return mitarbeiterVertrieb.get();
    }

}
