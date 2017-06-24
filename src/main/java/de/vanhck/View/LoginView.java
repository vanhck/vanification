package de.vanhck.View;

import com.vaadin.ui.*;
import de.vanhck.Util;
import de.vanhck.data.User;
import de.vanhck.data.UserDAO;

/**
 * Created by Jonas on 23.06.2017.
 *
 * @author Jonas Kett
 * @version 1.0
 */
public class LoginView extends ClosableView {
    private TextField nameTextField;
    private PasswordField pwTextField;
    private UserDAO userSaver;

    private PopupView error;


    public LoginView(UserDAO userSaver, Layout parentLayout, Layout toClose) {
        super(parentLayout, toClose);

        this.userSaver = userSaver;
        error = new PopupView(null, new Label("Falscher Benutzername oder Passwort"));
        Label signUp = new Label("Anmeldung");
        addComponent(signUp);

        nameTextField = new TextField();
        nameTextField.setCaption("Name");
        nameTextField.setDescription("Username");

        addComponent(nameTextField);

        pwTextField = new PasswordField();
        pwTextField.setCaption("Passwort");
        pwTextField.setDescription("Password");

        addComponent(pwTextField);
        addComponent(error);

        addComponent(new Label(""));

        Button loginButton = new Button("login");
        loginButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                login();
            }
        });
        addComponent(loginButton);
    }

    private void login() {
        //TODO
        String name = nameTextField.getValue();
        String pw = pwTextField.getValue();
        //TODO pw hashen



        if (name.isEmpty() || pw.isEmpty()) {
            error.setPopupVisible(true);
        } else {
            User tmp = userSaver.findByName(name);
            if (tmp != null) {
                try {
                    boolean correctPw = Util.check(pw, tmp.getPwHash());
                    if (correctPw) {
                        (new UserView(userSaver, tmp, super.getParentComponent(), this)).show();
                    } else {
                        error.setPopupVisible(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    error.setPopupVisible(true);
                }
            } else {
                error.setPopupVisible(true);
            }
        }
    }
}
