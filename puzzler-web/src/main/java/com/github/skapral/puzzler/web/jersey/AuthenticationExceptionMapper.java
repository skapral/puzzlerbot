/*
 * MIT License
 *
 * Copyright (c) 2018 Kapralov Sergey
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
 *
 */

package com.github.skapral.puzzler.web.jersey;

import oo.atom.anno.NotAtom;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static com.github.skapral.puzzler.web.jersey.AuthenticationExceptionMapper.AuthenticationException;

/**
 * Exception mapper for {@link AuthenticationException}.
 *
 * @author Kapralov Sergey
 */
@NotAtom
@Provider
public class AuthenticationExceptionMapper implements ExceptionMapper<AuthenticationException> {
    /**
     * Exception for issues related to unauthorized access to application.
     *
     * @author Kapralov Sergey
     */
    @NotAtom
    public static final class AuthenticationException extends RuntimeException {
        /**
         * Ctor.
         * @param message Exception message.
         */
        public AuthenticationException(String message) {
            super(message);
        }
    }

    @Override
    public final Response toResponse(AuthenticationException exception) {
        exception.printStackTrace();
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
