package de.vanhck.View;

import com.vaadin.server.FileResource;
import com.vaadin.ui.*;

import java.io.File;

/**
 * Created by Jonas on 23.06.2017.
 *
 * @author Jonas Kett
 * @version 1.0
 */
public class MainView extends VerticalLayout {
    private HorizontalLayout horizontalLayout;
    Label company;

    public MainView() {
        horizontalLayout = new HorizontalLayout();
        Image imageLogo = new Image("", new FileResource(new File((new File("")).getAbsolutePath()
                + "\\src\\main\\resources\\LOGO_ScoreUp.png")));
        Image imageCompany = new Image("", new FileResource(new File((new File("")).getAbsolutePath()
                + "\\src\\main\\resources\\Daimler.png")));

        horizontalLayout.addComponent(imageCompany);
        horizontalLayout.setComponentAlignment(imageCompany, Alignment.TOP_LEFT);
        horizontalLayout.addComponent(imageLogo);
        horizontalLayout.setComponentAlignment(imageLogo, Alignment.TOP_RIGHT);
        horizontalLayout.setSizeFull();
        addComponent(horizontalLayout);
    }
}
