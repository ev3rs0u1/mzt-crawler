(defproject mzt-crawler "0.1.0"
  :description "Clojure Meizitu Crawler"
  :main ^:skip-aot mzt-crawler.core
  :dependencies [[org.clojure/clojure "1.8.0"] 
                [org.jsoup/jsoup "1.10.2"]]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})