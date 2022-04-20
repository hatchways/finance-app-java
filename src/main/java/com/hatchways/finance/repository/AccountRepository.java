package com.hatchways.finance.repository;

import com.hatchways.finance.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

  @Query("SELECT a from Account as a where a.user.id = :userId")
  List<Account> findByUserId(Integer userId);
}
