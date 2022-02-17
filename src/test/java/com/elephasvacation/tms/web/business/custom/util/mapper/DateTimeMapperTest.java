/*
 * MIT License
 *
 * Copyright (c) 2022 Dhanusha Perera
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.elephasvacation.tms.web.business.custom.util.mapper;

import com.elephasvacation.tms.web.WebAppConfig;
import com.elephasvacation.tms.web.WebRootConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertNotNull;

@RunWith(value = SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebRootConfig.class, WebAppConfig.class})
public class DateTimeMapperTest {

    @Autowired
    private DateTimeMapper mapper;

    @Test
    public void asString() {
        ZoneId zoneId = ZoneId.of("Europe/Paris");
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
        assertNotNull(zonedDateTime);
        System.out.println(zonedDateTime);
        assertNotNull(zonedDateTime.toString());
    }

//    @Test
//    public void asZonedDateTime() {
//        String dateAsString = "2007-12-03T10:15:30+01:00[Europe/Paris]";
//        ZonedDateTime zonedDateTime = this.mapper.asZonedDateTime(dateAsString);
//        assertNotNull(zonedDateTime);
//        assertEquals(zonedDateTime.toString(), dateAsString);
//        System.out.println(zonedDateTime);
//    }

//    @Test(expected = DateTimeInvalidException.class)
//    public void asZonedDateTimeError() {
////        String dateAsString = null;
//        ZonedDateTime zonedDateTime = this.mapper.asZonedDateTime(null);
//        System.out.println(zonedDateTime);
//    }

}