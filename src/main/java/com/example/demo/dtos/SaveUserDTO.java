package com.example.demo.dtos;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SaveUserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String email;

    private String password;

    private String cpf;

    private LocalDateTime birthDate;

    private String telephone;

    private String instagram;

    private String twitter;

    private String github;

    private String youtube;

    private String facebook;

    private String linkedin;
}
