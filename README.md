# Scalograf


[![build](https://github.com/mcsim4s/scalograf/actions/workflows/scala.yml/badge.svg)](https://github.com/mcsim4s/scalograf/actions)

Scalograf lets you generate and parse grafana entities(dashboards, datasources etc.) 
and communicate with grafana instances through built in http client.

The library is built on top of [circe](https://github.com/circe/circe) and [sttp](https://github.com/softwaremill/sttp).

<details>
    <summary>Disclaimer</summary>

```
    This project is currently in its super early development stage.
    Some parts of ADT are not obvious or easy to use. Some are missing. 
    Scalagraph doesn't provide much type safety or automated field derivations.
    There is no suitable Error model for the http client. 
    
    But with your interest (and maybe help) I am plannig to develop it to a usable state.
```
</details>

## Usage

You can find a getting started demo in [Demo.scala file](tools/src/main/scala/Demo.scala)

The demo can be started with `make start/demo` (installed docker required).
It will print grafana http address to console, so you can view the generated dashboard (user/pass is default to admin/admin).

Alternatively you can start grafana env separately from demo with `make start/grafana-env` (installed docker-compose required).
It will just start grafana and some test metrics sources. Grafana instance will be available on [localhost:1337](http://localhost:1337).
Then you can run `make start/demo-embedded` infinite amount of times. It will rewrite the generated dashboard to 
grafana instance.

Dashboard name is `Demo dashboard`.

## Features

### Scheme derivation 
Unfortunately grafana doesn't provide a complete scheme of its models. So we need to reverse engineer it from existing dashboards.
The way scalograf is doing it is by parsing community dashboards into scalograf adt and back to json. Then we compare the original
dashboard model and the generated one to see if something is missing or parsed wrong.

You can check [TestDataGen](tools/src/main/scala/TestDataGen.scala) which provides static data for unit testing,
or [Scrapper]() which infinitely scrapes community dashboards in the background.

If you're missing something from the scheme - feel free to reach out or introduce a pull request to fill the gap.

### Alerts dsl
The raw adt for grafana alerts seemed too complicated and unintuitive.
With scalograf you can write something like:

```scala
import scala.concurrent.duration.DurationInt
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