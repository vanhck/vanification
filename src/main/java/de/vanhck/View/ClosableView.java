package de.vanhck.View;

import com.vaadin.ui.Button;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by Jonas on 23.06.2017.
 *
 * @author Jonas Kett
 * @version 1.0
 */
public abstract class ClosableView extends VerticalLayout {
    private Layout parentComponent;

    public Layout getParentComponent() {
        return parentComponent;
    }

    public Layout getToClose() {
        return toClose;
    }

    private Layout toClose;
    private Button backButton;

    public ClosableView(Layout parentComponent, Layout toClose) {
        this.parentComponent = parentComponent;
        this.toClose = toClose;
        backButton = new Button("zurück");
        backButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });
        addComponent(backButton);
    }

    public void close() {
        parentComponent.removeComponent(this);
        parentComponent.addComponent(toClose);
    }

    public void show() {
        parentComponent.addComponent(this);
        parentComponent.removeComponent(toClose);
    }
}
