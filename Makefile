checks:	check-format check-namespaces
fix:
	clojure -Sdeps '{:deps {lein-cljfmt {:mvn/version "0.6.4"}}}' -m cljfmt.main fix
check-format:
	clojure -Sdeps '{:deps {lein-cljfmt {:mvn/version "0.6.4"}}}' -m cljfmt.main check
check-namespaces:
	clojure -A:kibit
tests:
	clojure -A:test
uber:
	clojure -Sdeps '{:deps {uberdeps {:mvn/version "0.1.11"}}}' -m uberdeps.uberjar
run:
	java -cp target/health-crud.jar clojure.main -m health-crud.core
