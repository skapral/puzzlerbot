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

package com.github.skapral.puzzler.config;

import io.vavr.collection.List;
import io.vavr.control.Option;

/**
 * Configuration property, value of which is obtained from another
 * {@link ConfigProperty} instances, until one which defined is met.
 *
 * @author Kapralov Sergey
 */
public class CpOneOf implements ConfigProperty {
    private final List<ConfigProperty> configProperties;

    /**
     * Ctor.
     *
     * @param configProperties Properties to search from.
     */
    public CpOneOf(final List<ConfigProperty> configProperties) {
        this.configProperties = configProperties;
    }

    /**
     * Ctor.
     *
     * @param configProperties Properties to search from.
     */
    public CpOneOf(final ConfigProperty... configProperties) {
        this(List.of(configProperties));
    }

    @Override
    public final Option<String> optionalValue() {
        return configProperties
            .map(ConfigProperty::optionalValue)
            .find(o -> o.isDefined())
            .get();
    }
}

