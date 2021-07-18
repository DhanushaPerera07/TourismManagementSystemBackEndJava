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
 * @date : 05/07/2021
 */
package com.elephasvacation.tms.web.api;

import com.elephasvacation.tms.web.api.accommodation.AccommodationAPIImpl;
import com.elephasvacation.tms.web.api.customer.customerAPI.CustomerAPIImpl;
import com.elephasvacation.tms.web.api.customer.tourDetailsAPI.TourDetailsAPIImpl;
import com.elephasvacation.tms.web.api.employee.EmployeeAPIImpl;
import com.elephasvacation.tms.web.api.mealPlan.MealPlanAPIImpl;
import com.elephasvacation.tms.web.api.roomCategory.RoomCategoryAPIImpl;
import com.elephasvacation.tms.web.api.roomType.RoomTypeAPIImpl;

public class APIFactory {
    private static APIFactory apiFactory = null;

    private APIFactory() {
    }

    public static APIFactory getInstance() {
        return (apiFactory == null) ? apiFactory = new APIFactory() : apiFactory;
    }

    public <T extends SuperAPI> T getAPI(APITypes apiTypes) {
        switch (apiTypes) {
            case CUSTOMER:
                return (T) new CustomerAPIImpl();
            case TOUR_DETAIL:
                return (T) new TourDetailsAPIImpl();
            case EMPLOYEE:
                return (T) new EmployeeAPIImpl();
            case ACCOMMODATION:
                return (T) new AccommodationAPIImpl();
            case ROOM_TYPE:
                return (T) new RoomTypeAPIImpl();
            case ROOM_CATEGORY:
                return (T) new RoomCategoryAPIImpl();
            case MEAL_PLAN:
                return (T) new MealPlanAPIImpl();
            default:
                return null;
        }
    }
}
