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
 */
/**
 * @author : Dhanusha Perera
 * @date : 03/07/2021
 */
package com.elephasvacation.tms.web.exception;

import com.elephasvacation.tms.web.dto.ErrorDTO;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseExceptionUtil {
    static final Logger logger = LoggerFactory.getLogger(ResponseExceptionUtil.class);

    public static void  handle(Throwable t, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        Jsonb jsonb = JsonbBuilder.create();
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Internal server error");
        errorDTO.setMessage(t.getMessage());

        if (t instanceof HttpResponseException) {
            HttpResponseException ex = (HttpResponseException) t;
            errorDTO.setStatus(ex.getStatusCode());
            response.setStatus(ex.getStatusCode());
            switch (ex.getStatusCode()) {
                case 400:
                    errorDTO.setError("Bad request");
                    errorDTO.setMessage(ex.getMessage());
                    break;
                case 404:
                    errorDTO.setError("Not found");
                    errorDTO.setMessage(ex.getMessage());
                    break;
                case 500:
                    logger.error(ex.getMessage(), ex);
                    break;
                default:
                    // We are good here :)
            }
        } else {
            response.setStatus(500);
            logger.error("Something went wrong", t);
        }
        response.getWriter().println(jsonb.toJson(errorDTO));

    }
}
