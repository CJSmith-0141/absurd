# surreal.scala

An example application built from the [ce3.g8 template](https://github.com/typelevel/ce3.g8).

This is the start of an attempt to make a parser for SurrealQL and a client that 
can communicate with SurrealDB via the HTTP interface. Very much a WIP.

## Install SurrealDB
[Follow this guide](https://surrealdb.com/docs/installation)

## Start SurrealDB in permissive mode (just for development!)
```shell
surreal start --log trace --user root --pass root memory --addr 127.0.0.1/32 --bind 127.0.0.1:8000
```

## Run application

```shell
sbt run
```

## Run tests

```shell
sbt test
```
