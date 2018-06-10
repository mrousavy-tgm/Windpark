package com.mrousavy.wpzentrale.model;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface WindparkVersionRepository extends MongoRepository<WindparkVersion, String> {

    public ArrayList<WindparkVersion> findByid(String id);

}
