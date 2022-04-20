package com.hatchways.finance.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.hatchways.finance.FinanceApplication;
import com.hatchways.finance.model.Account;
import com.hatchways.finance.model.Transaction;
import com.hatchways.finance.model.User;
import com.hatchways.finance.repository.AccountRepository;
import com.hatchways.finance.repository.TransactionRepository;
import com.hatchways.finance.repository.UserRepository;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.DAYS;

public class SeedDataUtil {
  private UserRepository userRepository;

  private AccountRepository accountRepository;

  private TransactionRepository transactionRepository;

  public SeedDataUtil(
      UserRepository userRepository,
      AccountRepository accountRepository,
      TransactionRepository transactionRepository) {
    this.userRepository = userRepository;
    this.accountRepository = accountRepository;
    this.transactionRepository = transactionRepository;
  }

  public void deleteAll() {
    System.out.println("-- Deleting all entities --");
    transactionRepository.deleteAll();
    transactionRepository.flush();
    accountRepository.deleteAll();
    accountRepository.flush();
    userRepository.deleteAll();
    userRepository.flush();
  }

  public void seedFromFiles() {
    System.out.println("-- Seeding user --");
    User user = new User();
    user.setEmail("test@test.com");
    user.setPasswordDigest(User.hashPassword("sample"));
    userRepository.save(user);

    Gson gson = new Gson();

    System.out.println("-- Seeding accounts --");
    InputStream accountsStream =
        FinanceApplication.class.getClassLoader().getResourceAsStream("accounts.json");
    Reader reader = new InputStreamReader(Objects.requireNonNull(accountsStream));
    JsonReader jsonReader = gson.newJsonReader(reader);
    JsonObject object = gson.fromJson(jsonReader, JsonObject.class);

    ArrayList<Account> accounts = new ArrayList<>();
    HashMap<String, Account> accountsByPlaidId = new HashMap<>();
    for (JsonElement jsonTx : object.getAsJsonArray("accounts")) {
      JsonObject accountObject = jsonTx.getAsJsonObject();
      Account account = new Account();
      account.setUser(user);
      account.setName(accountObject.get("name").getAsString());
      account.setPlaidAccountId(accountObject.get("plaid_account_id").getAsString());
      account.setMask(accountObject.get("mask").getAsString());
      account.setOfficialName(accountObject.get("official_name").getAsString());
      account.setCurrentBalanceStr(accountObject.get("current_balance").getAsString());
      account.setIsoCurrencyCode(accountObject.get("iso_currency_code").getAsString());
      account.setUnofficialCurrencyCode(
          accountObject.get("unofficial_currency_code").isJsonNull()
              ? null
              : accountObject.get("unofficial_currency_code").getAsString());
      account.setAccountSubtype(accountObject.get("account_subtype").getAsString());
      account.setAccountType(accountObject.get("account_type").getAsString());

      accounts.add(account);
      accountsByPlaidId.put(account.getPlaidAccountId(), account);
    }
    accountRepository.saveAllAndFlush(accounts);

    System.out.println("-- Seeding transactions --");
    InputStream transactionsStream =
        FinanceApplication.class.getClassLoader().getResourceAsStream("transactions.json");
    reader = new InputStreamReader(Objects.requireNonNull(transactionsStream));
    jsonReader = gson.newJsonReader(reader);
    object = gson.fromJson(jsonReader, JsonObject.class);

    JsonArray jsonTransactions = object.getAsJsonArray("transactions");

    DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDate latestDate =
        LocalDate.parse(
            jsonTransactions.get(0).getAsJsonObject().get("date").getAsString(), dtFormatter);
    for (JsonElement jsonTx : jsonTransactions) {
      JsonObject txObject = jsonTx.getAsJsonObject();
      LocalDate txDate = LocalDate.parse(txObject.get("date").getAsString(), dtFormatter);
      if (txDate.isAfter(latestDate)) {
        latestDate = txDate;
      }
    }

    LocalDate today = LocalDate.now();
    long dayOffset = Duration.between(latestDate.atStartOfDay(), today.atStartOfDay()).toDays() - 1;

    ArrayList<Transaction> transactions = new ArrayList<>();
    for (JsonElement jsonTx : jsonTransactions) {
      JsonObject txObject = jsonTx.getAsJsonObject();
      Account account = accountsByPlaidId.get(txObject.get("plaid_account_id").getAsString());
      if (account == null) {
        continue;
      }

      LocalDateTime txDate =
          LocalDateTime.parse(txObject.get("date").getAsString(), dtFormatter).plusDays(dayOffset);

      Transaction transaction = new Transaction();
      transaction.setAccount(account);
      transaction.setUser(user);
      transaction.setPlaidTransactionId(txObject.get("plaid_transaction_id").getAsString());
      transaction.setPlaidCategoryId(txObject.get("plaid_category_id").getAsString());
      transaction.setCategories(txObject.get("categories").getAsString());
      transaction.setType(txObject.get("type").getAsString());
      transaction.setName(txObject.get("name").getAsString());
      transaction.setAmountStr(txObject.get("amount").getAsString());
      transaction.setIsoCurrencyCode(txObject.get("iso_currency_code").getAsString());
      transaction.setUnofficialCurrencyCode(txObject.get("unofficial_currency_code").getAsString());
      transaction.setPending(txObject.get("pending").getAsBoolean());
      transaction.setDate(txDate);

      transactions.add(transaction);
    }
    transactionRepository.saveAllAndFlush(transactions);
    System.out.println("-- Seeding complete --");
  }
}
