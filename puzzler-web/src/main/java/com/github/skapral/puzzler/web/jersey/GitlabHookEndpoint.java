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

import com.github.skapral.puzzler.core.operation.OpPersistAllPuzzles;
import com.github.skapral.puzzler.gitlab.config.Cp_GITLAB_AUTH_TOKEN;
import com.github.skapral.puzzler.gitlab.config.Cp_GITLAB_HOOK_SECRET;
import com.github.skapral.puzzler.gitlab.itracker.ItGitlabIssues;
import com.github.skapral.puzzler.gitlab.location.GlapiProduction;
import com.github.skapral.puzzler.gitlab.operation.OpValidatingGitlabEventToken;
import com.github.skapral.puzzler.gitlab.project.GprjFromGitlabEvent;
import com.github.skapral.puzzler.gitlab.source.PsrcFromGitlabEvent;
import com.pragmaticobjects.oo.atom.anno.NotAtom;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * The HTTP endpoint for Gitlab hook
 *
 * @author Kapralov Sergey
 */
@NotAtom
@Path("gitlab")
public class GitlabHookEndpoint {
    /**
     * Gitlab hook endpoint
     * @param eventType Event type, obtained from X-Gitlab-Event header.
     * @param eventToken Event token from X-Gitlab-Token header.
     * @param event Event body in JSON format
     * @return HTTP response.
     * @throws Exception If something went wrong.
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public final Response githubHook(@HeaderParam("X-Gitlab-Event") final String eventType, @HeaderParam("X-Gitlab-Token") final String eventToken, final String event) throws Exception {
        new OpValidatingGitlabEventToken(
            new Cp_GITLAB_HOOK_SECRET(),
            eventToken,
            new OpPersistAllPuzzles(
                new PsrcFromGitlabEvent(
                    new GlapiProduction(
                        new Cp_GITLAB_AUTH_TOKEN()
                    ),
                    eventType,
                    event
                ),
                new ItGitlabIssues(
                    new GlapiProduction(
                        new Cp_GITLAB_AUTH_TOKEN()
                    ),
                    new GprjFromGitlabEvent(
                        event
                    )
                )
            ),
            AuthenticationExceptionMapper.AuthenticationException::new
        ).execute();
        return Response.ok("{}").build();
    }
}
