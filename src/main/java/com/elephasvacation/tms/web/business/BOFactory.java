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
 * @date : 04/07/2021
 */
package com.elephasvacation.tms.web.business;

import com.elephasvacation.tms.web.business.custom.impl.*;

public class BOFactory {

    private static BOFactory boFactory = null;

    public BOFactory() {
    }

    public static BOFactory getInstance() {
        return (boFactory == null) ? (boFactory = new BOFactory()) : (boFactory);
    }

    public <T extends SuperBO> T getBO(BOTypes boTypes) {
        switch (boTypes) {
            case EMPLOYEE:
                return (T) new EmployeeBOImpl();
            case EMPLOYEE_CREDENTIAL:
                return (T) new EmployeeCredentialBOImpl();
            case CUSTOMER:
                return (T) new CustomerBOImpl();
            case TOUR_DETAIL:
                return (T) new TourDetailBOImpl();
            case ROOM_TYPE:
                return (T) new RoomTypeBOImpl();
            case ROOM_CATEGORY:
                return (T) new RoomCategoryBOImpl();
            case MEAL_PLAN:
                return (T) new MealPlanBOImpl();
            case ACCOMMODATION:
                return (T) new AccommodationBOImpl();
            case ACCOMMODATION_PACKAGE:
                return (T) new AccommodationPackageBOImpl();
            case ACCOMMODATION_RATE:
                return (T) new AccommodationRateBOImpl();
            case ACCOMMODATION_PACKAGE_MEAL_PLAN:
                return (T) new AccommodationPackageMealPlanBOImpl();
            case ACCOMMODATION_PACKAGE_ROOM_CATEGORY:
                return (T) new AccommodationPackageRoomCategoryBOImpl();
            case ACCOMMODATION_PACKAGE_ROOM_TYPE:
                return (T) new AccommodationPackageRoomTypeBOImpl();
            default:
                return null;
        }
    }
}
