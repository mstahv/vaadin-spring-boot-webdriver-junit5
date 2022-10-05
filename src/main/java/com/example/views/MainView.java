package com.example.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main view is a top-level placeholder for other views.
 */
@Route
public class MainView extends VerticalLayout {

    public MainView() {
        TextField tf = new TextField("Your name here:");
        Paragraph p = new Paragraph();
        Button button = new Button("Click me",
                event -> {
                    try {
                        // simulate backend latency
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    p.setText("Hello " + tf.getValue());
                });

        p.setId("message");

        add(tf, button, p);
    }
}
