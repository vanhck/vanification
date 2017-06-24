package de.vanhck.View;

import com.vaadin.ui.*;
import de.vanhck.Util;
import de.vanhck.data.User;
import de.vanhck.data.UserDAO;

/**
 * Created by Jonas on 24.06.2017.
 *
 * @author Jonas Kett
 * @version 1.0
 */
public class UserRegistrationView extends ClosableView {
    private TextField name;
    private PasswordField pw;
    private PasswordField pw2;
    private Button addUserButton;
    private PopupView p;
    private PopupView q;

    private UserDAO userSaver;

    public UserRegistrationView(UserDAO userSaver, Layout parentComponent, Layout toClose) {
        super(parentComponent, toClose);
        this.userSaver = userSaver;
        p = new PopupView(null, new Label("Benutzer erfolgreich hinzugefügt"));
        q = new PopupView(null, new Label("Fehler"));

        addComponent(new Label("Benutzer hinzufügen"));
        name = new TextField("Name");
        pw = new PasswordField("Passwort");
        pw2 = new PasswordField("Passwort wiederholen");
        addUserButton = new Button("Benutzer hinzufügen");

        addComponent(name);
        addComponent(pw);
        addComponent(pw2);
        addComponent(p);
        addComponent(q);
        addUserButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                registerUser();
            }
        });
        addComponent(addUserButton);

    }

    private void registerUser() {
        if (!name.getValue().isEmpty() && !pw.getValue().isEmpty() && !pw2.getValue().isEmpty() && pw.getValue().equals(pw2.getValue())) {
            try {
                String hash = Util.getSaltedHash(pw.getValue());
                User newUser = new User(name.getValue(), hash);
                userSaver.save(newUser);

                name.clear();
                pw.clear();
                pw2.clear();

                p.setPopupVisible(true);


            } catch (Exception e) {
                //e.printStackTrace();
                q.setPopupVisible(true);
            }
        } else {
            q.setPopupVisible(true);
        }
    }
}
