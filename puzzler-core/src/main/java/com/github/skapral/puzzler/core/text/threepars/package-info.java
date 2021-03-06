/*-
 * ===========================================================================
 * puzzler-core
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Copyright (C) 2019 Kapralov Sergey
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * ============================================================================
 */
/**
 * Three-paragraph approach for parsing text and preparing it for making puzzles.
 *
 * The text is splat to the paragraphs (non-empty pieces of text separated by newline).
 * Each paragraph is classified in the following way:
 *
 * <ul>
 *     <li>
 *         The paragraph which mentions the predefined trigger word (usually, the name
 *         of puzzlerbot user) is controlling paragraph: it is used to identify that
 *         the text is the puzzle and provide the metadata.
 *     </li>
 *     <li>
 *         First non-controlling paragraph in the text is the puzzle's title.
 *     </li>
 *     <li>
 *         The rest non-controlling paragraphs are combined into description.
 *     </li>
 * </ul>
 */
package com.github.skapral.puzzler.core.text.threepars;
