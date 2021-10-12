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
package com.elephasvacation.tms.web.api.util;

import com.elephasvacation.tms.web.commonConstant.Commons;
import com.elephasvacation.tms.web.exception.HttpResponseException;

public class IDUtil {

    /**
     * Convert the URI to lowercase, remove forward slashes
     * and split path segments into String array.
     *
     * @param getPathInfoString : request.getPathInfo() string.
     * @return String[] : split array with path segment.
     */
    public static String[] getSplitArray(String getPathInfoString) {
        return getPathInfoString.toLowerCase().trim().split("/");
    }


    /**
     * Extract the integer ID.
     *
     * @param splitArray       : split array consisting URI path segments.
     * @param index:           which index of the array that consider for the extraction.
     * @param replacingString: character or string which you want to replaced with empty string.
     * @param invalidMessage:  in case if extraction failed, error message.
     */
    public static Integer extractIDFrom(String[] splitArray, int index, String replacingString, String invalidMessage) {
        /* extracting customer ID from URL. */
        try {
            return Integer.parseInt(splitArray[index].replace(replacingString, Commons.EMPTY_STRING));
        } catch (NumberFormatException numberFormatException) {
            numberFormatException.printStackTrace();
            throw new HttpResponseException(400, invalidMessage, numberFormatException);
        }
    }
}
