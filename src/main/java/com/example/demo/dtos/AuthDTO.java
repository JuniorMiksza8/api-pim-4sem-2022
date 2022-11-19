package com.example.demo.dtos;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class AuthDTO {

    @NotNull
    private String username;

    @NotNull
    private String password;

}
