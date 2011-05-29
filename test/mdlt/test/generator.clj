(ns mdlt.test.generator
  (:use [clojure.test])
  (:use [mdlt generator]))

(deftest numbered-headers
  (is (= "\\section*{header}"
	 (as-latex '([:h1 "header"]) {:numbered false})))
  (is (= "\\section{header}"
	 (as-latex '([:h1 "header"]) {:numbered true}))))