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

package com.skapral.github.puzzler.web.jersey;

import com.github.skapral.puzzler.core.operation.OpPersistAllPuzzles;
import com.skapral.github.puzzler.config.Cp_GITHUB_AUTH_TOKEN;
import com.skapral.github.puzzler.github.itracker.ItGithubIssues;
import com.skapral.github.puzzler.github.project.GprjFromGithubEvent;
import com.skapral.github.puzzler.github.source.PsrcStubbedFromGithubEvent;
import oo.atom.anno.NotAtom;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@NotAtom
@Path("github")
public class GithubHookEndpoint {
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public final Response githubHook(@HeaderParam("X-GitHub-Event") final String eventType, final String event) throws Exception {
        new OpPersistAllPuzzles(
            new PsrcStubbedFromGithubEvent(
                eventType,
                event
            ),
            new ItGithubIssues(
                new GprjFromGithubEvent(
                    event
                ),
                new Cp_GITHUB_AUTH_TOKEN()
            )
        ).execute();
        return Response.ok("{}").build();
    }
}
