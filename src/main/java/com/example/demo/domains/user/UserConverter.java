package com.example.demo.domains.user;

import com.example.demo.domains.user.dtos.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

@Slf4j
public class UserConverter {

    public static User fromVo(UserDTO vo){
        User user = new User();

        BeanUtils.copyProperties(vo,user);

        return user;
    }

    public static UserDTO toDTO(User user){
        UserDTO vo = new UserDTO();

        BeanUtils.copyProperties(user,vo);

        return vo;
    }

    public static Page<UserDTO> toPageVO(Page<User> page){
        return page.map(UserConverter::toDTO);
    }

}
