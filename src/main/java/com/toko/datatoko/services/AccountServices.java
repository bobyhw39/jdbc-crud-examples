package com.toko.datatoko.services;

import com.toko.datatoko.repository.AccountRepository;

import javax.annotation.Resource;

public class AccountServices {

    AccountRepository accountRepository;

    public AccountServices(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
}
