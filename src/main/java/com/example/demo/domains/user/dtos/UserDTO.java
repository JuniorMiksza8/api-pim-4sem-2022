package com.example.demo.domains.user.dtos;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 7100179587555243994L;

    @NotNull
    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
