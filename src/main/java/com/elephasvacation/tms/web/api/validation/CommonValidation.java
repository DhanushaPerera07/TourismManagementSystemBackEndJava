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
 * @date : 09/07/2021
 */
package com.elephasvacation.tms.web.api.validation;

import com.elephasvacation.tms.web.commonConstant.MimeTypes;
import com.elephasvacation.tms.web.commonConstant.ValidationMessages;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;

public class CommonValidation {

    public static boolean isContentTypeNotJSON(HttpServletRequest request) {
        return (request.getContentType() == null || !request.getContentType().equals(MimeTypes.Application.JSON));
    }

    public static void sendBadRequest(HttpServletResponse response, Logger logger) throws IOException {
        String errorMessage = MessageFormat.format(ValidationMessages.REQUEST_CONTENT_TYPE_INVALID,
                MimeTypes.Application.JSON);
        logger.info(errorMessage);
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, errorMessage);
    }

}
