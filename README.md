# health-crud
![Clojure CI](https://github.com/solar05/health-crud/workflows/Clojure%20CI/badge.svg)

A simple crud that uses Clojure.

## Commands
To run project manually, run:
```bash
$ createdb patients
$ make run
```
Or you can use docker:
```bash
$ make compose-run
$ make compose-down
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
