package com.eath.Service.Implement;

import com.eath.Service.InformationsNutritionnellesService;
import com.eath.dao.InformationsNutritionnellesRepository;
import com.eath.dao.ProduitsRepository;
import com.eath.entite.InformationsNutritionnelles;
import com.eath.exception.InformationsNutritionnellesNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InformationsNutritionnellesServiceImpl implements InformationsNutritionnellesService {

    private final InformationsNutritionnellesRepository infoRepo;
    private final ProduitsRepository produitsRepo;

    @Override
    public InformationsNutritionnelles addInformationsNutritionnelles(InformationsNutritionnelles info) {
        return infoRepo.save(info);
    }

    @Override
    public List<InformationsNutritionnelles> getAllInformationsNutritionnelles() {
        return infoRepo.findAll();
    }

    @Override
    public InformationsNutritionnelles getInformationsNutritionnellesById(Integer id) {
        return infoRepo.findById(id)
                .orElseThrow(() -> new InformationsNutritionnellesNotFoundException("InformationsNutritionnelles not found with id: " + id));
    }

    @Override
    public InformationsNutritionnelles updateInformationsNutritionnelles(Integer id, InformationsNutritionnelles info) {
        InformationsNutritionnelles existingInfo = getInformationsNutritionnellesById(id);
        existingInfo.setCalories(info.getCalories());
        existingInfo.setProteines(info.getProteines());
        existingInfo.setGraisses(info.getGraisses());
        existingInfo.setGlucides(info.getGlucides());
        existingInfo.setProduit(info.getProduit());
        return infoRepo.save(existingInfo);
    }

    @Override
    public void deleteInformationsNutritionnelles(Integer id) {
        if (!infoRepo.existsById(id)) {
            throw new InformationsNutritionnellesNotFoundException("InformationsNutritionnelles not found with id: " + id);
        }
        infoRepo.deleteById(id);
    }
}
