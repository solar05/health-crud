(defproject health-crud "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :uberjar-name "health-crud.jar"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [compojure "1.6.1"]
                 [ring/ring-core "1.8.1"]
                 [ring/ring-jetty-adapter "1.8.1"]
                 [org.clojure/java.jdbc "0.7.11"]
                 [ring/ring-defaults "0.3.2"]
                 [org.postgresql/postgresql "42.2.14"]]
  :plugins [[jonase/eastwood "0.3.10"]
            [lein-cljfmt "0.6.8"]
            [lein-kibit "0.1.8"]
            [lein-ring "0.8.13"]
            [hiccup "1.0.5"]]
  :profiles {:dev {:dependencies [[org.xerial/sqlite-jdbc "3.32.3.2"]]}
             :uberjar {:aot :all}})
