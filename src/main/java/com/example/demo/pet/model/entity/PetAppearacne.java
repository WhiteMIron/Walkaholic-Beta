package com.example.demo.pet.model.entity;


import com.example.demo.level.model.entity.Level;
import com.example.demo.pet.model.entity.Pet;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
@Table(name="pet_appearance")
public class PetAppearacne {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="pet_filename")
    private String petFIleName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "pet_id")
    @JsonManagedReference
    private Level petId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinColumn (name = "evolution_id")
    private Pet evolutionId;


}
