package com.mrousavy.wpwindpark;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@RestController
public class WindparkRestController {

    private final String FILE_NAME = "parknodedata.xml";

    @GetMapping("/xml")
    @ResponseBody
    public String getXml() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            StringBuilder builder = new StringBuilder();

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                builder.append(sCurrentLine);
            }

            String xml = builder.toString();

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
