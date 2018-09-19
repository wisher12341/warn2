package com.warn.service;

import com.warn.entity.Account;
import com.warn.dto.PageHelper;

import java.util.List;

/**
 * Created by netlab606 on 2017/5/22.
 */
public interface AccountService {
    Long getDatagridTotal();

    List<Account> datagridAccount(PageHelper page);

    void addAccount(Account account);

    void editAccount(Account account);

    void deleteAccount(Integer id);
}
