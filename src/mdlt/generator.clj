(ns mdlt.generator
  (:use [clojure.string :only (join)]))

(declare target-grammar)


(def latex-header
     "\\documentclass[]{article}
\\usepackage[utf8]{inputenc}
\\usepackage{listings}
\\usepackage[pdftex]{graphicx}

\\begin{document}
")

(def latex-footer "
\\end{document}")

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
     (str latex-header
       (as-latex tree)
       latex-footer))

(def target-grammar
     {:h1 #(str "\\section{" % "}")
      :h2 #(str "\\subsection{" % "}")
      :h3 #(str "\\subsubsection{" % "}")
      :code #(between-tags "lstlisting" (as-latex %))
      :quoted #(between-tags "quote" (as-latex %))
      :text #(str % "\\\\")
      :raw #(str %)})