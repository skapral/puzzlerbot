package com.github.skapral.puzzler.core.text.threepars;

import io.vavr.collection.List;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Basic three-paragraphs text implementation
 *
 * @author Kapralov Sergey
 */
public class TxtBase implements Text {
    private final TriggerWord triggerWord;
    private final String text;

    /**
     * Ctor.
     *
     * @param triggerWord Trigger word.
     * @param text Text to parse.
     */
    public TxtBase(TriggerWord triggerWord, String text) {
        this.triggerWord = triggerWord;
        this.text = text;
    }

    @Override
    public final List<Paragraph> paragraphs() {
        try(BufferedReader reader = new BufferedReader(new StringReader(text))) {
            boolean titleFound = false;
            final ArrayList<Paragraph> result = new ArrayList<>();
            for(String line : reader.lines().collect(Collectors.toList())) {
                if(line.contains(triggerWord.value())) {
                    result.add(
                        new ParStatic(Paragraph.Type.CONTROLLING, line)
                    );
                } else if(!line.trim().isEmpty() && !titleFound) {
                    titleFound = true;
                    result.add(
                        new ParStatic(
                            Paragraph.Type.TITLE,
                            line
                        )
                    );
                } else {
                    result.add(
                        new ParStatic(
                            Paragraph.Type.DESCRIPTION,
                            line
                        )
                    );
                }
            }
            return List.ofAll(result);
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
