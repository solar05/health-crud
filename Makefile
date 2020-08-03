checks: check-format check-style check-namespaces

check-format:
	lein cljfmt check

fix-format:
	lein cljfmt fix

check-style:
	lein kibit

check-namespaces:
	lein eastwood '{:exclude-linters [:suspicious-expression]}'

tests:
	lein trampoline test

run:
	lein run

build:
	docker build -t health-crud .

docker-run:
	docker run -it --rm --name health-app health-crud

