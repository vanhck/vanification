package de.vanhck;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import de.vanhck.View.LoginView;
import de.vanhck.View.MainView;
import de.vanhck.View.TestView;
import de.vanhck.View.UserRegistrationView;
import de.vanhck.data.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;

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
    private MainView mainView;
    private VerticalLayout layout;
    @Autowired
    private UserDAO userSaver;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        mainView = new MainView();
        layout = new VerticalLayout();




        layout.addComponent(new Label("Main Site"));

        Button navigateToTestViewButton = new Button("goto test page");
        navigateToTestViewButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                (new TestView(mainView, layout)).show();
            }
        });
        layout.addComponent(navigateToTestViewButton);

        Button navigateToLoginViewButton = new Button("goto login");
        navigateToLoginViewButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                (new LoginView(userSaver, mainView, layout)).show();
            }
        });
        layout.addComponent(navigateToLoginViewButton);

        Button navigateToUserRegistrationViewButton = new Button("goto add user");
        navigateToUserRegistrationViewButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                (new UserRegistrationView(userSaver, mainView,layout)).show();
            }
        });
        layout.addComponent(navigateToUserRegistrationViewButton);

        mainView.addComponent(layout);
        setContent(mainView);
    }

}
