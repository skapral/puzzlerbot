# Puzzler bot

[![Build Status](https://img.shields.io/travis/skapral/puzzlerbot/master.svg)](https://travis-ci.org/skapral/puzzlerbot)
[![Codecov](https://codecov.io/gh/skapral/puzzlerbot/branch/master/graph/badge.svg)](https://codecov.io/gh/skapral/puzzlerbot)
[![Heroku](https://heroku-badge.herokuapp.com/?app=puzzler-bot)](https://puzzler-bot.herokuapp.com/status)

## Notice:

Puzzle Driver Development concept, `pdd` and `0pdd` tools belong to [Yegor Bugayenko](yegor256.com).
Links:
- [Puzzle Driver Development concept](https://www.yegor256.com/2010/03/04/pdd.html)
- [0pdd](https://github.com/yegor256/0pdd/)
- [pdd](https://github.com/yegor256/pdd/)

## Introduction:

Puzzler bot is a bot which provides new ways of managing the
puzzles - trackable TODO stub-like tasks which make possible to 
[cut corners](https://www.yegor256.com/2015/01/15/how-to-cut-corners.html)
in fast and smooth way. Initial concept supposes that the puzzles
are defined as specially-formatted TODO items in the project's source code, but
practically this approach have some drawbacks:

- Code base is volatile. Puzzles in the code are rarely stay in one line. 
Some code is added, some code is removed, some codebase is changing intentionally
or during merge conflicts resolving - it all means that puzzle in code has no stable 
identity. It opens plenty of ways to mess the issue tracker or get abnormal behavior
of the parsers.

- Code is not the only place where we could cut corners: we may want to cut corners
during various high-level discussions, architecture planning, 
epic stories decomposition - somewhere where code is not existing yet.

Puzzler bot's approach is to scan for puzzles in github comments after 
the issue or pull request is closed. Github comments tend to be more stable than 
the code base, they may exist when even no code is written yet.

Details of how the puzzles are handled by Puzzler bot will be provided later. 

## Principles:

- Puzzler bot is not a replacement for 0pdd. It will be always possible to use
them both in one project.

## Quick setup:

To be defined later.

