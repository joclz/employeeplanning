package com.cegeka.employeeplanning.service;

import com.cegeka.employeeplanning.data.MitarbeiterVertrieb;
import com.cegeka.employeeplanning.data.dto.MitarbeiterDTO;
import com.cegeka.employeeplanning.repositories.MitarbeiterVertriebRepository;
import com.cegeka.employeeplanning.util.EmployeeplanningUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MitarbeiterVertriebService extends EmployeeplanningUtil {
    @Autowired
    private MitarbeiterVertriebRepository mitarbeiterVertriebRepository;

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
}
