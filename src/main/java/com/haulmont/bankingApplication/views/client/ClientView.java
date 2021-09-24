package com.haulmont.bankingApplication.views.client;

import com.haulmont.bankingApplication.models.Client;
import com.haulmont.bankingApplication.services.ClientService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = "clients")
public class ClientView extends VerticalLayout implements View {
    @Autowired
    ClientService clientService;

    public static Grid<Client> clientGrid = new Grid<>(Client.class);

    @PostConstruct
    void init(){
        Button addButton = new Button("Insert");
        Button editButton = new Button("Update");
        Button deleteButton = new Button("Delete");

        addButton.setIcon(VaadinIcons.INSERT);
        editButton.setIcon(VaadinIcons.REFRESH);
        deleteButton.setIcon(VaadinIcons.FOLDER_REMOVE);
        deleteButton.setStyleName(ValoTheme.BUTTON_DANGER);

        Page.getCurrent().setTitle("Clients");

        deleteButton.setEnabled(false);
        editButton.setEnabled(false);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.addComponents(addButton, editButton, deleteButton);

        addComponent(buttonsLayout);

        clientGrid.setSizeFull();
        clientGrid.setColumns("firstname", "surname", "patronymic", "phoneNumber", "passportNumber","mail");
        clientGrid.setItems(clientService.findAll());

        addComponent(clientGrid);

        clientGrid.addSelectionListener(valueChangeEvent -> {
            if (!clientGrid.asSingleSelect().isEmpty()) {
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);
            } else {
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }
        });

        addButton.addClickListener(e -> {
            Client client = new Client();
            ClientWindow clientWindow = new ClientWindow(clientService, client);
            getUI().addWindow(clientWindow);
        });

        editButton.addClickListener(e -> {
            Client client = clientGrid.asSingleSelect().getValue();
            ClientWindow clientWindow = new ClientWindow(clientService, client);
            getUI().addWindow(clientWindow);
        });

        deleteButton.addClickListener(e -> {
            Client client = clientGrid.asSingleSelect().getValue();
            try {
                clientService.remove(client);
                updateClientGrid(clientService);
                Notification notification = new Notification(client.getFirstname() + " successfully deleted",
                        Notification.Type.WARNING_MESSAGE);
                notification.setDelayMsec(1500);
                notification.show(getUI().getPage());
            } catch (Exception deleteException) {
                Notification notification = new Notification(" Failed to delete the client",
                        Notification.Type.WARNING_MESSAGE);
                notification.show(getUI().getPage());
            }
        });
    }

    static void updateClientGrid(ClientService clientService) {
        clientGrid.setItems(clientService.findAll());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
