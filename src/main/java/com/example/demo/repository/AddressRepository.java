package com.example.demo.repository;

import com.example.demo.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    public List<Address> findByUserId(Long id);

}