package com.example.demo.services;

import com.example.demo.configs.WebSecurity;
import com.example.demo.dtos.UpdateSocialDTO;
import com.example.demo.dtos.UpdateUserDTO;
import com.example.demo.models.User;
import com.example.demo.dtos.SaveUserDTO;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
public class UserService  {

    @Autowired
    private UserRepository repository;

    @Autowired
    WebSecurity security;

    public User save(SaveUserDTO data){
        User user = new User();

        if(data.getId() != null){
            Optional<User> loaded = repository.findById(data.getId());
            if(loaded.isPresent()) user = loaded.get();
        }

        BeanUtils.copyProperties(data,user);

        user.setEmail(data.getEmail());

        if(data.getPassword() != null){
            user.setPassword(security.passwordEncoder().encode(data.getPassword()));
        }

        user = this.repository.save(user);

        return user;

    }

    public User update(Long id,UpdateUserDTO data){
        log.info("dto {}",data.toString());
        Optional<User> loaded = repository.findById(id);

        if(loaded.isEmpty()) return null;

        User user = loaded.get();

        log.info("loaded {}",user.toString());

        BeanUtils.copyProperties(data,user);

        log.info("copied {}",user.toString());

        user = this.repository.save(user);

        return user;

    }

    public Page<User> paginate(Pageable pageable){
        Page<User> paginated = this.repository.findAll(pageable);

        return paginated;
    }

    public Optional<User> findByEmail(String email){
        return this.repository.findByEmail(email);
    }

    @Transactional
    public Optional<User> updateSocial(Long id,UpdateSocialDTO data){
        this.repository.updateSocials(
                id,
                data.getFacebook(),
                data.getLinkedin(),
                data.getInstagram(),
                data.getGithub(),
                data.getTwitter(),
                data.getYoutube()
        );

        return this.repository.findById(id);
    }

}
