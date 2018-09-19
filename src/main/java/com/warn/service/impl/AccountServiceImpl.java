package com.warn.service.impl;

import com.warn.dao.AccountDao;
import com.warn.entity.Account;
import com.warn.dto.PageHelper;
import com.warn.entity.Role;
import com.warn.entity.User;
import com.warn.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by netlab606 on 2017/5/22.
 */
@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    AccountDao accountDao;

    @Override
    public Long getDatagridTotal() {
        return accountDao.getDatagridTotal();
    }

    @Override
    public List<Account> datagridAccount(PageHelper page) {
        page.setStart((page.getPage() - 1) * page.getRows());
        return accountDao.datagridAccount(page);
    }

    @Transactional
    @Override
    public void addAccount(Account account) {
        User user=new User();
        user.setUsername(account.getUsername());
        user.setPassword(account.getPassword());
        accountDao.addUser(user);
        Role role=new Role();
        role.setId(accountDao.getRoleIdByName(account.getName()));
        accountDao.addUser_Role(user.getId(),role.getId());
    }

    @Transactional
    @Override
    public void editAccount(Account account) {
        User user=new User();
        user.setId(account.getId());
        user.setUsername(account.getUsername());
        user.setPassword(account.getPassword());
        accountDao.editUserByUid(user);
        Role role=new Role();
        role.setId(accountDao.getRoleIdByName(account.getName()));
        accountDao.editUser_Role(user.getId(),role.getId());
    }

    @Transactional
    @Override
    public void deleteAccount(Integer id) {
        accountDao.deleteUser_RoleByUid(id);
        accountDao.deleteUserByUid(id);
    }
}
