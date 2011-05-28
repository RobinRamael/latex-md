(ns mdlt.core
  (:use [clojure.contrib.duck-streams :only (read-lines)]
	[clojure.pprint :only (pprint)]
	[mdlt.parser :only (parse)]
	[mdlt.generator :only (latex)])
  (:gen-class))

(defn -main [& args]
  (print (latex (parse (read-lines (first args))))))