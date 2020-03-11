package com.account.bank.repository;

import com.account.bank.Model.entity.LimitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LimitRepository extends JpaRepository<LimitEntity, Long> {
}
