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
package com.elephasvacation.tms.web.dal;

import com.elephasvacation.tms.web.dal.custom.impl.*;
import org.springframework.beans.factory.annotation.Autowired;

public class DAOFactory {

    private static DAOFactory daoFactory = null;

    @Autowired
    private CustomerDAOImpl customerDAOImpl;

    @Autowired
    private TourDetailDAOImpl tourDetailDAOImpl;

    @Autowired
    private AccommodationDAOImpl accommodationDAOImpl;

    @Autowired
    private AccommodationPackageDAOImpl accommodationPackageDAOImpl;

    @Autowired
    private AccommodationRateDAOImpl accommodationRateDAOImpl;

    @Autowired
    private EmployeeDAOImpl employeeDAOImpl;

    @Autowired
    private EmployeeCredentialDAOImpl employeeCredentialDAOImpl;

    @Autowired
    private RoomTypeDAOImpl roomTypeDAOImpl;

    @Autowired
    private RoomCategoryDAOImpl roomCategoryDAOImpl;

    @Autowired
    private MealPlanDAOImpl mealPlanDAOImpl;

    @Autowired
    private AccommodationPackageMealPlanDAOImpl accommodationPackageMealPlanDAOImpl;

    @Autowired
    private AccommodationPackageRoomCategoryDAOImpl accommodationPackageRoomCategoryDAOImpl;

    @Autowired
    private AccommodationPackageRoomTypeDAOImpl accommodationPackageRoomTypeDAOImpl;

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return (daoFactory == null) ? (daoFactory = new DAOFactory()) : daoFactory;
    }

    public <T extends SuperDAO> T getDAO(DAOTypes daoTypes) {
        switch (daoTypes) {
            case CUSTOMER:
                return (T) this.customerDAOImpl;
            case TOUR_DETAIL:
                return (T) this.tourDetailDAOImpl;
            case ACCOMMODATION:
                return (T) this.accommodationDAOImpl;
            case ACCOMMODATION_PACKAGE:
                return (T) this.accommodationPackageDAOImpl;
            case ACCOMMODATION_RATE:
                return (T) this.accommodationRateDAOImpl;
            case EMPLOYEE:
                return (T) this.employeeDAOImpl;
            case EMPLOYEE_CREDENTIAL:
                return (T) this.employeeCredentialDAOImpl;
            case ROOM_TYPE:
                return (T) this.roomTypeDAOImpl;
            case ROOM_CATEGORY:
                return (T) this.roomCategoryDAOImpl;
            case MEAL_PLAN:
                return (T) this.mealPlanDAOImpl;
            case MEAL_PLAN_FOR_ACCOMMODATION_PACKAGE:
                return (T) this.accommodationPackageMealPlanDAOImpl;
            case ROOM_CATEGORY_FOR_ACCOMMODATION_PACKAGE:
                return (T) this.accommodationPackageRoomCategoryDAOImpl;
            case ROOM_TYPE_FOR_ACCOMMODATION_PACKAGE:
                return (T) this.accommodationPackageRoomTypeDAOImpl;
            default:
                return null;
        }
    }

}
