package com.example.demo.regis_term.model.entity;

import javax.persistence.*;

@Entity
@Table(name="regis_term")
public class RegisTerm {

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    private Long id;
    @Column(name="registerm_name")
    private String registerm_name;
    @Column(name="registerm_content")
    private String getRegisterm_content;





}
