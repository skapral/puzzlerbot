package com.github.skapral.puzzler.core.source;

import com.github.skapral.puzzler.core.puzzle.PzlStatic;
import com.github.skapral.puzzler.core.text.threepars.TwStatic;
import com.pragmaticobjects.oo.tests.TestCase;
import com.pragmaticobjects.oo.tests.junit5.TestsSuite;

class PsrcFromCommentsTest extends TestsSuite {
    public PsrcFromCommentsTest() {
        super(
            new TestCase(
                "puzzle source produces puzzles from comments",
                new AssertPuzzleSourceProducesCertainPuzzles(
                    new PsrcFromComments(
                        new TwStatic("@puzzlerbot"),
                        String.join(
                            "\r\n",
                            "Some arbitrary comment"
                        ),
                        String.join(
                            "\r\n",
                            "@puzzlerbot FYI",
                            "",
                            "Something wrong happened",
                            "",
                            "Description of what is wrong"
                        )
                    ),
                    new PzlStatic(
                        "Something wrong happened",
                        "Description of what is wrong"
                    )
                )
            )
        );
    }
}