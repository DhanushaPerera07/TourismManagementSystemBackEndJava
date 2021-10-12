/*
 * MIT License
 *
 * Copyright (c) 2021 Dhanusha Perera
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
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
/*
 * @author : Dhanusha Perera
 * @date : 12/10/2021
 */
package com.elephasvacation.tms.web.util;

import com.elephasvacation.tms.web.commonConstant.HibernateConstant;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.Properties;

/* JPA Bootstrapping and build EntityManagerFactory */
public class HibernateUtil {
    private static EntityManagerFactory entityManagerFactory = buildEntityManagerFactory();

    /** Expose the EntityManagerFactory instance.
    * @return EntityManagerFactory */
    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    /** Build the EntityManagerFactory.
     * @return EntityManagerFactory instance */
    private static EntityManagerFactory buildEntityManagerFactory() {
        EntityManagerFactory entityManagerFactory = null;

        Properties properties = new Properties();
        try {
            properties.load(HibernateUtil.class.getResourceAsStream(HibernateConstant.APPLICATION_PROPERTIES_FILE_NAME));

            entityManagerFactory = Persistence
                    .createEntityManagerFactory(HibernateConstant.PERSISTENCE_UNIT_NAME, properties);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return entityManagerFactory;
    }
}
