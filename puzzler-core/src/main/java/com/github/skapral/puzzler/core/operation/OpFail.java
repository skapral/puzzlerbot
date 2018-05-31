package com.github.skapral.puzzler.core.operation;

import com.github.skapral.puzzler.core.Operation;

/**
 * Operation that fails. Always.
 *
 * @author Kapralov Sergey
 */
public class OpFail implements Operation {
    @Override
    public final void execute() {
        throw new RuntimeException("Intended failure");
    }
}
