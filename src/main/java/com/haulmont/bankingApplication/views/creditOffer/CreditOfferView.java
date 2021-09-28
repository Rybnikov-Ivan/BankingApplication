package com.haulmont.bankingApplication.views.creditOffer;

import com.haulmont.bankingApplication.models.Client;
import com.haulmont.bankingApplication.services.*;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringView(name = "creditOffer")
public class CreditOfferView extends VerticalLayout implements View {

    @Autowired
    ClientService clientService;
    @Autowired
    CreditService creditService;
    @Autowired
    PaymentScheduleService scheduleService;
    @Autowired
    CreditOfferService creditOfferService;
    @Autowired
    BankService bankService;

    private final Button makeCredit = new Button("Select offers");

    private final VerticalLayout mainLayout = new VerticalLayout();
    private final HorizontalLayout limit = new HorizontalLayout();
    private final HorizontalLayout period = new HorizontalLayout();
    private final VerticalLayout buttonAndSelect = new VerticalLayout();

    static NativeSelect<Client> clientSelect;

    private final TextField creditAmount = new TextField("Enter the amount: ");
    private final TextField creditPeriod = new TextField("Enter the period: ");

    @PostConstruct
    void init(){
        Page.getCurrent().setTitle("CreditOffer");

        clientSelect = new NativeSelect<>("Select a client", clientService.findAll());
        clientSelect.setRequiredIndicatorVisible(true);

        limit.addComponent(creditAmount);
        period.addComponent(creditPeriod);
        creditAmount.setRequiredIndicatorVisible(true);
        creditAmount.setPlaceholder("Rubles");

        creditPeriod.setRequiredIndicatorVisible(true);
        creditPeriod.setPlaceholder("Years");

        makeCredit.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        makeCredit.addClickListener(clickEvent -> {
            try {
                CreditOfferWindow creditOfferForm = new CreditOfferWindow(creditService,
                        Long.parseLong(creditAmount.getValue()), Integer.parseInt(creditPeriod.getValue()),
                        clientSelect.getValue(), creditOfferService, scheduleService, bankService);
                getUI().addWindow(creditOfferForm);
            } catch (Exception e) {
                Notification error = new Notification("Check the entered data");
                error.setDelayMsec(1500);
                error.show(getUI().getPage());
            }
        });

        buttonAndSelect.addComponents(clientSelect, limit, period, makeCredit);
        buttonAndSelect.setComponentAlignment(makeCredit, Alignment.BOTTOM_LEFT);

        mainLayout.addComponents(buttonAndSelect);
        addComponents( mainLayout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
