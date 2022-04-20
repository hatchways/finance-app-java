package com.hatchways.finance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(
    name = "accounts",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"plaid_account_id"})})
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_sequence")
  @SequenceGenerator(name = "account_sequence", sequenceName = "account_sequence")
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @JsonIgnore
  private User user;

  @Column(name = "plaid_account_id")
  private String plaidAccountId;

  private String name;

  private String mask;

  @Column(name = "official_name")
  private String officialName;

  @Column(name = "current_balance_str")
  private String currentBalanceStr;

  @Column(name = "iso_currency_code")
  private String isoCurrencyCode;

  @Column(name = "unofficial_currency_code")
  private String unofficialCurrencyCode;

  @Column(name = "account_type")
  private String accountType;

  @Column(name = "account_subtype")
  private String accountSubtype;

  @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
  @JsonIgnore
  private Set<Transaction> transactions;

  @CreatedDate
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getPlaidAccountId() {
    return plaidAccountId;
  }

  public void setPlaidAccountId(String plaidAccountId) {
    this.plaidAccountId = plaidAccountId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMask() {
    return mask;
  }

  public void setMask(String mask) {
    this.mask = mask;
  }

  public String getOfficialName() {
    return officialName;
  }

  public void setOfficialName(String officialName) {
    this.officialName = officialName;
  }

  public String getCurrentBalanceStr() {
    return currentBalanceStr;
  }

  public void setCurrentBalanceStr(String currentBalanceStr) {
    this.currentBalanceStr = currentBalanceStr;
  }

  public String getIsoCurrencyCode() {
    return isoCurrencyCode;
  }

  public void setIsoCurrencyCode(String isoCurrencyCode) {
    this.isoCurrencyCode = isoCurrencyCode;
  }

  public String getUnofficialCurrencyCode() {
    return unofficialCurrencyCode;
  }

  public void setUnofficialCurrencyCode(String unofficialCurrencyCode) {
    this.unofficialCurrencyCode = unofficialCurrencyCode;
  }

  public String getAccountType() {
    return accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public String getAccountSubtype() {
    return accountSubtype;
  }

  public void setAccountSubtype(String accountSubtype) {
    this.accountSubtype = accountSubtype;
  }

  public Set<Transaction> getTransactions() {
    return transactions;
  }

  public void setTransactions(Set<Transaction> transactions) {
    this.transactions = transactions;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public BigDecimal getCurrentBalance() {
    return new BigDecimal(this.currentBalanceStr);
  }
}
