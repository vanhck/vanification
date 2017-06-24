package de.vanhck.View;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
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

    private Image getGreenIcon() {
        return new Image("", new FileResource(new File((new File("")).getAbsolutePath()
                + "\\src\\main\\resources\\icons\\green.png")));
    }

    private void buildOverviewChart() {
        addComponent(new Label("Übersicht"));

        GridLayout layout = new GridLayout();

        Label label = new Label(VaadinIcons.SMILEY_O.getHtml(), ContentMode.HTML);
        Label label2 = new Label(VaadinIcons.HEART.getHtml(), ContentMode.HTML);
        Label label1 = new Label(VaadinIcons.ALARM.getHtml(), ContentMode.HTML);

        addComponent(label2);
        addComponent(label);

        addComponent(label1);
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
