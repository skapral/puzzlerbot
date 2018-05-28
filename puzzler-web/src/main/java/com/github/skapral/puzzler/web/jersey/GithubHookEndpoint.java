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

import com.github.skapral.puzzler.config.Cp_GITHUB_AUTH_TOKEN;
import com.github.skapral.puzzler.config.Cp_GITHUB_HOOK_SECRET;
import com.github.skapral.puzzler.core.operation.OpPersistAllPuzzles;
import com.github.skapral.puzzler.github.itracker.ItGithubIssues;
import com.github.skapral.puzzler.github.location.GhapiProduction;
import com.github.skapral.puzzler.github.operation.OpValidatingEventSignature;
import com.github.skapral.puzzler.github.project.GprjFromGithubEvent;
import com.github.skapral.puzzler.github.source.PsrcFromGithubEvent;
import oo.atom.anno.NotAtom;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Objects;

import static com.github.skapral.puzzler.web.jersey.AuthenticationExceptionMapper.AuthenticationException;


/**
 * The HTTP endpoint for Github hook
 *
 * @author Kapralov Sergey
 */
@NotAtom
@Path("github")
public class GithubHookEndpoint {
    /**
     * Github hook endpoint
     * @param eventType Event type, obtained from X-GitHub-Event header.
     * @param eventSignature Event signature from X-Hub-Signature header.
     * @param event Event body in JSON format
     * @return HTTP response.
     * @throws Exception If something went wrong.
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public final Response githubHook(@HeaderParam("X-GitHub-Event") final String eventType, @HeaderParam("X-Hub-Signature") final String eventSignature, final String event) throws Exception {
        new OpValidatingEventSignature(
            new Cp_GITHUB_HOOK_SECRET(),
            event,
            Objects.isNull(eventSignature) ? "" : eventSignature,
            new OpPersistAllPuzzles(
                new PsrcFromGithubEvent(
                    eventType,
                    event,
                    new Cp_GITHUB_AUTH_TOKEN()
                ),
                new ItGithubIssues(
                    new GhapiProduction(
                        new Cp_GITHUB_AUTH_TOKEN()
                    ),
                    new GprjFromGithubEvent(
                        event
                    )
                )
            ),
            AuthenticationException::new
        ).execute();
        return Response.ok("{}").build();
    }
}
