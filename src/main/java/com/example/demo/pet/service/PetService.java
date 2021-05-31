package com.example.demo.pet.service;


import com.example.demo.pet.repository.PetRepository;
import com.example.demo.user.model.dto.UserPetAppearanceInfo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository){
        this.petRepository = petRepository;
    }


    @Transactional
    public List<UserPetAppearanceInfo> getPetAppearanceInfo(String appearceInfo,Long level){

        return petRepository.finByAppearanceInfo(appearceInfo,level);

    }

}
