package com.github.skapral.puzzler.github.operation;

import com.github.skapral.puzzler.core.operation.AssertOperationFails;
import com.github.skapral.puzzler.core.operation.AssertOperationSuccessful;
import com.github.skapral.puzzler.core.operation.OpFail;
import com.pragmaticobjects.oo.tests.TestCase;
import com.pragmaticobjects.oo.tests.junit5.TestsSuite;
import org.apache.commons.io.IOUtils;

class OpIgnoringUnprivildgedEventSenderTest extends TestsSuite {
    private static final String OWNER_IS_SENDER;
    private static final String OWNER_IS_NOT_SENDER;

    static {
        try {
            OWNER_IS_SENDER = IOUtils.toString(OpValidatingGithubEventSignature.class.getResource("ownerIsSender"));
            OWNER_IS_NOT_SENDER = IOUtils.toString(OpValidatingGithubEventSignature.class.getResource("ownerIsNotSender"));
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

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