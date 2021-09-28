package com.haulmont.bankingApplication.services;

import com.haulmont.bankingApplication.models.PaymentSchedule;
import com.haulmont.bankingApplication.repositories.PaymentScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentScheduleService {
    @Autowired
    PaymentScheduleRepository paymentScheduleRepository;

    public PaymentScheduleService() {}

    public List<PaymentSchedule> getAll(){
        return paymentScheduleRepository.findAll();
    }

    public void deleteById(Long id){
        paymentScheduleRepository.deleteById(id);
    }

    public void save(PaymentSchedule paymentSchedule){
        paymentScheduleRepository.save(paymentSchedule);
    }
}
