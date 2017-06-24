package de.vanhck.View;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by Jonas on 23.06.2017.
 *
 * @author Jonas Kett
 * @version 1.0
 */
public class MainView extends VerticalLayout {
    private HorizontalLayout horizontalLayout;

    public MainView() {
        horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponent(new Label("FIRMEN_NAME"));
        horizontalLayout.addComponent(new Label(""));
        Label daimler = new Label("Daimler");

        horizontalLayout.addComponent(daimler);
        horizontalLayout.setSizeFull();
        addComponent(horizontalLayout);
    }
}
