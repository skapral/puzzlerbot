package com.github.skapral.puzzler.core.source;

import com.github.skapral.puzzler.core.Puzzle;
import com.github.skapral.puzzler.core.PuzzleSource;
import com.github.skapral.puzzler.core.puzzle.PzlUsingThreeParsText;
import com.github.skapral.puzzler.core.text.threepars.TriggerWord;
import com.github.skapral.puzzler.core.text.threepars.TxtStandard;
import io.vavr.collection.List;

import static com.github.skapral.puzzler.core.text.threepars.Paragraph.Type.CONTROLLING;

/**
 * Puzzle source from a set of generic comments
 *
 * @author Kapralov Sergey
 */
public class PsrcFromComments implements PuzzleSource {
    private final TriggerWord triggerWord;
    private final List<String> comments;

    /**
     * Ctor.
     *
     * @param triggerWord Trigger word for three-pars algorithm
     * @param comments comments to parse
     */
    public PsrcFromComments(TriggerWord triggerWord, List<String> comments) {
        this.triggerWord = triggerWord;
        this.comments = comments;
    }

    /**
     * Ctor.
     *
     * @param triggerWord Trigger word for three-pars algorithm
     * @param comments comments to parse
     */
    public PsrcFromComments(TriggerWord triggerWord, String... comments) {
        this(
            triggerWord,
            List.of(comments)
        );
    }

    @Override
    public final List<Puzzle> puzzles() {
        return comments
            .map(body -> new TxtStandard(
                triggerWord,
                body
            ))
            .filter(txt -> txt.paragraphs().exists(p -> p.type() == CONTROLLING))
            .map(txt -> new PzlUsingThreeParsText(txt))
            .collect(List.collector());
    }
}
