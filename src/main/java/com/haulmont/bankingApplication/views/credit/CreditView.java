package com.haulmont.bankingApplication.views.credit;

import com.haulmont.bankingApplication.models.Credit;
import com.haulmont.bankingApplication.services.CreditService;
import com.haulmont.bankingApplication.views.MyUI;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = "credits")
public class CreditView extends VerticalLayout implements View {

    @Autowired
    CreditService creditService;

    private final Button addButton = new Button("Insert");
    private final Button editButton = new Button("Update");
    private final Button deleteButton = new Button("Delete");

    private final HorizontalLayout buttonsLayout = new HorizontalLayout();
    private final HorizontalLayout headerLayout = new HorizontalLayout();

    public static Grid<Credit> creditGrid = new Grid<>(Credit.class);

    private final Label header = new Label("All credits");

    @PostConstruct
    void init(){
        Page.getCurrent().setTitle("Credits");
        MyUI.setStyleNavigationButton(3);

        header.addStyleName(ValoTheme.LABEL_HUGE);
        headerLayout.setWidth("100%");
        headerLayout.addComponent(header);
        headerLayout.setComponentAlignment(header, Alignment.TOP_CENTER);
        headerLayout.addStyleName(ValoTheme.LAYOUT_CARD);
        addComponent(headerLayout);

        addButton.setIcon(VaadinIcons.INSERT);
        editButton.setIcon(VaadinIcons.REFRESH);
        editButton.setEnabled(false);
        deleteButton.setIcon(VaadinIcons.FOLDER_REMOVE);
        deleteButton.setStyleName(ValoTheme.BUTTON_DANGER);
        deleteButton.setEnabled(false);

        buttonsLayout.addComponents(addButton, editButton, deleteButton);
        addComponent(buttonsLayout);

        creditGrid.setSizeFull();
        creditGrid.setColumns("creditLimit","interestRate");
        creditGrid.setItems(creditService.findAll());
        creditGrid.addSelectionListener(valueChangeEvent -> {
            if (!creditGrid.asSingleSelect().isEmpty()) {
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);
            } else {
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }
        });
        addComponent(creditGrid);

        addButton.addClickListener(e -> {
            Credit credit = new Credit();
            CreditWindow creditWindow = new CreditWindow(creditService, credit);
            getUI().addWindow(creditWindow);
        });

        editButton.addClickListener(e -> {
            Credit credit = creditGrid.asSingleSelect().getValue();
            CreditWindow creditWindow = new CreditWindow(creditService, credit);
            getUI().addWindow(creditWindow);
        });

        deleteButton.addClickListener(e -> {
            Credit credit = creditGrid.asSingleSelect().getValue();
            try {
                creditService.delete(credit);
                updateCreditGrid(creditService);
                Notification notification = new Notification("Successfully deleted",
                        Notification.Type.WARNING_MESSAGE);
                notification.setDelayMsec(1500);
                notification.show(getUI().getPage());
            } catch (Exception deleteException) {
                Notification notification = new Notification(" Failed to delete the credit",
                        Notification.Type.WARNING_MESSAGE);
                notification.show(getUI().getPage());
            }
        });

    }

    static void updateCreditGrid(CreditService creditService) {
        creditGrid.setItems(creditService.findAll());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
