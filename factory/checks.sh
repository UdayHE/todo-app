#!/usr/bin/env bash
# The repo's checks. Exit 0 only when the branch is fit to review.
#
# The gate runs this script from the main checkout with the job's worktree as
# the working directory. Keep it fast, deterministic and non-interactive: no
# watch modes, no prompts, nothing that rewrites files.
#
# The context-load test needs PostgreSQL on localhost:5432 (db todos,
# postgres/12345678): `docker compose up -d database`.
set -euo pipefail

# Gradle 8.11 can't run on newer JDKs; the project targets Java 21.
if [ -x /usr/libexec/java_home ] && J=$(/usr/libexec/java_home -v 21 2>/dev/null); then
  export JAVA_HOME="$J"
fi

./gradlew --no-daemon --console=plain clean build
