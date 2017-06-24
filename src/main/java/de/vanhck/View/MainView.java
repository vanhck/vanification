package de.vanhck.View;

import com.vaadin.ui.Alignment;
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
    Label company;

    public MainView() {
        horizontalLayout = new HorizontalLayout();
        company = new Label("FIRMEN_NAME");
        horizontalLayout.addComponent(company);

        Label daimler = new Label("Daimler");

        horizontalLayout.addComponent(daimler);
        horizontalLayout.setComponentAlignment(daimler, Alignment.TOP_RIGHT);
        horizontalLayout.setSizeFull();
        addComponent(horizontalLayout);
    }

    public void setCompanyName(String newName) {
        company.setValue(newName);
    }
}
