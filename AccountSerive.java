package com.cashsystem.service;

import com.cashsystem.dao.AccountDao;
import com.cashsystem.entity.Account;

public class AccountSerive {
    private AccountDao accountDao;

    public AccountSerive(){
        this.accountDao = new AccountDao();
    }

    public Account login(String username,String password){
        return this.accountDao.login(username,password);
    }
    public boolean register(Account account){
        return this.accountDao.register(account);
    }



}
