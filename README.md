# Scalograf


[![build](https://github.com/mcsim4s/scalograf/actions/workflows/scala.yml/badge.svg)](https://github.com/mcsim4s/scalograf/actions)

This library lets you generate and parse grafana entities(dashboards, datasources etc.) 
and communicate with grafana instances thought build in http client.

The library is build on top of [circe](https://github.com/circe/circe) and [sttp](https://github.com/softwaremill/sttp).

<details>
    <summary>Disclaimer</summary>

```
    This library is currently in super early stage of it's development.
    Some parts of ADT is unobvious and not easy to use. Some is missing. 
    Library doesn't provide much of type safety or automated fields derivations.
    There is no suitable Error model for the http client. 
    
    But with your interest (and maybe help) i am plannig to develop it to a usable state.
```
</details>

## Usage

You can find a getting started demo in [Demo.scala file](tools/src/main/scala/Demo.scala)

The demo can be started with `make start/demo` (installed docker required).
It will print grafana http address to console, so you can view generated dashboard (user/pass is default to admin/admin).

Alternatively you can start grafana env separately from demo with `make start/grafana-env` (installed docker-compose required).
It will just start grafana and some test metrics sources. Grafana instance will be available on [localhost:1337](http://localhost:1337).
Then you can run `make start/demo-embedded` infinite amount of times. It will rewrite generated dashboard to 
grafana instance.

Dashboard name is `Demo dashboard`.

## Features

### Scheme derivation 
Unfortunately grafana doesn't provide a complete scheme of its models. So we need to reverse engineer it from existing dashboards.
The way scalograf is doing it is by parsing community dashboards into scalograf adt and back to json. Then we compare otiginal
dashboard model and generated one to see if something is missing or parsed wrong.

You can check [TestDataGen](tools/src/main/scala/TestDataGen.scala) witch provides static data for unit testing,
or [Scrapper]() witch scrapes community dashboards in background infinitely.

If you're missing something from scheme - feel fre to write me or introduce a pull request to fill the gap.

### Alerts dsl
The raw adt for grafana alerts seemed to complicated and unintuitive.
With scalograf you can write something like:

```scala
val alert = Alert(
    conditions =
      max("A").over(5.seconds).from(now).isAbove(16)
        or avg("A").over(10.minutes).from(now).isAbove(13)
        or min("A").over(1.hour).from(now).isAbove(8),
    name = "High rpc alert",
    frequency = 5.seconds,
    `for` = 10.seconds,
    handler = 1
  )
```