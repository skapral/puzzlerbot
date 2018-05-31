package com.github.skapral.puzzler.github.operation;

import com.github.skapral.puzzler.core.Operation;
import io.vavr.control.Option;
import org.json.JSONObject;

/**
 * Operation, which is executed only if GitHub event sender and
 * repository owner are the same person.
 *
 * @author Kapralov Sergey
 */
public class OpIgnoringUnprivildgedEventSender implements Operation {
    private final String githubEvent;
    private final Operation delegate;

    /**
     * Ctor.
     *
     * @param githubEvent GitHub webhook event to validate (JSON)
     * @param delegate operation to pass control to if verification is ok
     */
    public OpIgnoringUnprivildgedEventSender(String githubEvent, Operation delegate) {
        this.githubEvent = githubEvent;
        this.delegate = delegate;
    }

    @Override
    public final void execute() {
        final JSONObject jsonObject = new JSONObject(githubEvent);
        final String repoOwner = Option.of(jsonObject)
            .map(jo -> jo.getJSONObject("repository"))
            .map(jo -> jo.getJSONObject("owner"))
            .map(jo -> jo.getString("login"))
            .get();
        final String eventOwner = Option.of(jsonObject)
            .map(jo -> jo.getJSONObject("sender"))
            .map(jo -> jo.getString("login"))
            .get();
        if(repoOwner.equals(eventOwner)) {
            delegate.execute();
        }
    }
}
