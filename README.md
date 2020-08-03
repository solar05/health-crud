# health-crud
![Clojure CI](https://github.com/solar05/health-crud/workflows/Clojure%20CI/badge.svg)

A simple crud that uses Clojure.

## Commands
To run project, firstly, create postgres db:
```bash
$ createdb patients
```
and after that you can run project manually:
```bash
$ make run
```
or use docker:
```bash
$ make build
$ make docker-run
```

To run linter, namespace checker and so on use:
```bash
$ make checks
```

To run tests:
```bash
$ make tests
```

To run format fix:
```bash
$ make fix-format
```

## Download
Download from docker hub:
```bash
$ docker pull solar7455/health-crud:latest
```
