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

package com.github.skapral.puzzler.gitlab.source;

import java.util.stream.StreamSupport;

import org.json.JSONArray;
import org.json.JSONObject;

import com.github.skapral.puzzler.core.PuzzleSource;
import com.github.skapral.puzzler.core.source.PsrcFromComments;
import com.github.skapral.puzzler.core.source.PsrcInferred;
import com.github.skapral.puzzler.gitlab.text.threepars.TwPuzzlerbotUser;

import io.vavr.collection.List;

/**
 * Puzzle source from Gitlab comments (JSON).
 *
 * @author Kapralov Sergey
 */
public class PsrcFromGitlabNotes extends PsrcInferred {
    /**
     * Ctor.
     *
     * @param comments Gitlab comments in JSON format.
     */
    public PsrcFromGitlabNotes(String comments) {
        super(
            new Inference(
                comments
            )
        );
    }

    /**
     * {@link PsrcFromGitlabNotes} inference
     *
     * @author Kapralov Sergey
     */
    private static class Inference implements PuzzleSource.Inference {
        private final String comments;

        /**
         * Ctor.
         *
         * @param comments Gitlab comments in JSON format.
         */
        public Inference(String comments) {
            this.comments = comments;
        }

        @Override
        public final PuzzleSource puzzleSource() {
            final JSONArray jsonComments = new JSONArray(comments);
            return new PsrcFromComments(
                new TwPuzzlerbotUser(),
                StreamSupport.stream(jsonComments.spliterator(), false)
                    .map(o -> (JSONObject) o)
                    .map(o -> o.getString("body"))
                    .collect(List.collector())
            );
        }
    }
}
