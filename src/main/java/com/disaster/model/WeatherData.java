package com.disaster.model;

public class WeatherData {

    private double temperature;

    private double humidity;

    private double windSpeed;

    private double rainfall;

    private double precipitationProbability;

    private double thunderstormProbability;

    private String condition;

    private int riskScore;

    private String riskLevel;

    public WeatherData() {
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(
            double temperature) {

        this.temperature =
                temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(
            double humidity) {

        this.humidity =
                humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(
            double windSpeed) {

        this.windSpeed =
                windSpeed;
    }

    public double getRainfall() {
        return rainfall;
    }

    public void setRainfall(
            double rainfall) {

        this.rainfall =
                rainfall;
    }

    public double getPrecipitationProbability() {
        return precipitationProbability;
    }

    public void setPrecipitationProbability(
            double precipitationProbability) {

        this.precipitationProbability =
                precipitationProbability;
    }

    public double getThunderstormProbability() {
        return thunderstormProbability;
    }

    public void setThunderstormProbability(
            double thunderstormProbability) {

        this.thunderstormProbability =
                thunderstormProbability;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(
            String condition) {

        this.condition =
                condition;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(
            int riskScore) {

        this.riskScore =
                riskScore;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(
            String riskLevel) {

        this.riskLevel =
                riskLevel;
    }
}
