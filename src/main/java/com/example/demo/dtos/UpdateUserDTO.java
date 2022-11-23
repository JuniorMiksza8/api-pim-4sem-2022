package com.example.demo.dtos;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UpdateUserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String email;

    private String cpf;

    private LocalDateTime birthDate;

    private String telephone;

}

