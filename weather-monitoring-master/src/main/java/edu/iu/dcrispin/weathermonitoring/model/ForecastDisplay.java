package edu.iu.dcrispin.weathermonitoring.model;

import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Component
public class ForecastDisplay
        implements Observer, DisplayElement{

    private float heatIndx;
    private Subject weatherData;

    public ForecastDisplay(Subject weatherData) {
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
        html += String.format("<label>Heat Index: %s</label><br />", heatIndx);
        html += "</section>";
        html += "</div>";
        return html;
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.heatIndx = findHeatIndex(temperature, humidity);
    }

    private float findHeatIndex(float t, float rh){
        //I'm not sure if we even have the necessary values to compute this
        //Humidity and pressure are both ambiguous and I have no idea how they relate to the
        //30 different values saturation temperatures or w/e needed to calculate RH properly
        //For the sake of completion and implementation (and since it runs on RNG anyway) I'm just going to assume humidity == relative humidity
        float index = (float)((16.923 + (0.185212 * t) + (5.37941 * rh) - (0.100254 * t * rh) +
                (0.00941695 * (t * t)) + (0.00728898 * (rh * rh)) +
                (0.000345372 * (t * t * rh)) - (0.000814971 * (t * rh * rh)) +
                (0.0000102102 * (t * t * rh * rh)) - (0.000038646 * (t * t * t)) + (0.0000291583 *
                (rh * rh * rh)) + (0.00000142721 * (t * t * t * rh)) +
                (0.000000197483 * (t * rh * rh * rh)) - (0.0000000218429 * (t * t * t * rh * rh)) +
                0.000000000843296 * (t * t * rh * rh * rh)) -
                (0.0000000000481975 * (t * t * t * rh * rh * rh)));
        return index;
    }

    @Override
    public String name() {
        return "Forecast";
    }

    @Override
    public String id() {
        return "forecast";
    }

    public void subscribe() {
        weatherData.registerObserver(this);
    }
    public void unsubscribe() {
        weatherData.removeObserver(this);
    }
}
