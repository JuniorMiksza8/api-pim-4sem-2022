package com.example.demo.services;

import com.example.demo.models.Address;
import com.example.demo.models.Car;
import com.example.demo.models.User;
import com.example.demo.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    CarRepository repository;

    public Car save(Car data){
        Car car = repository.save(data);

        return car;
    }

    public void delete(Long id){

        repository.deleteById(id);
    }

    public List<Car> list(Long userId){

        return repository.findByUserId(userId);
    }

}
