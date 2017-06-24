package de.vanhck.View;

import com.vaadin.data.HasValue;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;
import de.vanhck.Util;
import de.vanhck.data.Score;
import de.vanhck.data.User;
import de.vanhck.data.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by Jonas on 24.06.2017.
 *
 * @author Jonas Kett
 * @version 1.0
 */
public class UserView extends ClosableView {


    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private User user;
    private GridLayout bestUsers;
    private UserDAO userSaver;

    public UserView(UserDAO userSaver, User user, Layout parentComponent, Layout toClose) {
        super(parentComponent, toClose);
        this.user = user;
        this.userSaver = userSaver;

        Image image = new Image("", new FileResource(new File((new File("")).getAbsolutePath()
                + "\\src\\main\\resources\\price.png")));
        image.setCaption("Preis für den Besten");
        addComponent(image);

        setComponentAlignment(image, Alignment.TOP_CENTER);
        addComponent(new Label(""));
        buildBestUserList();
        addComponent(new Label(""));
        buildOverviewChart();
        addComponent(new Label(""));
        buildCompareThing();
    }

    private void buildOverviewChart() {
        addComponent(Util.makeBold(new Label("Übersicht")));

        GridLayout layout = new GridLayout(5,2);
        layout.setSpacing(true);
        layout.setSizeFull();
        layout.addComponent(new Label("Score"),0,0);
        layout.addComponent(new Label("Zeitraum"), 0 ,1);
        layout.addComponent(new Label("Gesamt"),1,1);
        layout.addComponent(new Label("" + Math.round(Util.getEndScore(user.getScores()))),1,0);
        layout.addComponent(new Label("lestzter Monat"),2,1);
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.MONTH, -1);
        Date newDate = cal.getTime();
        layout.addComponent(new Label("" + Math.round(Util.getScoreFromTime(user.getScores(), newDate))),2,0);
        layout.addComponent(new Label("letzte Woche"), 3, 1);
        cal.setTime(now);
        cal.add(Calendar.WEEK_OF_MONTH, -1);
        newDate = cal.getTime();
        layout.addComponent(new Label("" + Math.round(Util.getScoreFromTime(user.getScores(),newDate))),3,0);
        layout.addComponent(new Label("letzte Fahrt"), 4, 1);
        layout.addComponent(new Label("" + Math.round(Util.getLastScore(user.getScores()))),4,0);


