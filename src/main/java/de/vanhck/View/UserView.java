package de.vanhck.View;

import com.vaadin.data.HasValue;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;
import de.vanhck.Util;
import de.vanhck.data.User;
import de.vanhck.data.UserDAO;

import java.io.File;
import java.util.*;

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
        layout.addComponent(new Label("" + Util.getEndScore(user.getScores())),1,0);
        layout.addComponent(new Label("lestzter Monat"),2,1);
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.MONTH, -1);
        Date newDate = cal.getTime();
        layout.addComponent(new Label("" + Util.getScoreFromTime(user.getScores(), newDate)),2,0);
        layout.addComponent(new Label("letzte Woche"), 3, 1);
        cal.setTime(now);
        cal.add(Calendar.WEEK_OF_MONTH, -1);
        newDate = cal.getTime();
        layout.addComponent(new Label("" + Util.getScoreFromTime(user.getScores(),newDate)),3,0);
        layout.addComponent(new Label("letzte Fahrt"), 4, 1);
        layout.addComponent(new Label("" + Util.getLastScore(user.getScores())),4,0);


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




        addComponent(new Label("Bestenliste"));
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

        GridLayout layout = new GridLayout(3,8);
        Label myName = new Label();
        layout.setSizeFull();
        myName.setValue(user.getName());
        Label myScore = new Label();
        myScore.setValue(Util.getEndScore(user.getScores()) + "");
        Label compareScore = new Label();

        Label compareName = new Label();
        compareName.setValue(comboBox.getValue() == null ? "" : comboBox.getValue().toString());

        comboBox.addValueChangeListener(new HasValue.ValueChangeListener() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent valueChangeEvent) {
                compareName.setValue(comboBox.getValue() == null ? "" : comboBox.getValue().toString());
                compareScore.setValue(comboBox.getValue() == null ? "" : "" + Util.getEndScore(((User) comboBox.getValue()).getScores()));
            }
        });

        layout.addComponent(myName, 1, 0);
        layout.addComponent(compareName, 2, 0);
        layout.addComponent(myScore, 1, 1);
        layout.addComponent(compareScore, 2, 1);
        layout.addComponent(new Label("Score"),0,1);
        layout.addComponent(new Label("gefahrene Kilometer"),0,2);
        layout.addComponent(new Label("durchschnittlicher Verbrauch"),0, 3);
        layout.addComponent(new Label("Vollbremsungen"), 0, 4);
        layout.addComponent(new Label("harte Beschleunigungen"), 0, 5);
        layout.addComponent(new Label("Zeit der Querbeschleunigung"), 0, 6);
        layout.addComponent(new Label("Zeit der gleichm. Fahrt"),0 ,7);

        addComponent(layout);
    }
}
