package io.coin.ccbc.api.application;

import io.coin.ccbc.domain.Account;
import io.coin.ccbc.domain.AccountRepository;
import io.coin.ccbc.domain.Address;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountApplicationService {

  private final AccountRepository accountRepository;

  public AccountApplicationService(
      final AccountRepository accountRepository
  ) {
    this.accountRepository = accountRepository;
  }

  @Transactional
  public void create(String value) {
    Address address = Address.of(value);
    if (!this.accountRepository.existsByAddress(address)) {
      Account account = Account.from(address);
      this.accountRepository.save(account);
    }
  }
}
