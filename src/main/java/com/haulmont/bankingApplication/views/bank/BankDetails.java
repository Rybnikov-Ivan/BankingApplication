package com.haulmont.bankingApplication.views.bank;

import com.haulmont.bankingApplication.models.CreditOffer;
import com.haulmont.bankingApplication.services.CreditOfferService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = "BankDetails")
public class BankDetails extends VerticalLayout implements View {
    @Autowired
    CreditOfferService creditOfferService;

    @PostConstruct
    protected void init() {
        Grid<CreditOffer> grid = new Grid<>(CreditOffer.class);
        grid.setColumns("client", "credit", "creditAmount", "paymentSchedule");
        grid.setItems(creditOfferService.findOffersForClient(BankView.bank_id));
        grid.setSizeFull();
        addComponent(grid);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
