package com.haulmont.bankingApplication.views.creditOffer;

import com.haulmont.bankingApplication.models.*;
import com.haulmont.bankingApplication.services.BankService;
import com.haulmont.bankingApplication.services.CreditOfferService;
import com.haulmont.bankingApplication.services.CreditService;
import com.haulmont.bankingApplication.services.PaymentScheduleService;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;

public class CreditOfferWindow extends Window implements View {

    private final Client client;
    private Credit credit;
    private final Long creditAmount;
    private final Integer creditPeriod;

    private final CreditService creditService;
    private final CreditOfferService creditOfferService;
    private final PaymentScheduleService paymentScheduleService;
    private final BankService bankService;

    private NativeSelect<Credit> creditSelect;

    private final VerticalLayout windowLayout = new VerticalLayout();
    private final HorizontalLayout buttonsLayout = new HorizontalLayout();
    private final HorizontalLayout header = new HorizontalLayout();

    private final Label creditLabel = new Label("Select an offer :");

    private final Button selectButton = new Button("Select");
    private final Button backButton = new Button("Back");

    public CreditOfferWindow(CreditService creditService, Long creditAmount, Integer creditPeriod,
                             Client client, CreditOfferService creditOfferService,
                             PaymentScheduleService paymentScheduleService, BankService bankService) {
        this.creditService = creditService;
        this.creditAmount = creditAmount;
        this.creditPeriod = creditPeriod;
        this.client = client;
        this.creditOfferService = creditOfferService;
        this.paymentScheduleService = paymentScheduleService;
        this.bankService = bankService;

        setCaption("Our Offers");
        center();
        setContent(createContent());
    }

    public Component createContent(){
        selectButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        backButton.setStyleName(ValoTheme.BUTTON_DANGER);
        buttonsLayout.addComponents(selectButton, backButton);

        if (creditService.findCreditByAmount(creditAmount).size() == 0) {
            windowLayout.addComponents(new Label("No suitable offers were found"));
        } else {
            List<Credit> credits = creditService.findCreditByAmount(creditAmount);
            creditSelect = new NativeSelect<>("Available loans: ", credits);
            windowLayout.addComponents(creditLabel, creditSelect, buttonsLayout);

            selectButton.addClickListener(event -> this.acceptance(creditSelect.getValue(), client, creditAmount, creditPeriod));
            selectButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
            selectButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
            backButton.addClickListener(event -> getUI().removeWindow(CreditOfferWindow.this));
        }
        return windowLayout;
    }

    private void acceptance(Credit credit, Client client, Long creditAmount, int creditPeriod){
        this.credit = credit;
        setCaption("Data acceptance");
        windowLayout.removeAllComponents();
        center();

        header.setWidth("100%");
        Label checkInfo = new Label("Check the entered data");
        checkInfo.addStyleName(ValoTheme.LABEL_SUCCESS);
        header.addComponent(checkInfo);
        header.setComponentAlignment(checkInfo, Alignment.MIDDLE_CENTER);

        Button accept = new Button("Accept");
        Button cancel = new Button("Cancel");
        accept.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        cancel.addStyleName(ValoTheme.BUTTON_DANGER);

        HorizontalLayout buttons = new HorizontalLayout(accept, cancel);
        double firstPayment = (creditAmount * 0.7 / (creditPeriod * 12)) +
                ((creditAmount * 0.7 * (credit.getInterestRate() / 100)) / (creditPeriod * 12));
        DecimalFormat df = new DecimalFormat("#.##");
        windowLayout.addComponents(header,
                new Label(client.toString()),
                new Label("\nLoan amount: " + creditAmount + " rubles' "),
                new Label("\nLoan term: " + creditPeriod + " years"),
                new Label("\nInterest rate: " + credit.getInterestRate() + "% "),
                new Label("\nFirst payment: " +
                        df.format(creditAmount * 0.2) + " rubles' "),
                new Label("\nPayment for the first month: " +
                        df.format(firstPayment) + " rubles' "),
                new Label("\nMonthly payment: " +
                        df.format(creditAmount * 0.8 / (creditPeriod * 12))),
                buttons
        );
        accept.addClickListener(clickEvent -> this.saveCredit());
        cancel.addClickListener(clickEvent -> getUI().removeWindow(CreditOfferWindow.this));
        windowLayout.setComponentAlignment(buttons, Alignment.MIDDLE_CENTER);
    }

    private void saveCredit(){
        Bank bank = new Bank(client, credit);
        bankService.save(bank);

        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.valueOf(localDateTime.toLocalDate());
        double scale = Math.pow(10, 2);

        double remains = creditAmount * 0.8;
        double percent = credit.getInterestRate();
        int period = creditPeriod * 12;
        double paymentBody = Math.ceil(remains / period * scale) / scale;

        for (int i = 0; i < creditPeriod * 12; i++) {
            double paymentPercent = Math.ceil(((remains * (percent / 100)) / period) * scale) / scale;
            double paymentPerMonth = Math.ceil((paymentBody + paymentPercent) * scale) / scale;
            if (paymentPercent < 0) paymentPercent = 0;
            PaymentSchedule schedule = new PaymentSchedule(date, paymentPerMonth, paymentBody, paymentPercent);
            remains -= paymentPerMonth;
            localDateTime = localDateTime.plusMonths(1);
            date = Date.valueOf(localDateTime.toLocalDate());
            paymentScheduleService.save(schedule);
            CreditOffer creditOffer = new CreditOffer(client, credit, schedule, creditAmount, bank.getId());
            creditOfferService.save(creditOffer);
        }
        Notification success = new Notification("The loan is issued:)",
                Notification.Type.HUMANIZED_MESSAGE);
        success.setDelayMsec(1500);
        success.show(getUI().getPage());
        getUI().removeWindow(CreditOfferWindow.this);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
