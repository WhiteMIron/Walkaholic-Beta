package com.example.demo.item.model.entity;

import com.example.demo.user.model.entity.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Table(name="user_has_item")
@Entity
public class UserHasItem {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinColumn(name="user_id")
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinColumn(name="item_id")
    private Item itemId;


}
