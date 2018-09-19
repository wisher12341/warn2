package com.warn.service;

import com.warn.dto.AuthorityDTO;
import com.warn.dto.PageHelper;
import com.warn.entity.Account;

import java.util.List;

/**
 * Created by netlab606 on 2017/5/22.
 */
public interface AuthorityService {


    Long getDatagridTotal();

    List<AuthorityDTO> datagridAuthority();

    void editAuthority(AuthorityDTO[] authorityDTOS);

    void recoverAuthority();
}
