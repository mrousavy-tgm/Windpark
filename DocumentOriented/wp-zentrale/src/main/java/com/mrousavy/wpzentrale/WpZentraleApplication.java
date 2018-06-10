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

	/*
	Delete all existing DB storage
	 */
	private void deleteExisting() {
        System.out.println("Deleting all Windparks...");
        windparks.deleteAll();
        windparkVersions.deleteAll();
        windrads.deleteAll();
        System.out.println("Deleted all Windparks!");
    }

	@Override
	public void run(String[] args) {
        System.out.println("Starting App...");
        deleteExisting();

		DataAccessLayer dal = new DataAccessLayer(windparks, windparkVersions, windrads);

		// Fetch data all 5 seconds
		while (true) {
		    try {
                System.out.println("Fetching Data...");
                dal.fetch();
                System.out.println("Fetched Data! Waiting 5s...");
                Thread.sleep(5000);
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
                break;
            }
        }

        System.out.println("Program exited!");
	}
}
