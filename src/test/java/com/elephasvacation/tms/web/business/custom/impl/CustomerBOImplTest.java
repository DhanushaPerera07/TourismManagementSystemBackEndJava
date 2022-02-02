package com.elephasvacation.tms.web.business.custom.impl;

import com.elephasvacation.tms.web.WebAppConfig;
import com.elephasvacation.tms.web.WebRootConfig;
import com.elephasvacation.tms.web.business.custom.CustomerBO;
import com.elephasvacation.tms.web.dto.CustomerDTO;
import com.elephasvacation.tms.web.entity.enumeration.GenderTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebRootConfig.class, WebAppConfig.class})
public class CustomerBOImplTest {

    @Autowired
    private CustomerBO customerBO;

    @Test
    public void checkCustomerBO() {
        assertNotNull(customerBO);
    }

    @Test
    public void createCustomer() throws Exception {
        assertNotNull(customerBO);

        CustomerDTO customerDTO = new CustomerDTO(
                "Johne Doe",
                "UK",
                "112233445566",
                "john.doe@gmail.com",
                GenderTypes.MALE,
                "+441632960417",
                "UK",
                "None",
                "None"
        );

        Integer savedCustomer = this.customerBO.createCustomer(customerDTO);

        assertNotNull(savedCustomer);
        System.out.println("SavedCustomer: " + savedCustomer);
    }

    @Test
    public void updateCustomer() {
    }

//    @Transactional
    @Test
    public void deleteCustomer() throws Exception {
        assertNotNull(this.customerBO);

        /* delete */
        this.customerBO.deleteCustomer(3);

        CustomerDTO customerDTOFromDB = this.customerBO.getCustomerByID(10);
        assertNull(customerDTOFromDB);
    }

    @Test
    public void getCustomerByID() {
    }

    @Test
    public void getAllCustomers() {
    }
}