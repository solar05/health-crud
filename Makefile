checks:	check-format check-namespaces
fix:
	clj -Sdeps '{:deps {lein-cljfmt {:mvn/version "0.6.4"}}}' -m cljfmt.main fix
check-format:
	clj -Sdeps '{:deps {lein-cljfmt {:mvn/version "0.6.4"}}}' -m cljfmt.main check
check-namespaces:
	clj -A:kibit
tests:
	clj -A:test
uber:
	clj -Sdeps '{:deps {uberdeps {:mvn/version "0.1.11"}}}' -m uberdeps.uberjar
run:
	java -cp target/health-crud.jar clojure.main -m health-crud.core
