(ns mdlt.generator
  (:use [clojure.string :only (join)]
	  [clojure.java.io :only (resource)]))

(declare target-grammar)

(defn between-tags [tag s]
  (str "\\begin{" tag "}" \newline  s \newline "\\end{" tag "}"))


(defn as-latex [tree opts]
  (join \newline
	(map #(let [[k v] %
		    f (k target-grammar)]
		(if (nil? f)
		  v
		  (f v opts)))
	     tree)))

(defn latex [tree opts]
  (str (slurp (resource "before.tex"))
       (as-latex tree opts)
       (slurp (resource "after.tex"))))


(defn gen-header [s n numbered?]
  (let [name (condp = n
		 1 "section"
		 2 "subsection"
		 3 "subsubsection"
		 "subsubsection")]
    (str "\\" name (when (not numbered?) "*") "{" s "}")))

(def target-grammar
     {:h1 #(gen-header %1 1 (:numbered %2))
      :h2 #(gen-header %1 2 (:numbered %2))
      :h3 #(gen-header %1 3 (:numbered %2))
      :code (fn [s opts] (between-tags "lstlisting" (as-latex s opts)))
      :quoted (fn [s opts] (between-tags "quote" (as-latex s opts)))
      :text (fn [s opts] (if (empty? s) s (str s "\\\\")))
      :raw (fn [s opts] (str s))})