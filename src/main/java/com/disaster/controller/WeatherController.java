package com.disaster.controller;

import com.disaster.model.WeatherData;
import com.disaster.service.WeatherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public String weatherPage() {
        return "weather";
    }

    @PostMapping("/weather")
    public String checkWeather(
            @RequestParam double latitude,
            @RequestParam double longitude,
            Model model) {

        try {

            WeatherData weather =
                    weatherService.getWeather(
                            latitude,
                            longitude
                    );

            model.addAttribute(
                    "weather",
                    weather
            );

        } catch (Exception e) {

            model.addAttribute(
                    "error",
                    "Unable to retrieve weather data. Check your Google Weather API key."
            );
        }

        return "weather";
    }
}