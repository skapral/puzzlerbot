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

package com.github.skapral.puzzler.github.source;

import com.github.skapral.puzzler.config.ConfigProperty;
import com.github.skapral.puzzler.core.PuzzleSource;
import com.github.skapral.puzzler.core.source.PsrcInferred;
import com.github.skapral.puzzler.core.source.PsrcStatic;
import io.vavr.control.Option;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

/**
 * Puzzles source, which parses puzzles from arbitrary Github event.
 *
 * @author Kapralov Sergey
 */
public class PsrcFromGithubEvent extends PsrcInferred {
    /**
     * Ctor.
     *
     * @param eventType Github event's type (usually passed via X-GitHub-Event header).
     * @param eventBody Github event's body in JSON format.
     * @param authToken Github API authentication token
     */
    public PsrcFromGithubEvent(String eventType, String eventBody, ConfigProperty authToken) {
        super(
            new Inference(
                eventType,
                eventBody,
                authToken
            )
        );
    }

    /**
     * The {@link PsrcFromGithubEvent} inference.
     *
     * @author Kapralov Sergey
     * @todo #27 refactor this class by splitting it to smaller composable components
     */
    private static class Inference implements PuzzleSource.Inference {
        private final String eventType;
        private final String eventBody;
        private final ConfigProperty authToken;

        /**
         * Ctor.
         *
         * @param eventType Github event's type (usually passed via X-GitHub-Event header).
         * @param eventBody Github event's body in JSON format.
         * @param authToken Github API authentication token
         */
        public Inference(String eventType, String eventBody, ConfigProperty authToken) {
            this.eventType = eventType;
            this.eventBody = eventBody;
            this.authToken = authToken;
        }

        @Override
        public final PuzzleSource puzzleSource() {
            final Option<JSONObject> jsonObject;
            switch(eventType) {
                case "issues": {
                    jsonObject = Option.of(new JSONObject(eventBody))
                        .filter(jo -> "closed".equals(jo.getString("action")))
                        .map(jo -> jo.getJSONObject("issue"));
                    break;
                }
                case "pull_request": {
                    jsonObject = Option.of(new JSONObject(eventBody)).map(jo -> jo.getJSONObject("pull_request"));
                    break;
                }
                default: {
                    jsonObject = Option.none();
                }
            }
            return jsonObject
                .<PuzzleSource>map(obj -> {
                    final String commentsUrl = obj.getString("comments_url");
                    final HttpGet request = new HttpGet(commentsUrl);
                    authToken.optionalValue().peek(_authToken ->
                        request.addHeader(
                            "Authorization",
                            _authToken
                        )
                    );
                    try {
                        final CloseableHttpResponse response = HttpClients.createDefault().execute(request);
                        if(response.getStatusLine().getStatusCode() > 299) {
                            throw new RuntimeException();
                        }
                        return new PsrcFromGithubComments(
                            IOUtils.toString(response.getEntity().getContent(), "UTF-8")
                        );
                    } catch(Exception ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .getOrElse(() -> new PsrcStatic());
        }
    }
}
