/*-
 * ===========================================================================
 * puzzler-github
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Copyright (C) 2019 Kapralov Sergey
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * ============================================================================
 */
package com.github.skapral.puzzler.github.operation;

import java.io.InputStream;

import com.github.skapral.puzzler.core.operation.AssertOperationFails;
import com.github.skapral.puzzler.core.operation.AssertOperationSuccessful;
import com.github.skapral.puzzler.core.operation.OpFail;
import com.pragmaticobjects.oo.tests.TestCase;
import com.pragmaticobjects.oo.tests.junit5.TestsSuite;
import org.apache.commons.io.IOUtils;

/**
 * Tests suite for {@link OpIgnoringUnprivildgedEventSender}
 *
 * @author Kapralov Sergey
 */
class OpIgnoringUnprivildgedEventSenderTest extends TestsSuite {
    private static final String OWNER_IS_SENDER;
    private static final String OWNER_IS_NOT_SENDER;

    static {
        try (InputStream ownerIsSenderStream = OpValidatingGithubEventSignature.class.getResource("ownerIsSender").openStream();
             InputStream ownerIsNotSenderStream = OpValidatingGithubEventSignature.class.getResource("ownerIsNotSender").openStream()) {
            OWNER_IS_SENDER = IOUtils.toString(ownerIsSenderStream, "UTF-8");
            OWNER_IS_NOT_SENDER = IOUtils.toString(ownerIsNotSenderStream, "UTF-8");
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Ctor.
     */
    public OpIgnoringUnprivildgedEventSenderTest() {
        super(
            new TestCase(
                "operation passes control to delegate if event sender and repository owner are a one person",
                new AssertOperationFails(
                    new OpIgnoringUnprivildgedEventSender(
                        OWNER_IS_SENDER,
                        new OpFail()
                    )
                )
            ),
            new TestCase(
                "operation suppresses delegate if event sender and repository owner are different persons",
                new AssertOperationSuccessful(
                    new OpIgnoringUnprivildgedEventSender(
                        OWNER_IS_NOT_SENDER,
                        new OpFail()
                    )
                )
            )
        );
    }
}
