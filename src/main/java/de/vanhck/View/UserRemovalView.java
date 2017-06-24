package de.vanhck.View;

import com.vaadin.ui.*;
import de.vanhck.data.User;
import de.vanhck.data.UserDAO;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Jonas on 24.06.2017.
 *
 * @author Jonas Kett
 * @version 1.0
 */
public class UserRemovalView extends ClosableView {
    private UserDAO userSaver;
    private ComboBox users;
    private PopupView popupView;

    public UserRemovalView(UserDAO userSaver, Layout parentComponent, Layout toClose) {
        super(parentComponent, toClose);

        addComponent(new Label("Benutzer entfernen"));
        popupView = new PopupView(null, new Label("Kein Benutzer ausgewählt!"));

        users = new ComboBox();
        fillComboBox(userSaver.findAll());
        users.setTextInputAllowed(false);
        users.setItemCaptionGenerator(new ItemCaptionGenerator() {
            @Override
            public String apply(Object o) {
                return o.toString();
            }
        });
        addComponent(users);
        addComponent(popupView);

        Button removeUserButton = new Button("Ausgewählten Benutzer entfernen");
        removeUserButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (users.getValue() == null) {
                    popupView.setPopupVisible(true);
                } else {
                    userSaver.delete((User) users.getValue());
                    fillComboBox(userSaver.findAll());
                    users.setValue(null);
                }
            }
        });
        addComponent(removeUserButton);
    }

    private void fillComboBox(Iterable<User> users) {
        Iterable<User> users1 = users;
        Collection<User> usersCollection = new HashSet<>();
        for(User user : users1) {
            usersCollection.add(user);
        }
        this.users.setItems(usersCollection);
    }
}
