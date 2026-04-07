package com.example.pets.repository;

import com.example.pets.model.Dog;
import org.springframework.data.repository.ListCrudRepository;

public interface DogRepository extends ListCrudRepository<Dog, Integer> {
}