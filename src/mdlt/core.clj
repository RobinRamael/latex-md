(ns mdlt.core
  (:use [clojure.contrib.duck-streams :only (read-lines)]
	[clojure.pprint :only (pprint)]
	[mdlt.parser :only (parse)])
  (:gen-class))

(defn -main [& args]
  (pprint (parse (read-lines (first args)))))