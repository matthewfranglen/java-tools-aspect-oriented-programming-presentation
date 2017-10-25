Overview
--------

This will cover how stuff works under the hood:

- Spring AOP, proxy pattern, dynamic proxies in java
- code coverage, byte/source code injection, java agents
- sampling vs profiling

Spring Aspect Oriented Programming
----------------------------------

[Show what problem slide]

### What problem does this solve?

This allows implementation of cross cutting concerns without repetition.

[Show cross cutting concerns slide]

Applications have paths through them.
Different paths have different behaviours.
Viewing the home page is quite different to logging in.

There are behaviours which you always want to have.
 * Security
 * Logging
 * Audit

For these to be effective they must be implemented universally and consistently.
Writing repetitive code is prone to error.
Aspect Oriented Programming allows you to implement cross cutting concerns without repetition.
