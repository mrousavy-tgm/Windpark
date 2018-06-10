package com.mrousavy.wpzentrale;

import com.mrousavy.wpzentrale.model.WindparkRepository;
import com.mrousavy.wpzentrale.model.WindparkVersionRepository;
import com.mrousavy.wpzentrale.model.WindradRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WpZentraleApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(WpZentraleApplication.class, args);
	}

	@Autowired
	private WindparkRepository windparks;

	@Autowired
	private WindparkVersionRepository windparkVersions;

	@Autowired
	private WindradRepository windrads;

	@Override
	public void run(String... args) throws Exception {

	    /*windparks.deleteAll();
        windparkVersions.deleteAll();
        windrads.deleteAll();*/

		DataAccessLayer dal = new DataAccessLayer(windparks, windparkVersions, windrads);
		dal.fetch();
	}
}
