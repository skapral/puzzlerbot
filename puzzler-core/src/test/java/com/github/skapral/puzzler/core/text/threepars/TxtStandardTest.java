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

import oo.atom.tests.TestCase;
import oo.atom.tests.TestsSuite;

class TxtStandardTest extends TestsSuite {
    public TxtStandardTest() {
        super(
            new TestCase(
                "Simple trigger text",
                new AssertTextInstanceProducesCertainParagraphs(
                    new TxtStandard(
                        new TwStatic("@puzzlertest"),
                        "@puzzlertest FYI"
                    ),
                    new ParStatic(
                        Paragraph.Type.CONTROLLING,
                        "@puzzlertest FYI"
                    )
                )
            ),
            new TestCase(
                "Simple multiline text with trigger, leading trigger",
                new AssertTextInstanceProducesCertainParagraphs(
                    new TxtStandard(
                        new TwStatic("@puzzlertest"),
                        String.join(
                            "\r\n",
                            "@puzzlertest FYI",
                            "Deployment must be reconfigured",
                            "Deployment must be reconfigured because of this and that"
                        )
                    ),
                    new ParStatic(
                        Paragraph.Type.CONTROLLING,
                        "@puzzlertest FYI"
                    ),
                    new ParStatic(
                        Paragraph.Type.TITLE,
                        "Deployment must be reconfigured"
                    ),
                    new ParStatic(
                        Paragraph.Type.DESCRIPTION,
                        "Deployment must be reconfigured because of this and that"
                    )
                )
            ),
            new TestCase(
                "Simple multiline text with trigger, trailing trigger",
                new AssertTextInstanceProducesCertainParagraphs(
                    new TxtStandard(
                        new TwStatic("@puzzlertest"),
                        String.join(
                            "\r\n",
                            "Deployment must be reconfigured",
                            "Deployment must be reconfigured because of this and that",
                            "@puzzlertest FYI"
                        )
                    ),
                    new ParStatic(
                        Paragraph.Type.TITLE,
                        "Deployment must be reconfigured"
                    ),
                    new ParStatic(
                        Paragraph.Type.DESCRIPTION,
                        "Deployment must be reconfigured because of this and that"
                    ),
                    new ParStatic(
                        Paragraph.Type.CONTROLLING,
                        "@puzzlertest FYI"
                    )
                )
            ),
            new TestCase(
                "Multiline text with empty paragraphs, leading trigger",
                new AssertTextInstanceProducesCertainParagraphs(
                    new TxtStandard(
                        new TwStatic("@puzzlertest"),
                        String.join(
                            "\r\n",
                            "    ",
                            "@puzzlertest FYI",
                            "",
                            "",
                            "Deployment must be reconfigured",
                            "\t  ",
                            "Deployment must be reconfigured because of this and that",
                            "",
                            ""
                        )
                    ),
                    new ParStatic(
                        Paragraph.Type.CONTROLLING,
                        "@puzzlertest FYI"
                    ),
                    new ParStatic(
                        Paragraph.Type.TITLE,
                        "Deployment must be reconfigured"
                    ),
                    new ParStatic(
                        Paragraph.Type.DESCRIPTION,
                        "Deployment must be reconfigured because of this and that"
                    )
                )
            )
        );
    }
}
