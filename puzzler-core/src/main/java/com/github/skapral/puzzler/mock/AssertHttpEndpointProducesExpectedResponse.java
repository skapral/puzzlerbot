/*-
 * ===========================================================================
 * puzzler-core
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
package com.github.skapral.puzzler.mock;

import com.pragmaticobjects.oo.tests.Assertion;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClients;
import org.assertj.core.api.Assertions;

import java.io.ByteArrayInputStream;
import java.util.function.Supplier;

/**
 * Asserts that there is available HTTP endpoint, which respondes with certain status code and body.
 *
 * @author Kapralov Sergey
 */
public class AssertHttpEndpointProducesExpectedResponse implements Assertion {
    /**
     * HTTP Request scenario.
     *
     * @author Kapralov Sergey
     */
    public interface RequestScenario extends Supplier<HttpRequestBase> {}

    private final RequestScenario requestScenario;
    private final int expectedStatus;
    private final String expectedBody;

    /**
     * Ctor.
     *
     * @param requestScenario HTTP request to make
     * @param expectedStatus Expected status
     * @param expectedBody Expected body
     */
    public AssertHttpEndpointProducesExpectedResponse(RequestScenario requestScenario, int expectedStatus, String expectedBody) {
        this.requestScenario = requestScenario;
        this.expectedStatus = expectedStatus;
        this.expectedBody = expectedBody;
    }

    @Override
    public final void check() throws Exception {
        CloseableHttpResponse response = HttpClients.createDefault().execute(requestScenario.get());
        try {
            Assertions.assertThat(response.getStatusLine().getStatusCode()).isEqualTo(expectedStatus);
            Assertions.assertThat(response.getEntity().getContent()).hasSameContentAs(new ByteArrayInputStream(expectedBody.getBytes()));
        } finally {
            response.close();
        }
    }
}
