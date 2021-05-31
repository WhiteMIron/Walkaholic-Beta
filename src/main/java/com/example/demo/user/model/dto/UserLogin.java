package com.example.demo.user.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
//@ApiModel(description="RequstParam 로그인")
public  class UserLogin {
//    @ApiModelProperty(value = "uid", notes = "카카오톡 uid 값")
    private Long id;
}
