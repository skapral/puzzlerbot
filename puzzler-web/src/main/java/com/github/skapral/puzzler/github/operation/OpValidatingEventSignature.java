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

package com.github.skapral.puzzler.github.operation;

import com.github.skapral.puzzler.config.ConfigProperty;
import com.github.skapral.puzzler.core.Operation;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.function.Function;

/**
 * Operation which is executed only if GitHub hook signatures
 * match on GitHub event. See <a href="https://developer.github.com/webhooks/securing/"></a>
 * for details.
 *
 * @author Kapralov Sergey
 */
public class OpValidatingEventSignature implements Operation {
    private final ConfigProperty secret;
    private final String eventBody;
    private final String eventSignature;
    private final Operation delegate;
    private final Function<String, RuntimeException> exceptionFn;

    /**
     * Ctor.
     *
     * @param secret Secret. If the property value is not provided, validation will be skept.
     * @param eventBody Event body.
     * @param eventSignature Event signature (X-Hub-Signature)
     * @param delegate Operation to delegate to, if signatures match.
     * @param exceptionFn Constructor of exception to throw if signatures mismatch.
     */
    public OpValidatingEventSignature(ConfigProperty secret, String eventBody, String eventSignature, Operation delegate, Function<String, RuntimeException> exceptionFn) {
        this.secret = secret;
        this.eventBody = eventBody;
        this.eventSignature = eventSignature;
        this.delegate = delegate;
        this.exceptionFn = exceptionFn;
    }

    @Override
    public final void execute() {
        if(secret.optionalValue().isEmpty()) {
            delegate.execute();
        } else {
            final String expectedSignature;
            try {
                final Mac mac = Mac.getInstance("HmacSHA1");
                final SecretKeySpec signingKey = new SecretKeySpec(secret.optionalValue().get().getBytes(), "HmacSHA1");
                mac.init(signingKey);
                final StringBuilder builder = new StringBuilder("sha1=");
                builder.append(Hex.encodeHex(mac.doFinal(eventBody.getBytes())));
                expectedSignature = builder.toString();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            if (expectedSignature.equals(eventSignature)) {
                delegate.execute();
            } else {
                throw exceptionFn.apply("Signatures mismatch");
            }
        }
    }
}
