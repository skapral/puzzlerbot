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

package com.github.skapral.puzzler.github.mock;

import oo.atom.anno.NotAtom;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import java.util.Objects;
import java.util.function.Function;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;

/**
 * Github mock server backed by mockserver.org framework.
 *
 * @author Kapralov Sergey
 */
public class GmsImplementation implements GithubMockServer {
    /**
     * Request building function.
     * @author Kapralov Sergey
     */
    public interface RequestFunction extends Function<HttpRequest, HttpRequest> {}
    /**
     * Response building function.
     * @author Kapralov Sergey
     */
    public interface ResponseFunction extends Function<HttpResponse, HttpResponse> {}

    private final MockServerContext ctx;
    private final int port;
    private final RequestFunction reqFn;
    private final ResponseFunction respFn;

    /**
     * Ctor.
     *
     * @param ctx Contrext for holding {@link ClientAndServer} instance.
     * @param port Port.
     * @param reqFn Request builder.
     * @param respFn Response builder.
     */
    private GmsImplementation(MockServerContext ctx, int port, RequestFunction reqFn, ResponseFunction respFn) {
        this.ctx = ctx;
        this.port = port;
        this.reqFn = reqFn;
        this.respFn = respFn;
    }

    /**
     * Ctor.
     *
     * @param port Port.
     * @param reqFn Request builder.
     * @param respFn Response builder.
     */
    public GmsImplementation(int port, RequestFunction reqFn, ResponseFunction respFn) {
        this(
            new MockServerContext(port),
            port,
            reqFn,
            respFn
        );
    }

    @Override
    public final void bootstrap() {
        ctx.start();
        MockServerClient server = new MockServerClient("localhost", port);
        server.when(
            reqFn.apply(HttpRequest.request())
        ).respond(
            respFn.apply(HttpResponse.response())
        );
    }

    @Override
    public final void destroy() {
        ctx.stop();
    }

    /**
     * Context for keeping {@link ClientAndServer} instance.
     *
     * @author Kapralov Sergey
     */
    @NotAtom
    private static final class MockServerContext {
        private final int port;
        private ClientAndServer clientAndServer;

        /**
         * Ctor.
         *
         * @param port Port.
         */
        public MockServerContext(int port) {
            this.port = port;
        }

        /**
         * Start {@link ClientAndServer}
         */
        public void start() {
            clientAndServer = startClientAndServer(port);
        }

        /**
         * Stop {@link ClientAndServer}
         */
        public void stop() {
            Objects.requireNonNull(clientAndServer);
            clientAndServer.stop();
            clientAndServer = null;
        }
    }
}
