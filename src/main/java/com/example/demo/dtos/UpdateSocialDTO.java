package com.example.demo.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.query.Param;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class UpdateSocialDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 7100179587555243994L;

    private String facebook;
    private String linkedin;
    private String instagram;
    private String github;
    private String twitter;
    private String youtube;

}
