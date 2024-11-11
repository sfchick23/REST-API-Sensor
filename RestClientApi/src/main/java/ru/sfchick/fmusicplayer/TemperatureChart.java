package ru.sfchick.fmusicplayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchart.*;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.style.CategoryStyler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TemperatureChart {

    public static void main(String[] args) throws IOException {
        String url = "http://localhost:8080/measurements";
        int maxNumMeasurements = 10; // Максимальное количество измерений

        List<Double> temperatures = ResponseApi.getTemperature(url, maxNumMeasurements);

        // Создаем массивы данных для графика
        String[] xCategories = new String[temperatures.size()];
        double[] yData = temperatures.stream().mapToDouble(Double::doubleValue).toArray();

        for (int i = 0; i < xCategories.length; i++) {
            xCategories[i] = Integer.toString(i + 1); // Категории для оси X
        }

        createAndShowChart(xCategories, yData);
    }

    private static void createAndShowChart(String[] xCategories, double[] yData) {
        // Создание графика
        CategoryChart chart = new CategoryChartBuilder()
                .width(800)
                .height(600)
                .title("Температуры")
                .xAxisTitle("Номер измерения")
                .yAxisTitle("Температура (°C)")
                .build();

        // Настройка стиля графика
        CategoryStyler styler = chart.getStyler();
        styler.setLegendVisible(false);
        styler.setMarkerSize(8);  // Размер маркера (точки)
        styler.setPlotGridLinesVisible(false);  // Скрыть сетку
        styler.setDefaultSeriesRenderStyle(CategorySeries.CategorySeriesRenderStyle.Line);  // Линии вместо столбцов

        // Преобразование строковых категорий в целочисленные значения
        double[] xValues = new double[xCategories.length];
        for (int i = 0; i < xCategories.length; i++) {
            xValues[i] = Integer.parseInt(xCategories[i]);
        }

        // Добавление серии данных
        chart.addSeries("Температура", xValues, yData);

        // Отображение графика
        new SwingWrapper<>(chart).displayChart();
    }
}
