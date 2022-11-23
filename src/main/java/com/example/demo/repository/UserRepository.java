package com.example.demo.repository;

import com.example.demo.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Modifying
    @Query(
            "UPDATE User SET " +
            "facebook =:facebook, " +
            "linkedin =:linkedin, " +
            "instagram =:instagram, " +
            "github =:github, " +
            "twitter =:twitter, " +
            "youtube =:youtube " +
            "WHERE id =:id"
    )
    void updateSocials(
            @Param("id") Long id,
            @Param("facebook") String facebook,
            @Param("linkedin") String linkedin,
            @Param("instagram") String instagram,
            @Param("github") String github,
            @Param("twitter") String twitter,
            @Param("youtube") String youtube
    );

}
