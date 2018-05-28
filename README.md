# Puzzler bot

[![Build Status](https://img.shields.io/travis/skapral/puzzlerbot/master.svg)](https://travis-ci.org/skapral/puzzlerbot)
[![Codecov](https://codecov.io/gh/skapral/puzzlerbot/branch/master/graph/badge.svg)](https://codecov.io/gh/skapral/puzzlerbot)

@todo #31 to add some badge which will show the `@puzzlerbot`'s status.

## Notice:

Puzzle Driver Development concept, `pdd` and `0pdd` tools belong to [Yegor Bugayenko](yegor256.com).
Links:
- [Puzzle Driver Development concept](https://www.yegor256.com/2010/03/04/pdd.html)
- [0pdd](https://github.com/yegor256/0pdd/)
- [pdd](https://github.com/yegor256/pdd/)

## Introduction:

`@puzzlerbot` is a bot which provides new ways of managing the
puzzles - trackable TODO stub-like tasks which make possible to 
[cut corners](https://www.yegor256.com/2015/01/15/how-to-cut-corners.html)
in fast and smooth way. Initial concept supposes that the puzzles
are defined as specially-formatted TODO items in the project's source code, but
practically this approach has some drawbacks:

- Code base is volatile. Puzzles in the code are rarely stay in one line. 
Some code is added, some code is removed, some codebase is changing intentionally
or during merge conflicts resolving - it all means that puzzle in code has no stable 
identity. It opens plenty of ways to mess the issue tracker or get abnormal behavior
of the parsers.

- Code is not the only place where we could cut corners: we may want to cut corners
during various high-level discussions, architecture planning, 
epic stories decomposition - somewhere where code is not existing yet.

- The syntax, used by documentation generators (like javadoc, for example) differs
from conventions, used in GitHub (Markdown). It makes the puzzle hard, almost impossible,
to format properly both in documentation and in GitHub Markdown.

`@puzzlerbot`'s approach is to scan for puzzles in GitHub comments after 
the issue or pull request is closed. Github comments tend to be more stable than 
the code base, they may exist when even no code is written yet.

@todo #8 Describe how to properly place puzzles so that puzzler bot discovers them. 

## Principles:

- `@puzzlerbot` is not a replacement for `0pdd`. It will be always possible to use
them both in one project without conflicts.

## Quick setup:

Just add new GitHub webhook to your project:
- Payload URL: `https://puzzler-bot.apps.skapral.com/github`
- Content-type: `application/json`
- Which events would you like to trigger this webhook: `Issues` and `Pull requests` would be enough.

## How to create a puzzle

Puzzles are placed in issue or pull request comments in GitHub. Once the issue of pull request is
closed, `@puzzlerbot` scans its comments for puzzles and creates one GitHub issue per puzzle. 
One comment may have at most one puzzle. To make `@puzzlebot` properly recognize your puzzles,
follow conventions described in this section.

Consider comment with this text:

```
@puzzlebot FYI

Something weird happened with the system, heeds to be fixed.

Steps to reproduce:
0. Preconditions
1. Step one
2. Step two
3. Step three

Expected result: Everything is okay.
Actual result Exception is thrown.
```

This comment will be parsed by `@puzzlerbot` as a puzzle with title
"Something weird happened with the system, heeds to be fixed." and description
"Steps to reproduce..." etc.

### In details

When parsing comments and seaking for puzzles, `@puzzlerbot` splits each comment to
paragraphs and classifies each paragraph to one of the types below:

- *Controlling* - the paragraph which mentions `@puzzlerbot` user. Controlling paragraphs
are an indicator that the comment represents a puzzle.
One comment may have any number of controlling paragraphs but only the first will be taken 
into account. Controlling paragraphs are not included to the resulting GitHub issue.

- *Title* - the first non-controlling paragraph in a comment. One comment may have only one
title paragraph. This paragraph is used for the GitHub issue's title.

- *Description* - rest of the paragraphs. They are used as the GitHub issue's description.

## License

MIT License

Copyright (c) 2018 Kapralov Sergey

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
