package com.haulmont.bankingApplication.views;

import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.Serializable;

@Theme("valo")
@SpringUI
@SpringViewDisplay
public class MyUI extends UI implements ViewDisplay, Serializable {

    final VerticalLayout root = new VerticalLayout();
    final HorizontalLayout navigationBar = new HorizontalLayout();
    public static HorizontalLayout buttonsLayout;
    private Panel springViewDisplay;

    @Override
    protected void init(VaadinRequest request) {

        buttonsLayout = new HorizontalLayout();
        buttonsLayout.addComponents(
                createNavigationButton("Home", ""),
                createNavigationButton("Clients", "clients"),
                createNavigationButton("Bank", "bank"),
                createNavigationButton("Credit", "credits"),
                createNavigationButton("Credit Registration", "creditOffer")
        );

        buttonsLayout.getComponent(0).setIcon(VaadinIcons.HOME);
        buttonsLayout.getComponent(1).setIcon(VaadinIcons.USERS);
        buttonsLayout.getComponent(2).setIcon(VaadinIcons.PIGGY_BANK_COIN);
        buttonsLayout.getComponent(3).setIcon(VaadinIcons.CREDIT_CARD);
        buttonsLayout.getComponent(4).setIcon(VaadinIcons.MONEY_DEPOSIT);

        navigationBar.setWidth("100%");
        navigationBar.addComponents(buttonsLayout);

        springViewDisplay = new Panel();
        springViewDisplay.setSizeFull();

        root.setSizeFull();
        setContent(root);
        root.addComponents(navigationBar);
        root.addComponent(springViewDisplay);
        root.setExpandRatio(springViewDisplay, 1.0f);
    }

    private Button createNavigationButton(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_LARGE);
        button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
        return button;
    }

    public static void setStyleNavigationButton(int index){
        int countOfButtons = buttonsLayout.getComponentCount();
        for(int i=0; i<countOfButtons; i++)
            buttonsLayout.getComponent(i).removeStyleName(ValoTheme.BUTTON_FRIENDLY);
        buttonsLayout.getComponent(index).addStyleName(ValoTheme.BUTTON_FRIENDLY);
    }
    @Override
    public void showView(View view) {
        springViewDisplay.setContent((Component) view);
    }
}