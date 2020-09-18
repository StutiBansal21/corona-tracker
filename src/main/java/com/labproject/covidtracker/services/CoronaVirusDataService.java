package com.labproject.covidtracker.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/*
when the application runs we want some code to execute and call the url(having the covid confirmed cases) and get the
data here. This class is going to give us the data and when the application loads which makes the call to code and then
fetches the data.
 */

@Service
public class CoronaVirusDataService {

    private static String VIRUS_DATA_URL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    // this function is going to make the http call to the url to get/fetch the data
    @PostConstruct
    public void fetchVirusData() throws IOException, InterruptedException {
        HttpClient client=HttpClient.newHttpClient();
        HttpRequest request=HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();

        //now get a response by sending the client this request and returns as a string
        HttpResponse<String>httpResponse=client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(httpResponse.body());

    }
}
