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

package com.github.skapral.puzzler.core.text.threepars;

import io.vavr.collection.List;
import oo.atom.tests.Assertion;
import org.assertj.core.api.Assertions;

import java.util.function.Function;

/**
 * Fails if the paragraphs, emitted by the text item, meet expectations.
 *
 * @author Kapralov Sergey
 */
public class AssertTextInstanceProducesCertainParagraphs implements Assertion {
    private final Text text;
    private final List<Paragraph> expectedParagraphs;

    /**
     * Ctor.
     *
     * @param text Text under test.
     * @param expectedParagraphs Expected paragraphs.
     */
    public AssertTextInstanceProducesCertainParagraphs(Text text, List<Paragraph> expectedParagraphs) {
        this.text = text;
        this.expectedParagraphs = expectedParagraphs;
    }

    /**
     * Ctor.
     *
     * @param text Text under test.
     * @param expectedParagraphs Expected paragraphs.
     */
    public AssertTextInstanceProducesCertainParagraphs(Text text, Paragraph... expectedParagraphs) {
        this(text, List.of(expectedParagraphs));
    }

    @Override
    public final void check() throws Exception {
        Function<Paragraph, String> normalizer = p -> String.format("Paragraph(%s,%s)", p.type(), p.content());
        Assertions
            .assertThat(text.paragraphs().map(normalizer))
            .containsExactlyElementsOf(expectedParagraphs.map(normalizer));
    }
}
