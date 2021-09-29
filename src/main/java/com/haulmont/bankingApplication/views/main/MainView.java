package com.haulmont.bankingApplication.views.main;

import com.haulmont.bankingApplication.views.MyUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.Serializable;

@SpringView(name = "")
public class MainView extends VerticalLayout implements View, Serializable {

    private final FileResource bankFile = new FileResource(new File("src/main/resources/images/bank.jpg"));

    private final VerticalLayout verticalLayout = new VerticalLayout();
    private final HorizontalLayout horizontalLayout = new HorizontalLayout();
    private final HorizontalLayout headerLayout = new HorizontalLayout();

    @PostConstruct
    void init() {
        Page.getCurrent().setTitle("Home");
        MyUI.setStyleNavigationButton(0);

        Link linkDev = new Link("About the developer",
                new ExternalResource("https://vk.com/rrrybnikov"));
        linkDev.setTargetName("_blank");

        headerLayout.addComponent(linkDev);
        headerLayout.setComponentAlignment(linkDev, Alignment.MIDDLE_CENTER);

        Image bank = new Image("", bankFile);
        bank.setWidth("30%");
        bank.setHeight("30%");

        verticalLayout.addComponent(bank);
        verticalLayout.setComponentAlignment(bank, Alignment.TOP_CENTER);

        horizontalLayout.setWidth("100%");
        horizontalLayout.addComponent(verticalLayout);

        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponents(headerLayout, horizontalLayout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}