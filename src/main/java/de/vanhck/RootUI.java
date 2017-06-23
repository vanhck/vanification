package de.vanhck;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import de.vanhck.restservice.FileSender;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@SpringUI
public class RootUI extends UI {
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(new Label("Test Site"));
        Button button = new Button("send file");
        button.addClickListener((Button.ClickListener) clickEvent -> {

            FileSender.sendFile();
            //System.out.println(new File("").getAbsolutePath());
        });
        layout.addComponent(button);

        setContent(layout);
    }
}
