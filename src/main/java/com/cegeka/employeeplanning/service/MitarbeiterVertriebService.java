package com.cegeka.employeeplanning.service;

import com.cegeka.employeeplanning.data.MitarbeiterVertrieb;
import com.cegeka.employeeplanning.data.util.MitarbeiterItem;
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

    public List<MitarbeiterItem> getMitarbeiterVertriebListOrderByName() {
        Iterable<MitarbeiterVertrieb> mitarbeiterOrderByName = mitarbeiterVertriebRepository.findMitarbeiterVertriebByOrderByName();
        List<MitarbeiterItem> mitarbeiterItemList = new ArrayList<MitarbeiterItem>();

        for (MitarbeiterVertrieb mitarbeiter : mitarbeiterOrderByName) {
            String name = mitarbeiter.getName() + ", " + mitarbeiter.getVorname();
            MitarbeiterItem mitarbeiterItem = new MitarbeiterItem(name, mitarbeiter.getId().toString());
            mitarbeiterItemList.add(mitarbeiterItem);
        }
        return mitarbeiterItemList;
    }
}
