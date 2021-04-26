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
 * @author : Dhanusha Perera
 * @since : 21/04/2021
 * @since : 21/04/2021
 */
/**
 * @author : Dhanusha Perera
 * @since : 21/04/2021
 */
package com.elephasvacation.tms.web.util;

import com.elephasvacation.tms.web.commonConstant.ApplicationProperties;
import com.elephasvacation.tms.web.commonConstant.Commons;
import org.junit.Assert;
import org.junit.Test;

import java.util.Properties;

public class ApplicationPropertiesUtilTest {

    @Test
    public void getInstance() {
        Assert.assertNotNull("ApplicationPropertiesUtil getInstance method returns null, " +
                "but it should not be.", ApplicationPropertiesUtil.getInstance());
    }

    @Test
    public void loadPropertyFile() {
        Properties properties = ApplicationPropertiesUtil.getInstance().loadPropertyFile(Commons.APPLICATION_PROPERTIES_FILE_NAME);
        Assert.assertNotNull(properties);
        Assert.assertEquals("root", properties.getProperty(ApplicationProperties.DATABASE_USER_NAME));
        Assert.assertEquals("root", properties.getProperty(ApplicationProperties.DATABASE_PASSWORD));
        Assert.assertEquals("jdbc:mysql://localhost:3306/tms", properties.getProperty(ApplicationProperties.DATABASE_URL));
        Assert.assertEquals("2", properties.getProperty(ApplicationProperties.DATABASE_INITIAL_SIZE));
    }

    @Test(expected = NullPointerException.class)
    public void loadPropertyFileNegativeCase() {
        Properties properties = ApplicationPropertiesUtil.getInstance().loadPropertyFile("/unknown.properties");
    }
}
