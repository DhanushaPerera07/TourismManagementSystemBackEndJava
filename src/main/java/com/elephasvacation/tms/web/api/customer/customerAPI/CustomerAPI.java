/**
 * MIT License
 * <p>
 * Copyright (c) 2021 Dhanusha Perera
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * @author : Dhanusha Perera
 * @date : 03/07/2021
 * @author : Dhanusha Perera
 * @date : 03/07/2021
 * @author : Dhanusha Perera
 * @date : 03/07/2021
 * @author : Dhanusha Perera
 * @date : 03/07/2021
 * @author : Dhanusha Perera
 * @date : 03/07/2021
 */
/**
 * @author : Dhanusha Perera
 * @date : 03/07/2021
 */
package com.elephasvacation.tms.web.api.customer.customerAPI;

import com.elephasvacation.tms.web.api.SuperAPI;
import com.elephasvacation.tms.web.dto.CustomerDTO;

import java.util.List;

public interface CustomerAPI extends SuperAPI {

    Integer createCustomer(CustomerDTO customerDTO) throws Exception;

    boolean updateCustomer(CustomerDTO customerDTO) throws Exception;

    boolean deleteCustomer(int customerID) throws Exception;

    CustomerDTO getCustomerByID(int customerID) throws Exception;

    List<CustomerDTO> getAllCustomers() throws Exception;
}