        addComponent(layout);
    }

    private void buildBestUserList() {
        bestUsers = new GridLayout(3,11);
        bestUsers.setSizeFull();
        Label rank = new Label("Platz");
        Label score = new Label("Score");
        Label name = new Label("Name");
        bestUsers.addComponent(rank);
        bestUsers.addComponent(score);
        bestUsers.addComponent(name);


        Iterable<User> users = userSaver.findAll();
        List<User> tmpUsers = new LinkedList<>();
        for (User user : users) {
            tmpUsers.add(user);
        }
        tmpUsers.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return (int) Math.round(Util.getEndScore(o2.getScores()) - Util.getEndScore(o1.getScores()));

            }
        });
        for (int i = 1; i <= tmpUsers.size(); i++) {


            Label index = new Label("" + i);
            long scoreInt = Math.round(Util.getEndScore(tmpUsers.get(i - 1).getScores()));
            Label scoreLabel = new Label("" + scoreInt);
            Label nameLabel = new Label(tmpUsers.get(i - 1).getName());
            User user = tmpUsers.get(i - 1);
            if (user.getName().equals(this.user.getName())) {
                index = Util.makeBold(index);
                scoreLabel = Util.makeBold(scoreLabel);
                nameLabel = Util.makeBold(nameLabel);
            }
            bestUsers.addComponent(index, 0, i);
            bestUsers.addComponent(scoreLabel, 1, i);
            bestUsers.addComponent(nameLabel, 2, i);
        }




        addComponent(Util.makeBold(new Label("Bestenliste")));
        addComponent(bestUsers);
    }



    private void buildCompareThing() {
        ComboBox comboBox = new ComboBox("Person zum Vergleich auswählen");
        Iterable<User> users = userSaver.findAll();
        Collection<User> usersWithoutMe = new HashSet<>();
        for(User user : users) {
            if (!user.getName().equals(this.user.getName())) {
                usersWithoutMe.add(user);
            }
        }
        comboBox.setItems(usersWithoutMe);
        comboBox.setItemCaptionGenerator(new ItemCaptionGenerator() {
            @Override
            public String apply(Object o) {
                return o.toString();
            }
        });


        //comboBox.setItems("option1","option2");
        comboBox.setTextInputAllowed(false);

        addComponent(comboBox);

        GridLayout layout = new GridLayout(3,9);

        layout.setSizeFull();
        GridColumn col1 = new GridColumn(new GridItem(user),layout,1);
        GridColumn col2 = new GridColumn(null, layout, 2);



        comboBox.addValueChangeListener(new HasValue.ValueChangeListener() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent valueChangeEvent) {
            col2.setGridItem(comboBox.getValue() == null ? null : new GridItem((User) comboBox.getValue()));
            }
        });


        layout.addComponent(new Label("Score"),0,1);
        layout.addComponent(new Label("gefahrene Kilometer"),0,2);
        layout.addComponent(new Label("durchschnittlicher Verbrauch"),0, 3);
        layout.addComponent(new Label("Vollbremsungen"), 0, 4);
        layout.addComponent(new Label("harte Beschleunigungen"), 0, 5);
        layout.addComponent(new Label("harte Seitwärtsbeschleunigungen"), 0 ,6);
        layout.addComponent(new Label("Stops"), 0, 7);
        layout.addComponent(new Label("Zeit der gleichm. Fahrt"),0 ,8);

        addComponent(layout);
    }


    private class GridColumn {
        private GridItem item;

        private Label name;
        private Label score;
        private Label km;
        private Label avConsumption;
        private Label hardStops;
        private Label hardAccelerations;
        private Label hardSidewaysAccelerations;
        private Label stops;
        private Label timeWithConstKmH;

        public GridColumn(GridItem item, GridLayout layout, int column) {
            name = new Label();
            score = new Label();
            km = new Label();
            avConsumption = new Label();
            hardStops = new Label();
            hardAccelerations = new Label();
            hardSidewaysAccelerations = new Label();
            stops = new Label();
            timeWithConstKmH = new Label();
            setGridItem(item);

            layout.addComponent(name, column, 0);
            layout.addComponent(score, column, 1);
            layout.addComponent(km, column, 2);
            layout.addComponent(avConsumption, column, 3);
            layout.addComponent(hardStops, column, 4);
            layout.addComponent(hardAccelerations, column, 5);
            layout.addComponent(hardSidewaysAccelerations, column, 6);
            layout.addComponent(stops, column, 7);
            layout.addComponent(timeWithConstKmH, column, 8);
        }

        public void setGridItem(GridItem item) {
            this.item = item;
            name.setValue(item == null ? "" : item.getName());
            score.setValue(item == null ? "" : item.getScore() + "");
            km.setValue(item == null ? "" : item.getDrivenKilometers() + "");
            avConsumption.setValue(item == null ? "" : item.getAverageConsumption() + "");
            hardStops.setValue(item == null ? "" : item.getHardStopsCount() + "");
            hardAccelerations.setValue(item == null ? "" : item.getHardAccelerationCount() + "");
            stops.setValue(item == null ? "" : item.getStopsCount() + "");
            timeWithConstKmH.setValue(item == null ? "" : item.getConstantVelocityKm() + "");
            hardSidewaysAccelerations.setValue(item == null ? "" : item.getHardSidewayAcceleration() + "");
        }
    }

    private class GridItem {
        private DecimalFormat df;

        private String name;
        private Double score;
        private Double drivenKilometers;
        private Double averageConsumption;
        private Double hardStopsCount;
        private Double hardAccelerationCount;
        private Double constantVelocityKm;

        public int getHardSidewayAcceleration() {
            return (int) Math.round(hardSidewayAcceleration);
        }

        private Double hardSidewayAcceleration;

        public String getName() {
            return name;
        }

        public int getScore() {
            return (int) Math.round(score);
        }

        public String getDrivenKilometers() {
            return df.format(drivenKilometers);
        }

        public String getAverageConsumption() {
            return df.format(averageConsumption);
        }

        public int getHardStopsCount() {
            return (int) Math.round(hardStopsCount);
        }

        public int getHardAccelerationCount() {
            return (int) Math.round(hardAccelerationCount);
        }

        public String getConstantVelocityKm() {
            return df.format(constantVelocityKm);
        }

        public int getStopsCount() {
            return (int) Math.round(stopsCount);
        }

        private Double stopsCount;

        public GridItem(User user) {
            df = new DecimalFormat("#.#");
            df.setRoundingMode(RoundingMode.CEILING);
            name = user.getName();

            score = Util.getEndScore(user.getScores());

            drivenKilometers = 0d;
            averageConsumption = 0d;
            hardStopsCount = 0d;
            hardAccelerationCount = 0d;
            hardSidewayAcceleration = 0d;
            constantVelocityKm = 0d;
            stopsCount = 0d;

            for (Score score: user.getScores()) {
                drivenKilometers += score.getCourse();
                averageConsumption += score.getAverageFuelConsumption();
                hardStopsCount += score.getHardAccelerationCount();
                hardAccelerationCount += score.getHardAccelerationCount();
                constantVelocityKm += score.getConstantVelocityKm();
                stopsCount += score.getStopCount();
                hardSidewayAcceleration += score.getSidewaysAcceleration();
            }
            averageConsumption = averageConsumption / user.getScores().size();

        }


    }
}
