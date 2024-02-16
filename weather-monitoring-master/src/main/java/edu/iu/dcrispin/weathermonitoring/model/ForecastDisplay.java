package edu.iu.dcrispin.weathermonitoring.model;

import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Component
public class ForecastDisplay
        implements Observer, DisplayElement{


    private Subject weatherData;
    public ForecastDisplay(Subject weatherData) {
        this.weatherData = weatherData;
    }


    @Override
    public String display() {
        return null;
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {

    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public String id() {
        return null;
    }
}
