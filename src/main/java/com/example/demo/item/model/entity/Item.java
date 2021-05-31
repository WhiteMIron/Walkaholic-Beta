package com.example.demo.item.model.entity;

import lombok.*;

import javax.persistence.*;

@Table (name = "item")
@Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="item_name")
    private String itemName;
    @Column(name="item_price")
    private Long itemPrice;
    @Column(name="item_type")
    private String itemType;

}
