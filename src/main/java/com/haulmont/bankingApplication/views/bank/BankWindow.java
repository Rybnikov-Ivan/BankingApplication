package com.haulmont.bankingApplication.views.bank;

import com.haulmont.bankingApplication.models.Bank;
import com.haulmont.bankingApplication.models.Client;
import com.haulmont.bankingApplication.models.Credit;
import com.haulmont.bankingApplication.services.BankService;
import com.haulmont.bankingApplication.services.ClientService;
import com.haulmont.bankingApplication.services.CreditService;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

public class BankWindow extends Window implements View {
    private final ClientService clientService;
    private final CreditService creditService;
    private final BankService bankService;

    private final Button saveButton = new Button("Save");
    private final Button cancelButton = new Button("Cancel");

    private final Bank bank;

    private NativeSelect<Client> clientSelect;
    private NativeSelect<Credit> creditSelect;

    private final VerticalLayout mainLayout = new VerticalLayout();

    public BankWindow(BankService bankService, Bank bank, ClientService clientService, CreditService creditService){
        this.bankService = bankService;
        this.bank = bank;
        this.clientService = clientService;
        this.creditService = creditService;
        setCaption("Add a new loan");

        setModal(true);
        setContent(createContent());
    }

    private Component createContent(){
        List<Client> clients = clientService.findAll();
        List<Credit> credits = creditService.findAll();

        clientSelect = new NativeSelect<>(" Выберите Клиента", clients);
        clientSelect.setEmptySelectionAllowed(false);

        creditSelect = new NativeSelect<>(" Кредитная Ставка", credits);
        creditSelect.setEmptySelectionAllowed(false);

        HorizontalLayout buttonsLayout = new HorizontalLayout(saveButton, cancelButton);

        mainLayout.addComponents(clientSelect, creditSelect, buttonsLayout);

        saveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        saveButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        saveButton.addClickListener(event -> this.save());

        cancelButton.addClickListener(event -> getUI().removeWindow(BankWindow.this));

        return mainLayout;
    }

    private void save(){
        try {
            bank.setClient(clientSelect.getValue());
            bank.setBankCredit(creditSelect.getValue());
            bankService.save(bank);

            Notification notification = new Notification("Successfully",
                    Notification.Type.HUMANIZED_MESSAGE);
            notification.setDelayMsec(1500);
            notification.show(getUI().getPage());
            getUI().removeWindow(BankWindow.this);

        } catch (Exception e) {
            new Notification("Error",
                    Notification.Type.ERROR_MESSAGE).show(getUI().getPage());
        }
        BankView.updateBankGrid(bankService);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
