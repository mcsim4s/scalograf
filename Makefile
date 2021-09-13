.PHONY: start/demo start/grafana-env start/demo-embedded

start/demo:
	sbt "tools/runMain scalograf.Demo"

start/grafana-env:
	docker-compose -f testkit/src/main/resources/datasources/docker-compose.yaml up

start/demo-embedded:
	sbt "tools/runMain scalograf.Demo http://localhost:1337"