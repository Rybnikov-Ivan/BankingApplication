package com.haulmont.bankingApplication.views.credit;

import com.haulmont.bankingApplication.models.Credit;
import com.haulmont.bankingApplication.services.CreditService;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CreditWindow extends Window implements View {

    private final TextField creditLimited = new TextField("Credit limit");
    private final TextField interestRate = new TextField("Interest rate");

    private final Button save = new Button("save");
    private final Button cancel = new Button("cancel");

    private final CreditService creditService;
    private final Credit credit;

    public CreditWindow(CreditService creditService, Credit credit){
        this.credit = credit;
        this.creditService = creditService;
        setCaption("Fill in the loan details");

        setModal(true);

        center();
        setContent(createContent());
    }

    public Component createContent(){
        VerticalLayout main = new VerticalLayout();
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.addComponents(save, cancel);

        HorizontalLayout textLayout = new HorizontalLayout();
        textLayout.addComponents(creditLimited, interestRate);
        main.addComponents(textLayout, buttonsLayout);

        creditLimited.setRequiredIndicatorVisible(true);
        interestRate.setRequiredIndicatorVisible(true);

        cancel.addClickListener(event -> getUI().removeWindow(CreditWindow.this));
        cancel.setStyleName(ValoTheme.BUTTON_DANGER);
        save.addClickListener(event -> this.save());
        save.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        return main;
    }

    void save(){
        if (creditService.getCredit(Long.parseLong(creditLimited.getValue()),
                Double.parseDouble(interestRate.getValue())).isEmpty() &&
        validateCreditLimited(creditLimited.getValue()) &&
        validateInterestRate(interestRate.getValue())) {
            credit.setCreditLimit(Long.parseLong(creditLimited.getValue().trim()));
            credit.setInterestRate(Double.parseDouble(interestRate.getValue().trim()));
            creditService.save(credit);

            CreditView.updateCreditGrid(creditService);
            getUI().removeWindow(CreditWindow.this);
        } else {
            new Notification("Validation error, please check the correctness of the entered data",
                    Notification.Type.ERROR_MESSAGE).show(getUI().getPage());
        }
    }

    public boolean validateCreditLimited(String creditLimited){
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(creditLimited);
        return matcher.find();
    }

    public boolean validateInterestRate(String interestRate){
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(interestRate);
        return matcher.find();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
