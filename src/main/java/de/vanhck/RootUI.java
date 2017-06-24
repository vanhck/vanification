package de.vanhck;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import de.vanhck.View.*;
import de.vanhck.data.*;
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
    @Autowired
    private OptionDAO optionSaver;
    @Autowired
    private ScoreDAO scoreDao;
    @Autowired
    private DrivingKeyValueDAO drivingKeyValueDao;
    @Autowired
    private DrivingResultDAO drivingResultDao;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        mainView = new MainView();
        layout = new VerticalLayout();
        /*
        Button navigateToTestViewButton = new Button("goto test page");
        navigateToTestViewButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                (new TestView(mainView, layout)).show();
            }
        });
        layout.addComponent(navigateToTestViewButton);
        */

        Button navigateToLoginViewButton = new Button("Login");
        navigateToLoginViewButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                (new LoginView(userSaver, mainView, layout)).show();
            }
        });
        layout.addComponent(navigateToLoginViewButton);

        Button navigateToUserRegistrationViewButton = new Button("Benutzer hinzufügen");
        navigateToUserRegistrationViewButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                (new UserRegistrationView(userSaver, mainView,layout)).show();
            }
        });
        layout.addComponent(navigateToUserRegistrationViewButton);

        Button navigateToAdminViewButton = new Button("Admin Seite");
        navigateToAdminViewButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                (new AdminView(optionSaver, userSaver, mainView,layout,drivingKeyValueDao, drivingResultDao, scoreDao)).show();
            }
        });
        layout.addComponent(navigateToAdminViewButton);

        Button navigateToRemoveUserViewButton = new Button("Benutzer entfernen");
        navigateToRemoveUserViewButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                (new UserRemovalView(userSaver,mainView, layout)).show();
            }
        });
        layout.addComponent(navigateToRemoveUserViewButton);


        mainView.addComponent(layout);
        setContent(mainView);
    }

}
