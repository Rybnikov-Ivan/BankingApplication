package com.haulmont.bankingApplication.repositories;

import com.haulmont.bankingApplication.models.PaymentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentScheduleRepository extends JpaRepository<PaymentSchedule, UUID> {
}
