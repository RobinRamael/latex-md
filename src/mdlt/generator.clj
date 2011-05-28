(ns mdlt.generator
  (:use [clojure.string :only (join)]
	  [clojure.java.io :only (resource)]))

(declare target-grammar)

(defn between-tags [tag s]
  (str "\\begin{" tag "}" \newline  s \newline "\\end{" tag "}"))


(defn as-latex [tree]
  (join \newline
	(map #(let [[k v] %
		    f (k target-grammar)]
		(if (nil? f)
		  v
		  (f v)))
	     tree)))

(defn latex [tree]
  (str (slurp (resource "before.tex"))
       (as-latex tree)
       (slurp (resource "after.tex"))))

(def target-grammar
     {:h1 #(str "\\section{" % "}")
      :h2 #(str "\\subsection{" % "}")
      :h3 #(str "\\subsubsection{" % "}")
      :code #(between-tags "lstlisting" (as-latex %))
      :quoted #(between-tags "quote" (as-latex %))
      :text #(str % "\\\\")
      :raw #(str %)})