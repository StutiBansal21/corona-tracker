package com.labproject.covidtracker.services;

import com.labproject.covidtracker.models.LocationInfo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/*
when the application runs we want some code to execute and call the url(having the covid confirmed cases) and get the
data here. This class is going to give us the data and when the application loads which makes the call to code and then
fetches the data.
 */

@Service//used with classes that provide some business functionalities.
public class CoronaVirusDataService {

    //this is the url from where the data is being fetched. this url is a .csv file which holds all the confirmed cases
    // upto the current data of all different locations
    private static String URL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<LocationInfo> allLocation=new ArrayList<>();//creating an arraylist of the locationInfo class so that after creating instance of this we can add info to it every time we get request

    //getter method
    public List<LocationInfo> getAllLocation() {
        return allLocation;
    }

    // this function is going to make the http call to the url to get/fetch the data
    @PostConstruct// used on a method that needs to be executed after dependency injection is done to perform any initialization.
    @Scheduled(cron="* * 1 * * *")//used when we want to schedule this method time period so that this project runs after a period of time and fetches the updated data.
    //method should have a void return type and should not have any parameters.
    public void fetchVirusData() throws IOException, InterruptedException {

        //new list so that while we are calling/using the old list and other person manipulates the list there is no concurrency problem created.
        List<LocationInfo> newLocation=new ArrayList<>();

        HttpClient client=HttpClient.newHttpClient();//to make http calls we make its client
        HttpRequest request=HttpRequest.newBuilder().uri(URI.create(URL)).build();//saying where we do we need to do the httpRequest

        //now get a response by sending the client this request and returns as a string
        HttpResponse<String>httpResponse=client.send(request, HttpResponse.BodyHandlers.ofString());
        //System.out.println(httpResponse.body());

        //to show the csv file data. to manage the printed string of data from the url we use csv library.To convert the
        // string to objects so that we can access each column as we want
        /*Some CSV files define header names in their first record. If configured, Apache Commons CSV can parse the
        header names from the first record. This will use the values from the first record as header names and skip the
        first record when iterating.
        */
        StringReader csvBodyReader=new StringReader(httpResponse.body());//instance of reader that passes a string
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        //looping throught the records which were the objects/columns returned when http request fetches the data.
        // we want to save the data in a model class so that we can use it easier
        for (CSVRecord record : records) {
            LocationInfo locationInfo=new LocationInfo();
            locationInfo.setState(record.get("Province/State"));
            locationInfo.setCountry(record.get("Country/Region"));
            locationInfo.setLatestCases(Integer.parseInt(record.get(record.size()-1)));//gives the last updated column
            System.out.println(locationInfo);
            newLocation.add(locationInfo);
            //String state = record.get("Province/State");
            //System.out.println(state);//prints the column we want : here prints all the province/state rom the url
        }
        this.allLocation=newLocation;

    }
}
