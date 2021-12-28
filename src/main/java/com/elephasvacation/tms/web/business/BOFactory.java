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
import org.springframework.beans.factory.annotation.Autowired;

public class BOFactory {

    private static BOFactory boFactory = null;

    @Autowired
    private EmployeeBOImpl employeeBOImpl;

    @Autowired
    private EmployeeCredentialBOImpl employeeCredentialBOImpl;

    @Autowired
    private CustomerBOImpl customerBOImpl;

    @Autowired
    private TourDetailBOImpl tourDetailBOImpl;

    @Autowired
    private RoomTypeBOImpl roomTypeBOImpl;

    @Autowired
    private RoomCategoryBOImpl roomCategoryBOImpl;

    @Autowired
    private MealPlanBOImpl mealPlanBOImpl;

    @Autowired
    private AccommodationBOImpl accommodationBOImpl;

    @Autowired
    private AccommodationPackageBOImpl accommodationPackageBOImpl;

    @Autowired
    private AccommodationRateBOImpl accommodationRateBOImpl;

    @Autowired
    private AccommodationPackageMealPlanBOImpl accommodationPackageMealPlanBOImpl;

    @Autowired
    private AccommodationPackageRoomCategoryBOImpl accommodationPackageRoomCategoryBOImpl;

    @Autowired
    private AccommodationPackageRoomTypeBOImpl accommodationPackageRoomTypeBOImpl;

    public BOFactory() {
    }

    public static BOFactory getInstance() {
        return (boFactory == null) ? (boFactory = new BOFactory()) : (boFactory);
    }

    public <T extends SuperBO> T getBO(BOTypes boTypes) {
        switch (boTypes) {
            case EMPLOYEE:
                return (T) this.employeeBOImpl;
            case EMPLOYEE_CREDENTIAL:
                return (T) this.employeeCredentialBOImpl;
            case CUSTOMER:
                return (T) this.customerBOImpl;
            case TOUR_DETAIL:
                return (T) this.tourDetailBOImpl;
            case ROOM_TYPE:
                return (T) this.roomTypeBOImpl;
            case ROOM_CATEGORY:
                return (T) this.roomCategoryBOImpl;
            case MEAL_PLAN:
                return (T) this.mealPlanBOImpl;
            case ACCOMMODATION:
                return (T) this.accommodationBOImpl;
            case ACCOMMODATION_PACKAGE:
                return (T) this.accommodationPackageBOImpl;
            case ACCOMMODATION_RATE:
                return (T) this.accommodationRateBOImpl;
            case ACCOMMODATION_PACKAGE_MEAL_PLAN:
                return (T) this.accommodationPackageMealPlanBOImpl;
            case ACCOMMODATION_PACKAGE_ROOM_CATEGORY:
                return (T) this.accommodationPackageRoomCategoryBOImpl;
            case ACCOMMODATION_PACKAGE_ROOM_TYPE:
                return (T) this.accommodationPackageRoomTypeBOImpl;
            default:
                return null;
        }
    }
}
