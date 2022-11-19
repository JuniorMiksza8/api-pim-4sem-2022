package com.example.demo.repository;

import com.example.demo.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    public List<Car> findByUserId(Long id);

}