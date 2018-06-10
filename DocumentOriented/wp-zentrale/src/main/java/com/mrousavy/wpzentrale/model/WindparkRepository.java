package com.mrousavy.wpzentrale.model;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface WindparkRepository extends MongoRepository<Windpark, String> {
    public Windpark findByid(String id);
}
