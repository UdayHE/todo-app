# todo-app

Spring Boot 3.4 / Java 21 to-do app with PostgreSQL and a static front end in
`src/main/resources/static/`. The API is served under `/to-do`.

## Setup

- Needs Java 21 (Gradle 8.11 does not run on JDK 22+) and a PostgreSQL on
  `localhost:5432`, database `todos`, user `postgres`, password `12345678`:
  `docker compose up -d database`.
- No env files to copy; datasource settings live in
  `src/main/resources/application.properties`.

## Run

`./gradlew bootRun --args='--server.port=<port>'`, then open
`http://localhost:<port>/to-do/`.

## Validate

`bash factory/checks.sh` (runs `./gradlew clean build`, including tests).

## Software factory

`/factory run` builds every open GitHub issue labeled `factory` through
intake → spec → build → gate → review → approve, one PR per issue. State is in
`factory/jobs/` (gitignored); `factory/bin/job` is its only writer. Checks:
`factory/checks.sh`. Dashboard: `cd factory && python3 -m http.server`, then
open http://localhost:8000/dashboard.html.

Each job works in its own git worktree, a full checkout of the repo at
`.worktrees/<job-id>` (gitignored). A tool run from the repo root that doesn't
read `.gitignore` sees those copies too; exclude `.worktrees/` in its config.
