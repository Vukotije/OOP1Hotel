package viewAdmin;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;

import entitiesEnums.ReservationStatus;
import reports.Report;

public class ChartsPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public ChartsPanel() {
        setLayout(new BorderLayout());

        JPanel mainChartPanel = createMonthlyProfitChart();
        JPanel pieChartsPanel = createPieChartsPanel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mainChartPanel, pieChartsPanel);
        splitPane.setResizeWeight(0.67); // 2/3 for the main chart, 1/3 for pie charts
        splitPane.setDividerSize(0);
        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel createMonthlyProfitChart() {
        Map<String, List<Double>> profitData = Report.getMonthlyRevenueByRoomType(12);
        
        XYChart chart = new XYChartBuilder().width(800).height(600)
                .title("Monthly Profit by Room Type")
                .xAxisTitle("Month")
                .yAxisTitle("Profit")
                .build();

        chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setYAxisDecimalPattern("$#,###.##");
        chart.getStyler().setDatePattern("MMM");
        chart.getStyler().setXAxisLabelRotation(45);

        List<Date> xData = new ArrayList<>();
        LocalDate now = LocalDate.now();
        for (int i = 11; i >= 0; i--) {
            xData.add(java.sql.Date.valueOf(now.minusMonths(i).withDayOfMonth(1)));
        }

        for (Map.Entry<String, List<Double>> entry : profitData.entrySet()) {
            chart.addSeries(entry.getKey(), xData, entry.getValue());
        }

        return new XChartPanel<>(chart);
    }

    private JPanel createPieChartsPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 0, 10));
        panel.add(createMaidDistributionChart());
        panel.add(createReservationStatusChart());
        return panel;
    }

    private JPanel createMaidDistributionChart() {
        Map<String, Integer> maidData = Report.getMaidDistribution(30);

        PieChart chart = new PieChartBuilder().width(300).height(250)
                .title("Maid assigned rooms (30 Days)")
                .build();

        chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideE);

        for (Map.Entry<String, Integer> entry : maidData.entrySet()) {
            chart.addSeries(entry.getKey(), entry.getValue());
        }

        return new XChartPanel<>(chart);
    }

    private JPanel createReservationStatusChart() {
        Map<ReservationStatus, Integer> statusData = Report.reservationReport(LocalDate.now().minusDays(30), LocalDate.now());

        PieChart chart = new PieChartBuilder().width(300).height(250)
                .title("Reservations Status (30 Days)")
                .build();

        chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideE);

        for (Map.Entry<ReservationStatus, Integer> entry : statusData.entrySet()) {
            chart.addSeries(entry.getKey().toString(), entry.getValue());
        }

        return new XChartPanel<>(chart);
    }
}