(defproject rhg135/node-hickory "0.0.1"
  :description "An HTML parser for nodejs"
  :url "http://github.com/node-hickory"
  :license {:name "Eclipse Public MIT"
            :url "http://www.opensource.org/licenses/MIT"}
  :plugins [[lein-cljsbuild "1.0.1"]
            [lein-npm "0.2.0"]]
  :node-dependencies [[htmlparser "1.7.7"]]
  :cljsbuild  {:builds  [{:source-paths ["src"]
                          :compiler {:output-to "out.js"
                                     :optimizations :simple
                                     :target :nodejs
                                     :pretty-print true}}]}
  :dependencies [[org.clojure/clojurescript "0.0-2138"]])
