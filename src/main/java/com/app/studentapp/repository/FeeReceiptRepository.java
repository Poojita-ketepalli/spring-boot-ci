package com.app.studentapp.repository;

import com.app.studentapp.bean.FeeReceipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeeReceiptRepository extends JpaRepository<FeeReceipt, Long> {
}
