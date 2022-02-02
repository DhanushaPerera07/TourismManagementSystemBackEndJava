package com.elephasvacation.tms.web.business.custom.util.mapper;

import com.elephasvacation.tms.web.WebAppConfig;
import com.elephasvacation.tms.web.WebRootConfig;
import com.elephasvacation.tms.web.dto.CustomerDTO;
import com.elephasvacation.tms.web.entity.Customer;
import com.elephasvacation.tms.web.entity.enumeration.GenderTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebRootConfig.class, WebAppConfig.class})
public class CustomerDTOMapperTest {

    @Autowired
    private CustomerDTOMapper mapper;

    @Test
    public void checkCustomerDTOMapper() {
        assertNotNull(mapper);
    }

    @Test
    public void getCustomer() {
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
        System.out.println("CustomerDTO: " + customerDTO);

        Customer customer = this.mapper.getCustomer(customerDTO);
        assertNotNull(customer);
        System.out.println("Customer Entity: " + customer);

    }

    @Test
    public void getCustomerDTO() {
    }
}