package com.mrousavy.wpwindpark;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@RestController
public class WindparkRestController {

    private final String FILE_NAME = "parknodedata.xml";

    public String readXml() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            StringBuilder builder = new StringBuilder();

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                builder.append(sCurrentLine);
            }

            return builder.toString();
        }
    }

    @GetMapping("/xml")
    @ResponseBody
    public String getXml() {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        try {
            return readXml();
        } catch (Exception e) {
            e.printStackTrace();
            return "<error>" + e.getMessage() + "</error>";
        }
    }


    @GetMapping("/json")
    @ResponseBody
    public String getJson() {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        try {
            String xml = readXml();

            JSONObject json = XML.toJSONObject(xml);
            String prettyJson = json.toString(4);
            System.out.println("Read JSON: " + prettyJson);
            return prettyJson;
        } catch (Exception e) {
            e.printStackTrace();
            return "<error>" + e.getMessage() + "</error>";
        }
    }
}
