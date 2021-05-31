package com.example.demo.common;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

public class Response<T> {
    private int code=200;
    private String message;
    private T data;


}
