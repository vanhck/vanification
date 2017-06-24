package de.vanhck.View;

import com.vaadin.server.FileResource;
import com.vaadin.ui.*;
import de.vanhck.Util;
import de.vanhck.data.User;
import de.vanhck.data.UserDAO;

import java.io.File;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jonas on 24.06.2017.
 *
 * @author Jonas Kett
 * @version 1.0
 */
public class UserView extends ClosableView {
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
        addComponent(new Label("Übersicht"));

        GridLayout layout = new GridLayout(5,2);
        layout.setSpacing(true);
        layout.setSizeFull();
        layout.addComponent(new Label("Score"),0,0);
        layout.addComponent(new Label("Zeitraum"), 0 ,1);
        layout.addComponent(new Label("Gesamt"),1,1);
        layout.addComponent(new Label("lestzter Monat"),2,1);
        layout.addComponent(new Label("letzte Woche"), 3, 1);
        layout.addComponent(new Label("letzte Fahrt"), 4, 1);

        int all = 750;
        int lastMonth = 600;
        int lastWeek = 650;
        int lastDrive = 500;

        layout.addComponent(new Label("" + all), 1,0);
        layout.addComponent(new Label("" + lastMonth), 2, 0);
        layout.addComponent(new Label("" + lastWeek), 3, 0);
        layout.addComponent(new Label("" + lastDrive) ,4 ,0);

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
                return (int) Math.round(Util.getEndScore(o1.getScores()) - Util.getEndScore(o2.getScores()));

            }
        });
        for (int i = 1; i <= tmpUsers.size(); i++) {
            bestUsers.addComponent(new Label("" + i), 0, i);
            User user = tmpUsers.get(i - 1);
            long scoreInt = Math.round(Util.getEndScore(user.getScores()));
            bestUsers.addComponent(new Label("" + scoreInt), 1, i);
            bestUsers.addComponent(new Label(user.getName()), 2, i);
        }


        //TODO get number of users to display


        addComponent(new Label("Bestenliste"));
        addComponent(bestUsers);
    }

    private void buildCompareThing() {
        ComboBox comboBox = new ComboBox("Person zum Vergleich auswählen");
        comboBox.setItems("option1","option2");
        comboBox.setTextInputAllowed(false);

        addComponent(comboBox);

        GridLayout layout = new GridLayout(3,7);

        layout.addComponent(new Label("Score"),0,0);
        layout.addComponent(new Label("gefahrene Kilometer"),0,1);
        layout.addComponent(new Label("durchschnittlicher Verbrauch"),0, 2);
        layout.addComponent(new Label("Vollbremsungen"), 0, 3);
        layout.addComponent(new Label("harte Beschleunigungen"), 0, 4);
        layout.addComponent(new Label("Zeit der Querbeschleunigung"), 0, 5);
        layout.addComponent(new Label("Zeit der gleichm. Fahrt"),0 ,6);

        addComponent(layout);
    }
}
