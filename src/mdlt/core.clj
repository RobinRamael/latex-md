(ns mdlt.core
  (:use [clojure.contrib.duck-streams :only (read-lines)]
	[clojure.pprint :only (pprint)]
	[mdlt.parser :only (parse)]
	[mdlt.generator :only (latex)]
	[clojure.java.io :only (resource)]
	[clojure.contrib.command-line :only (with-command-line)])
  (:gen-class))

(declare error-out)

(defn -main [& args]
  (with-command-line args
    "latex-md: Generate latex from markdown."
    [[numbered? n? "Output numbered headers."]
     remaining]
    (let [f (first remaining)]
      (if f
	(try (let [lines  (read-lines f)]
	       (latex (parse lines) {:numbered numbered?}))
	     (catch java.io.FileNotFoundException e
	       (error-out "File" f "not found.")))
	(error-out "No file specified.")))))


(defn error-out [& s]
  (binding [*out* *err*]
    (apply println s)))