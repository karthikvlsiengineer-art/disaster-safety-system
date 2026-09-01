package com.disaster.service;

import com.disaster.model.WeatherData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class WeatherService {

    private final HttpClient httpClient =
            HttpClient.newHttpClient();

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    public WeatherData getWeather(
            double latitude,
            double longitude)
            throws Exception {

        String url =
                "https://api.open-meteo.com/v1/forecast"
                + "?latitude=" + latitude
                + "&longitude=" + longitude
                + "&current="
                + "temperature_2m,"
                + "relative_humidity_2m,"
                + "precipitation,"
                + "rain,"
                + "weather_code,"
                + "wind_speed_10m,"
                + "wind_gusts_10m"
                + "&hourly=precipitation_probability"
                + "&timezone=auto";

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();

        HttpResponse<String> response =
                httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        if (response.statusCode() != 200) {

            throw new RuntimeException(
                    "Weather API error: "
                    + response.statusCode()
            );
        }

        JsonNode root =
                objectMapper.readTree(
                        response.body()
                );

        JsonNode current =
                root.path("current");

        WeatherData weather =
                new WeatherData();

        weather.setTemperature(
                current.path(
                        "temperature_2m"
                ).asDouble()
        );

        weather.setHumidity(
                current.path(
                        "relative_humidity_2m"
                ).asDouble()
        );

        weather.setWindSpeed(
                current.path(
                        "wind_speed_10m"
                ).asDouble()
        );

        weather.setRainfall(
                current.path(
                        "rain"
                ).asDouble()
        );

        int weatherCode =
                current.path(
                        "weather_code"
                ).asInt();

        weather.setCondition(
                getWeatherCondition(
                        weatherCode
                )
        );

        double precipitationProbability =
                getCurrentPrecipitationProbability(
                        root
                );

        weather.setPrecipitationProbability(
                precipitationProbability
        );

        weather.setThunderstormProbability(
                getThunderstormProbability(
                        weatherCode
                )
        );

        calculateRisk(
                weather,
                weatherCode,
                current.path(
                        "wind_gusts_10m"
                ).asDouble()
        );

        return weather;
    }


    private double getCurrentPrecipitationProbability(
            JsonNode root) {

        JsonNode hourly =
                root.path("hourly");

        JsonNode probabilities =
                hourly.path(
                        "precipitation_probability"
                );

        if (
            probabilities.isArray()
            && probabilities.size() > 0
        ) {

            return probabilities
                    .get(0)
                    .asDouble();
        }

        return 0;
    }


    private String getWeatherCondition(
            int code) {

        if (code == 0) {
            return "Clear Sky";
        }

        if (
            code == 1 ||
            code == 2 ||
            code == 3
        ) {
            return "Cloudy";
        }

        if (
            code == 45 ||
            code == 48
        ) {
            return "Fog";
        }

        if (
            code >= 51 &&
            code <= 57
        ) {
            return "Drizzle";
        }

        if (
            code >= 61 &&
            code <= 67
        ) {
            return "Rain";
        }

        if (
            code >= 71 &&
            code <= 77
        ) {
            return "Snow";
        }

        if (
            code >= 80 &&
            code <= 82
        ) {
            return "Rain Showers";
        }

        if (
            code == 85 ||
            code == 86
        ) {
            return "Snow Showers";
        }

        if (
            code >= 95 &&
            code <= 99
        ) {
            return "Thunderstorm";
        }

        return "Unknown";
    }


    private double getThunderstormProbability(
            int code) {

        if (
            code >= 95 &&
            code <= 99
        ) {
            return 100;
        }

        return 0;
    }


    private void calculateRisk(
            WeatherData weather,
            int weatherCode,
            double windGust) {

        int score = 0;

        double rain =
                weather.getRainfall();

        double wind =
                weather.getWindSpeed();

        double precipitation =
                weather
                    .getPrecipitationProbability();


        if (rain >= 20) {

            score += 40;

        } else if (rain >= 5) {

            score += 20;
        }


        if (wind >= 70) {

            score += 35;

        } else if (wind >= 40) {

            score += 20;
        }


        if (windGust >= 80) {

            score += 20;

        } else if (windGust >= 50) {

            score += 10;
        }


        if (precipitation >= 80) {

            score += 15;

        } else if (precipitation >= 50) {

            score += 8;
        }


        if (
            weatherCode >= 95 &&
            weatherCode <= 99
        ) {

            score += 25;
        }


        if (
            weatherCode == 82
            || weatherCode == 65
            || weatherCode == 67
        ) {

            score += 20;
        }


        score =
                Math.min(
                        score,
                        100
                );


        weather.setRiskScore(
                score
        );


        if (score >= 60) {

            weather.setRiskLevel(
                    "HIGH RISK"
            );

        } else if (score >= 30) {

            weather.setRiskLevel(
                    "WARNING"
            );

        } else {

            weather.setRiskLevel(
                    "NORMAL"
            );
        }
    }
}
