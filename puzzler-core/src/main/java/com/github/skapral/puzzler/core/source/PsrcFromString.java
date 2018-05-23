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

package com.github.skapral.puzzler.core.source;

import com.github.skapral.puzzler.core.Puzzle;
import com.github.skapral.puzzler.core.PuzzleSource;
import com.github.skapral.puzzler.core.puzzle.PzlStatic;
import io.vavr.collection.List;

import java.io.BufferedReader;
import java.io.StringReader;

public class PsrcFromString implements PuzzleSource {
    private final String comment;

    public PsrcFromString(String comment) {
        this.comment = comment;
    }

    public final List<Puzzle> puzzles() {
        try(BufferedReader reader = new BufferedReader(new StringReader(comment))) {
            return reader.lines().map(s -> new PzlStatic("test", s)).collect(List.collector());
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
