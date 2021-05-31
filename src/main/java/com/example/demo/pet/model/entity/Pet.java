package com.example.demo.pet.model.entity;

import javax.persistence.*;

@Entity
@Table(name="pet")

public class Pet {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="pet_name")
    private String characterName;

}
