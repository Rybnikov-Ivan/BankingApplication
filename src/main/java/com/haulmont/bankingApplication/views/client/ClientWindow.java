package com.haulmont.bankingApplication.views.client;

import com.haulmont.bankingApplication.models.Client;
import com.haulmont.bankingApplication.services.ClientService;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientWindow extends Window implements View {

    private final ClientService clientService;
    private final Client client;

    private final TextField firstname = new TextField("Name");
    private final TextField surname = new TextField("Surname");
    private final TextField patronymic = new TextField("Patronymic");
    private final TextField phoneNumber = new TextField("Phone number");
    private final TextField mail = new TextField("E-mail");
    private final TextField passportNumber = new TextField("Passport");

    private final Button save = new Button("Save");
    private final Button cancel = new Button("Cancel");

    public ClientWindow(ClientService clientService, Client client) {
        this.clientService = clientService;
        this.client = client;
        if (client.getId() == null){
            setCaption("Insert client");
        } else {
            setCaption("Update client");
        }
        setModal(true);
        center();
        setContent(createContent());
    }

    public Component createContent() {
        VerticalLayout main = new VerticalLayout();
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        HorizontalLayout rowFCS = new HorizontalLayout();
        HorizontalLayout rowOtherData = new HorizontalLayout();
        buttonsLayout.addComponents(save, cancel);
        rowFCS.addComponents(firstname, surname, patronymic);
        rowOtherData.addComponents(passportNumber, phoneNumber, mail);
        main.addComponents(rowFCS, rowOtherData, buttonsLayout);

        firstname.setRequiredIndicatorVisible(true);
        surname.setRequiredIndicatorVisible(true);
        phoneNumber.setRequiredIndicatorVisible(true);
        passportNumber.setRequiredIndicatorVisible(true);

        cancel.addClickListener(event -> getUI().removeWindow(ClientWindow.this));
        cancel.setStyleName(ValoTheme.BUTTON_DANGER);
        save.addClickListener(event -> this.save());
        save.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        ClientView.updateClientGrid(clientService);

        return main;
    }

    private void save() {
        if (validatePhone(phoneNumber.getValue()) && validatePassport(passportNumber.getValue())
        && validateFirstname(firstname.getValue()) && validateSurname(surname.getValue()) && validatePatronymic(patronymic.getValue())) {

            client.setFirstname(firstname.getValue().trim());
            client.setSurname(surname.getValue().trim());
            client.setPatronymic(patronymic.getValue().trim());
            client.setPassportNumber(Long.parseLong(passportNumber.getValue().trim()));
            client.setMail(mail.getValue().trim());
            client.setPhoneNumber(Long.parseLong(phoneNumber.getValue().trim()));
            clientService.save(client);

            getUI().removeWindow(ClientWindow.this);
            ClientView.updateClientGrid(clientService);
        } else {
            new Notification("Validation error, please check the correctness of the entered data",
                    Notification.Type.ERROR_MESSAGE).show(getUI().getPage());
        }
    }

    public boolean validatePhone (String phoneNumber) {
        Pattern pattern = Pattern.compile("\\d{10}");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.find();
    }

    public boolean validatePassport (String passport) {
        Pattern pattern = Pattern.compile("\\d{10}");
        Matcher matcher = pattern.matcher(passport);
        return matcher.find();
    }

    public boolean validateFirstname (String firstname) {
        Pattern pattern = Pattern.compile("^[a-z0-9_-]{2,15}$");
        Matcher matcher = pattern.matcher(firstname);
        return matcher.find();
    }

    public boolean validateSurname (String surname) {
        Pattern pattern = Pattern.compile("^[a-z0-9_-]{2,15}$");
        Matcher matcher = pattern.matcher(surname);
        return matcher.find();
    }

    public boolean validatePatronymic (String patronymic) {
        Pattern pattern = Pattern.compile("^[a-z0-9_-]{3,15}$");
        Matcher matcher = pattern.matcher(patronymic);
        return matcher.find();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
