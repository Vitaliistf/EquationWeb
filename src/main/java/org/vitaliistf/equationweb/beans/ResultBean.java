package org.vitaliistf.equationweb.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import org.vitaliistf.equationweb.data.Point;
import org.vitaliistf.equationweb.service.Main;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Bean for storing and processing results of equation calculations.
 */
@Getter
@Named("resultBean")
@RequestScoped
public class ResultBean {
    private double[] xValues;
    private double[] yValues;
    private double sum;
    private double average;
    private double minValue;
    private int minIndex;
    private double maxValue;
    private int maxIndex;

    private LineChartModel chart;

    @Inject
    private InputBean inputBean;

    @Inject
    private Main main;

    /**
     * Calculates the results of the equation based on input values.
     */
    @PostConstruct
    public void calculateResults() {
        xValues = main.createXArray(inputBean.getStart(), inputBean.getEnd(), inputBean.getStep());
        yValues = main.createYArray(xValues);

        sum = main.calculateSum(yValues);
        average = main.calculateAverage(yValues);
        minIndex = main.getMinElementIndex(yValues);
        minValue = yValues[minIndex];
        maxIndex = main.getMaxElementIndex(yValues);
        maxValue = yValues[maxIndex];
        buildChart();
    }

    /**
     * Builds the line chart to display the graph of the equation.
     */
    private void buildChart() {
        chart = new LineChartModel();

        LineChartDataSet dataSet = new LineChartDataSet();
        List<Object> y = Arrays.stream(yValues)
                .mapToObj(e -> (Object) e)
                .toList();
        List<String> x = Arrays.stream(xValues)
                .mapToObj(Double::toString)
                .toList();

        dataSet.setData(y);
        dataSet.setLabel("Y(X)");
        dataSet.setBackgroundColor("black");

        ChartData data = new ChartData();
        data.addChartDataSet(dataSet);
        data.setLabels(x);

        chart.setData(data);
    }

    /**
     * Generates a list of points from the calculated x and y values.
     *
     * @return the list of points
     */
    public List<Point> getPoints() {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < yValues.length; i++) {
            points.add(new Point(xValues[i], yValues[i]));
        }
        return points;
    }
}
