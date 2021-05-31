package com.example.demo.level.model.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name="level")
public class Level {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private Long need_exp;


}
