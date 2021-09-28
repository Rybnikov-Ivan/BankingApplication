package com.haulmont.bankingApplication.views.bank;

import com.haulmont.bankingApplication.models.Bank;
import com.haulmont.bankingApplication.models.CreditOffer;
import com.haulmont.bankingApplication.services.*;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringView(name = "bank")
public class BankView extends VerticalLayout implements View {

    @Autowired
    PaymentScheduleService paymentScheduleService;
    @Autowired
    BankService bankService;
    @Autowired
    ClientService clientService;
    @Autowired
    CreditService creditService;
    @Autowired
    CreditOfferService creditOfferService;

    public static Grid<Bank> bankGrid = new Grid<>(Bank.class);
    public static UUID bank_id;
    private final Button addButton = new Button("Insert");
    private final Button deleteButton = new Button("Delete");
    private final Button detailsButton = new Button("Details");

    private final HorizontalLayout buttonsLayout = new HorizontalLayout();

    @PostConstruct
    void init(){
        Page.getCurrent().setTitle("Bank");
        addButton.setIcon(VaadinIcons.INSERT);
        detailsButton.setEnabled(false);
        detailsButton.setIcon(VaadinIcons.PAPERPLANE);
        deleteButton.setEnabled(false);
        deleteButton.setStyleName(ValoTheme.BUTTON_DANGER);
        deleteButton.setIcon(VaadinIcons.FOLDER_REMOVE);

        buttonsLayout.addComponents(addButton, detailsButton, deleteButton);
        addComponent(buttonsLayout);

        bankGrid.setSizeFull();
        bankGrid.setColumns("client", "bankCredit");
        bankGrid.setItems(bankService.findAll());
        bankGrid.addSelectionListener(valueChangeEvent -> {
            if (!bankGrid.asSingleSelect().isEmpty()) {
                deleteButton.setEnabled(true);
                detailsButton.setEnabled(true);
            } else {
                deleteButton.setEnabled(false);
                detailsButton.setEnabled(false);
            }
        });
        addComponent(bankGrid);

        addButton.addClickListener(e -> {
            Bank bank = new Bank();
            BankWindow bankWindow = new BankWindow(bankService, bank, clientService, creditService);
            getUI().addWindow(bankWindow);
        });

        deleteButton.addClickListener(e -> {
            Bank bank = bankGrid.asSingleSelect().getValue();
            bank_id = bankGrid.asSingleSelect().getValue().getId();
            try {
                List<CreditOffer> creditOffers = creditOfferService.findOffersForClient(BankView.bank_id);
                List<UUID> idsOfPaymentSchedules = new ArrayList<>();

                for (CreditOffer creditOffer : creditOffers)
                    idsOfPaymentSchedules.add(creditOffer.getPaymentSchedule().getId());

                creditOfferService.deleteAllOffersForClient(BankView.bank_id);

                for (UUID l : idsOfPaymentSchedules)
                    paymentScheduleService.deleteById(l);

                bankService.delete(bank);
                updateBankGrid(bankService);
                Notification notification = new Notification(bank.toString() + " был успешно удален",
                        Notification.Type.WARNING_MESSAGE);
                notification.setDelayMsec(1500);
                notification.setPosition(Position.BOTTOM_CENTER);
                notification.show(getUI().getPage());
            } catch (Exception deleteException) {
                Notification notification = new Notification("Ошибка! Попробуйте еще раз позже",
                        Notification.Type.WARNING_MESSAGE);
                notification.show(getUI().getPage());
                deleteException.printStackTrace();
            }
        });

        detailsButton.addClickListener(e -> {
            bank_id = bankGrid.asSingleSelect().getValue().getId();
            getUI().getNavigator().navigateTo("BankPayments");
        });

        updateBankGrid(bankService);
    }

    static void updateBankGrid(BankService bankService) {
        bankGrid.setItems(bankService.findAll());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
