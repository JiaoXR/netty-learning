package com.jaxer.dubbo.provider.service.emp;

import com.jaxer.dubbo.api.service.EmployeeService;

/**
 * Created on 2020/6/27 11:17
 *
 * @author jaxer
 */
public class EmployeeServiceImpl implements EmployeeService {
    @Override
    public String getName(Integer id) {
        return "Jack";
    }
}
