package com.elephasvacation.tms.web.business.custom.util;

import com.elephasvacation.tms.web.entity.Customer;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/*
@author : Dhanusha Perera
@date : 05/07/2021
*/public class EntityDTOMapperTest {

    @Test
    public void getCustomerDTO() {
        EntityDTOMapper mapper = EntityDTOMapper.instance;
        Customer customer = new Customer();
        customer.setId(115);

        Assert.assertEquals(115, mapper.getCustomerDTO(customer).getId());
    }
}
