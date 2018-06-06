/*
 * MIT License
 *
 * Copyright (c) %today.year Kapralov Sergey
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
 *
 */

package com.github.skapral.puzzler.core.config;

import com.pragmaticobjects.oo.tests.Assertion;
import io.vavr.control.Option;
import org.assertj.core.api.Assertions;

/**
 * Assertion that passes if config property has expected value
 *
 * @author Kapralov Sergey
 */
public class AssertConfigPropertyHasValue implements Assertion {
    private final ConfigProperty configProperty;
    private final String expectedValue;

    /**
     * Ctor.
     *
     * @param configProperty Config property under test
     * @param expectedValue Expected value
     */
    public AssertConfigPropertyHasValue(ConfigProperty configProperty, String expectedValue) {
        this.configProperty = configProperty;
        this.expectedValue = expectedValue;
    }

    @Override
    public final void check() throws Exception {
        Option<String> optValue = configProperty.optionalValue();
        Assertions.assertThat(optValue).isNotEmpty();
        Assertions.assertThat(optValue.get()).isEqualTo(expectedValue);
    }
}
