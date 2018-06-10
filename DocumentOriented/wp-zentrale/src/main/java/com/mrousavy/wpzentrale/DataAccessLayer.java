package com.mrousavy.wpzentrale;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrousavy.wpzentrale.model.*;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class DataAccessLayer {
    private static final String FILE_NAME = "config.txt";
    private WindparkRepository windparks;
    private WindparkVersionRepository windparkVersions;
    private WindradRepository windrads;


    public DataAccessLayer(WindparkRepository windparks, WindparkVersionRepository windparkVersions, WindradRepository windrads) {
        this.windparks = windparks;
        this.windparkVersions = windparkVersions;
        this.windrads = windrads;
    }

    /*
    Fetch and Save new data from the configured windparks
     */
    public void fetch() {
        ArrayList<Windpark> allWindparks = new ArrayList<>(windparks.findAll());

        ArrayList<String> urls = getUrls();
        for (String url : urls) {
            try {
                // Read JSON from URL
                String data = getWindparkData(url);

                // Parse JSON to WindparkVersion
                ObjectMapper mapper = new ObjectMapper();
                WindparkVersion windparkVersion = mapper.readValue(data, WindparkVersion.class);

                // Save each Windrad in WindparkVersion
                for (Windrad windrad : windparkVersion.getWindrads()) {
                    windrads.save(windrad);
                }

                // Save WindparkVersion
                windparkVersions.save(windparkVersion);

                // Add WindparkVersion to Windpark
                boolean found = false;
                for (Windpark windpark : allWindparks) {
                    if (windpark.getId().equals(windparkVersion.getId())) {
                        windpark.addVersion(windparkVersion);
                        windparks.save(windpark);
                        found = true;
                        break;
                    }
                }

                // If there was no saved Windpark with that ID, create it
                if (!found) {
                    Windpark windpark = new Windpark(windparkVersion.getId());
                    windpark.addVersion(windparkVersion);
                    windparks.save(windpark);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public ArrayList<String> getUrls() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            ArrayList<String> urls = new ArrayList<>();

            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                urls.add(currentLine.trim());
            }

            return urls;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static String getWindparkData(String url) throws URISyntaxException {
        RestTemplate rest = new RestTemplate();
        return rest.exchange(new URI(url), HttpMethod.GET, null, String.class).getBody();
    }
}
