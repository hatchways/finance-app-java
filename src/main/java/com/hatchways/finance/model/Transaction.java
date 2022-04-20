package com.hatchways.finance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "transactions",
    uniqueConstraints = @UniqueConstraint(columnNames = {"plaid_transaction_id"}))
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_sequence")
  @SequenceGenerator(name = "transaction_sequence", sequenceName = "transaction_sequence")
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id")
  @JsonIgnore
  private Account account;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @JsonIgnore
  private User user;

  @Column(name = "plaid_transaction_id")
  private String plaidTransactionId;

  @Column(name = "plaid_category_id")
  private String plaidCategoryId;

  private String categories; // comma delimited

  private String type;

  private String name;

  @Column(name = "amount_str")
  private String amountStr;

  @Column(name = "iso_currency_code")
  private String isoCurrencyCode;

  @Column(name = "unofficial_currency_code")
  private String unofficialCurrencyCode;

  private LocalDateTime date;

  private Boolean pending;

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

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getPlaidTransactionId() {
    return plaidTransactionId;
  }

  public void setPlaidTransactionId(String plaidTransactionId) {
    this.plaidTransactionId = plaidTransactionId;
  }

  public String getPlaidCategoryId() {
    return plaidCategoryId;
  }

  public void setPlaidCategoryId(String plaidCategoryId) {
    this.plaidCategoryId = plaidCategoryId;
  }

  public String getCategories() {
    return categories;
  }

  public void setCategories(String categories) {
    this.categories = categories;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAmountStr() {
    return amountStr;
  }

  public void setAmountStr(String amountStr) {
    this.amountStr = amountStr;
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

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public Boolean getPending() {
    return pending;
  }

  public void setPending(Boolean pending) {
    this.pending = pending;
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

  public BigDecimal getAmount() {
    return new BigDecimal(this.amountStr);
  }
}
