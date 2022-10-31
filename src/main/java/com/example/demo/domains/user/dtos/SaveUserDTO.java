package com.example.demo.domains.user.dtos;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class SaveUserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    private String email;

    @NotNull
    private String password;
}
