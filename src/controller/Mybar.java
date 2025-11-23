//package controller;
//
//public class InstructorController {
//
//
////    public int AverageMarkForLesson(String LessonID)
////    {
////
////    }
//}
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package controller;

import java.awt.Dimension;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.UIUtils;
import org.jfree.data.category.DefaultCategoryDataset;

public class Mybar extends ApplicationFrame {
    public Mybar(String title) {
        super(title);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue((double)2.0F, "Row 2", "Column 1");
        dataset.addValue((double)3.0F, "Row 2", "Column 2");
        dataset.addValue((double)2.0F, "Row 2", "Column 3");
        JFreeChart chart = ChartFactory.createBarChart(title, "Category", "Value", dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        this.setContentPane(chartPanel);
    }

}
