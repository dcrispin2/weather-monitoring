package edu.iu.dcrispin.weathermonitoring.model;

import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Component
public class StatisticsDisplay
        implements Observer, DisplayElement{

    private double avgTemp;
    private double minTemp;
    private double maxTemp;
    private ArrayList<Double> temps = new ArrayList<>();

    private Subject weatherData;

    public StatisticsDisplay(Subject weatherData) {
        this.weatherData = weatherData;
    }

    @Override
    public String display() {
        String html = "";
        html += String.format("<div style=\"background-image: " +
                "url(/images/sky.webp); " +
                "height: 400px; " +
                "width: 647.2px;" +
                "display:flex;flex-wrap:wrap;justify-content:center;align-content:center;" +
                "\">");
        html += "<section>";
        html += String.format("<label>Average Temperature: %s</label><br />", avgTemp);
        html += String.format("<label>High Temperature: %s</label><br />", maxTemp);
        html += String.format("<label>Low Temperature: %s</label>", minTemp);
        html += "</section>";
        html += "</div>";
        return html;
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        temps.add((double)temperature);
        this.avgTemp = avTemps(temps);
        this.maxTemp = getHigh(temps);
        this.minTemp = getLow(temps);
    }

    private float avTemps(ArrayList temps){
        double total = 0;
        //double[] temps = tempArray.stream().mapToDouble(d -> d).toArray();
        for (int i = 0; i < temps.size(); i++){
            total = total + (double)temps.get(i);
        }

        return (float)(total / temps.size());
    }

    private double getHigh(ArrayList temps){
        double result = -9999;
        for (int i = 0; i < temps.size(); i++){
            if (result < (double)temps.get(i)){
                result = (double)temps.get(i);
            }
        }
        return result;
    }

    private double getLow(ArrayList temps){
        double result = 9999;
        for (int i = 0; i < temps.size(); i++){
            if (result > (double)temps.get(i)){
                result = (double)temps.get(i);
            }
        }
        return result;
    }

    @Override
    public String name() {
        return "Weather Stats Display";
    }

    @Override
    public String id() {
        return "weather-stats";
    }

    public void subscribe() {
        weatherData.registerObserver(this);
    }
    public void unsubscribe() {
        weatherData.removeObserver(this);
    }
}
