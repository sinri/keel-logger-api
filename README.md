# Keel Logger Api

The low-level logging API for Keel.

## Record

Defined a basic log recording interface for universal logging without certain format.

## Event

For most scenarios, logging is for recording events,
which would hold a timestamp, a level, a message, sometimes more context entries, and throwable information for exception logging.
The common event logging interface.

## Issue

When a certain format of logging is needed,
the issue logging interface can be used to record issues.