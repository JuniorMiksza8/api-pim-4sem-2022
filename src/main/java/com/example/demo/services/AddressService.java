package com.example.demo.services;

import com.example.demo.models.Address;
import com.example.demo.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    AddressRepository repository;

    public Address save(Address data){

        Address address = repository.save(data);

        return address;
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    public List<Address> list(Long userId){
        return repository.findByUserId(userId);
    }
}
