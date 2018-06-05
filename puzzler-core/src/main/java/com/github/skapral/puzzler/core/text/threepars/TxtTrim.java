package com.github.skapral.puzzler.core.text.threepars;

import io.vavr.collection.List;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static com.github.skapral.puzzler.core.text.threepars.Paragraph.Type.CONTROLLING;
import static com.github.skapral.puzzler.core.text.threepars.Paragraph.Type.DESCRIPTION;
import static com.github.skapral.puzzler.core.text.threepars.Paragraph.Type.TITLE;


/**
 * Text, omitting empty leading and trailing paragraphs.
 *
 * @author Kapralov Sergey
 */
public class TxtTrim implements Text {
    private final Text text;

    /**
     * Ctor.
     *
     * @param text Text to trim
     */
    public TxtTrim(Text text) {
        this.text = text;
    }

    @Override
    public final List<Paragraph> paragraphs() {
        boolean firstNonEmptyParagraphFound = false;
        int emptyParagraphsCounter = 0;
        final ArrayList<Paragraph> result = new ArrayList<>();
        final Predicate<Paragraph> emptyDescriptionParagraph = p ->
            p.type().equals(DESCRIPTION) &&
            p.content().trim().isEmpty();
        for(Paragraph paragraph : text.paragraphs()) {
            if(emptyDescriptionParagraph.test(paragraph)) {
                emptyParagraphsCounter++;
            } else if(paragraph.type().equals(CONTROLLING)) {
                result.add(paragraph);
            } else if(paragraph.type().equals(TITLE)) {
                emptyParagraphsCounter = 0;
                result.add(paragraph);
            } else if(!firstNonEmptyParagraphFound && !paragraph.content().isEmpty()) {
                firstNonEmptyParagraphFound = true;
                emptyParagraphsCounter = 0;
                result.add(paragraph);
            } else {
                IntStream.range(
                    0,
                    emptyParagraphsCounter
                ).forEach(
                    i -> result.add(
                        new ParStatic(
                            Paragraph.Type.DESCRIPTION,
                            ""
                        )
                    )
                );
                result.add(paragraph);
                emptyParagraphsCounter = 0;
            }
        }
        return List.ofAll(result);
    }
}
