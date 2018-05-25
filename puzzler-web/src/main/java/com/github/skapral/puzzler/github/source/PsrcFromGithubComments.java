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

package com.github.skapral.puzzler.github.source;

import com.github.skapral.puzzler.core.Puzzle;
import com.github.skapral.puzzler.core.PuzzleSource;
import com.github.skapral.puzzler.core.puzzle.PzlUsingThreeParsText;
import com.github.skapral.puzzler.core.text.threepars.TxtStandard;
import com.github.skapral.puzzler.github.text.threepars.TwPuzzlerbotUser;
import io.vavr.collection.List;
import org.json.JSONArray;
import org.json.JSONObject;
import static com.github.skapral.puzzler.core.text.threepars.Paragraph.Type.*;

import java.util.stream.StreamSupport;

/**
 * Puzzle source from Github comments (JSON).
 *
 * @author Kapralov Sergey
 */
public class PsrcFromGithubComments implements PuzzleSource {
    private final String comments;

    /**
     * Ctor.
     *
     * @param comments Github comments in JSON format.
     */
    public PsrcFromGithubComments(String comments) {
        this.comments = comments;
    }

    @Override
    public final List<Puzzle> puzzles() {
        final JSONArray jsonComments = new JSONArray(comments);
        return StreamSupport.stream(jsonComments.spliterator(), false)
            .map(o -> (JSONObject) o)
            .map(o -> o.getString("body"))
            .map(body -> new TxtStandard(
                new TwPuzzlerbotUser(),
                body
            ))
            .filter(txt -> txt.paragraphs().exists(p -> p.type() == CONTROLLING))
            .map(txt -> new PzlUsingThreeParsText(txt))
            .collect(List.collector());
    }
}
