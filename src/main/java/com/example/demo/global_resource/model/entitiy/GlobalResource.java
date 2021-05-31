package com.example.demo.global_resource.model.entitiy;

import javax.persistence.*;

@Entity
@Table(name="global_resource")

public class GlobalResource {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @Column(name="resource_name")
    private String resourceName;

    @Column(name="resource_filename")
    private String resourceFilname;

    @Column(name="resource_type")
    private String resourceType;

    @Column(name="resource_code")
    private String resoueceCode;

}
