package com.disaster.service;

import com.disaster.model.WeatherData;
import org.springframework.stereotype.Service;

@Service
public class RouteService {

    public int calculateRouteRisk(
            WeatherData weather) {

        int risk =
                weather.getRiskScore();

        String condition =
                weather.getCondition() == null
                        ? ""
                        : weather.getCondition()
                        .toLowerCase();

        if (condition.contains("thunder")) {
            risk += 15;
        }

        if (condition.contains("storm")) {
            risk += 20;
        }

        if (condition.contains("heavy rain")) {
            risk += 20;
        }

        if (condition.contains("hail")) {
            risk += 15;
        }

        return Math.min(
                risk,
                100
        );
    }

    public String getRiskStatus(
            int score) {

        if (score >= 60) {
            return "DANGER";
        }

        if (score >= 30) {
            return "CAUTION";
        }

        return "LOWER RISK";
    }
}