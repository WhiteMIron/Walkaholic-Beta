package com.example.demo.item.model.dto;


import lombok.*;

import java.util.ArrayList;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class BuyitemInfo {
    private ArrayList<Long> itemId ;

}
