package com.powernode.bank.dao;

import com.powernode.bank.pojo.Account;

import java.util.List;

public interface AccountDao {
    int insert(Account act);
    int deleteById(Long id);
    int update(Account act);
    Account selectByActno(String actno);
    List<Account> selectAll();
}
