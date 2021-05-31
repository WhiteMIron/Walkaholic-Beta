package com.example.demo.pet.repository;

import com.example.demo.pet.model.entity.Pet;
import com.example.demo.user.model.dto.UserPetAppearanceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet,Long> {
//    @Query(value="select p.pet_filename as PetApperanceFileName from pet_appearance p  where p.pet_filename like concat(:appearanceInfo,'%') ", nativeQuery= true)
//    List<UserPetAppearanceInfo> finByAppearance(String appearanceInfo,Long id);

    @Query(value= "SELECT p.pet_filename as PetApperanceFileName FROM user u left JOIN evolution e ON u.level_id > e.evolution_level " +
            " OR u.level_id = e.evolution_level LEFT OUTER JOIN pet_appearance p ON p.evolUtion_id = e.id " +
            " WHERE u.id= :id and p.pet_filename like concat('___',:appearanceInfo,'눈%')  Order BY e.evolution_level DESC LIMIT 2",nativeQuery =  true)
    List<UserPetAppearanceInfo> finByAppearanceInfo(String appearanceInfo,Long id);

//    @Query(value= "SELECT p.pet_filename as PetApperanceFileName FROM user u left JOIN evolution e ON u.level_id > e.evolution_level " +
//            " OR u.level_id = e.evolution_level LEFT OUTER JOIN pet_appearance p ON p.evolUtion_id = e.id " +
//            " WHERE u.id= :id and p.pet_filename like concat('____',:appearanceInfo,'눈%')  Order BY e.evolution_level DESC LIMIT 2",nativeQuery =  true)
//    List<UserPetAppearanceInfo> finByPervicwAppearanceInfo(String appearanceInfo,Long id);



}

//    concat('%',:appearanceInfo,'%')