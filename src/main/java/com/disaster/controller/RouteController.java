package com.disaster.controller;

import com.disaster.model.WeatherData;
import com.disaster.service.RouteService;
import com.disaster.service.WeatherService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class RouteController {

    private final WeatherService weatherService;

    private final RouteService routeService;


    public RouteController(
            WeatherService weatherService,
            RouteService routeService) {

        this.weatherService =
                weatherService;

        this.routeService =
                routeService;
    }


    @GetMapping("/route")
    public String routePage() {

        return "route";
    }


    @GetMapping("/api/route-risk")
    @ResponseBody
    public Map<String, Object> routeRisk(

            @RequestParam double lat,
            @RequestParam double lng) {


        Map<String, Object> result =
                new HashMap<>();


        try {

            WeatherData weather =
                    weatherService.getWeather(
                            lat,
                            lng
                    );


            int risk =
                    routeService
                            .calculateRouteRisk(
                                    weather
                            );


            result.put(
                    "riskScore",
                    risk
            );


            result.put(
                    "status",
                    routeService
                            .getRiskStatus(
                                    risk
                            )
            );


            result.put(
                    "condition",
                    weather.getCondition()
            );


            result.put(
                    "rain",
                    weather.getRainfall()
            );


            result.put(
                    "wind",
                    weather.getWindSpeed()
            );


        } catch (Exception e) {


            result.put(
                    "riskScore",
                    50
            );


            result.put(
                    "status",
                    "UNKNOWN"
            );


            result.put(
                    "condition",
                    "Weather unavailable"
            );
        }


        return result;
    }
}
