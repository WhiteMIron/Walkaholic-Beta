package com.example.demo.user.model.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserSignUpDto {


    private Long id;
    private String nickName;
    private String gender;
    private String weight;
    private String height;
    private String birth;


}
