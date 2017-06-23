package de.vanhck.View;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;
import com.vaadin.ui.Label;
import de.vanhck.data.User;

import java.io.File;

/**
 * Created by Jonas on 24.06.2017.
 *
 * @author Jonas Kett
 * @version 1.0
 */
public class UserView extends ClosableView {
    private User user;
    private GridLayout bestUsers;

    public UserView(User user, Layout parentComponent, Layout toClose) {
        super(parentComponent, toClose);
        this.user = user;




        //System.out.println((new File("")).getAbsolutePath());
        Image image = new Image("", new FileResource(new File((new File("")).getAbsolutePath()
                + "\\src\\main\\resources\\price.png")));
        image.setCaption("Preis für den Besten");
        addComponent(image);

        setComponentAlignment(image, Alignment.TOP_CENTER);
        buildBestUserList();
        buildOverviewChart();

    }

    private void buildOverviewChart() {
        Chart chart = new Chart(ChartType.COLUMN);
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Übersicht");


        final String[] categories = new String[] { "1", "2", "13",
                "4", "5", "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15", "16", "17",
                "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
        };

        XAxis x1 = new XAxis();
        //conf.addxAxis(x1);
        //x1.setCategories(categories);
        //x1.setReversed(false);
        x1.setMin(0);
        x1.setMax(1000);
        x1.setTitle("test");
        conf.addxAxis(x1);

        YAxis y = new YAxis();
        y.setCategories(categories);
        y.setReversed(false);
        //y.setMin(0);
        //y.setMax(1000);
        //y.setTitle(new AxisTitle("Score"));
        conf.addyAxis(y);

        conf.addSeries(new ListSeries("sas", 500, 600, 300, 400, 1000, 20));

        chart.drawChart(conf);
        addComponent(chart);

    }

    private void buildBestUserList() {
        //TODO get number of users to display
        bestUsers = new GridLayout(3,11);
        bestUsers.setSizeFull();
        Label rank = new Label("Platz");
        Label score = new Label("Score");
        Label name = new Label("Name");
        bestUsers.addComponent(rank);
        bestUsers.addComponent(score);
        bestUsers.addComponent(name);

        addComponent(new Label("Bestenliste"));
        addComponent(bestUsers);
    }
}
