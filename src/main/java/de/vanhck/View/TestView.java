package de.vanhck.View;

import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import de.vanhck.restservice.FileSender;

/**
 * Created by Jonas on 23.06.2017.
 *
 * @author Jonas Kett
 * @version 1.0
 */
public class TestView extends ClosableView {

    private Button closeButton;

    public TestView(Layout parentLayout, Layout toClose) {
        super(parentLayout, toClose);

        //VerticalLayout layout = new VerticalLayout();
        addComponent(new Label("Test Site"));
        Button button = new Button("send file");
        button.addClickListener((Button.ClickListener) clickEvent -> {

            FileSender.sendFile();
            //System.out.println(new File("").getAbsolutePath());
        });
        addComponent(button);
    }

}
