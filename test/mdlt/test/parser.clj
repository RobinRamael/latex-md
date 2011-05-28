(ns mdlt.test.parser
    (:use [mdlt.parser])
    (:use [clojure.test]
	  [clojure.contrib.duck-streams :only (read-lines)]
	  [clojure.pprint]))


(deftest test-divide
  (is (= (list "# h" "a\nb" "c" "" "d" "    code" "> quote")
	 (divide (read-lines "ex/test-divide.md")))))


(deftest test-parse
  (let [expected
	'([:h1 "h1"] [:h2 "h2"] [:h3 "h3"]
	    [:text "text same par"] [:text "new par"] [:text ""] [:text "empty line ^"]
	      [:quoted ([:text "quote quote"])]
		[:code ([:raw "code"] [:raw "more code"])]
		  [:quoted ([:code
			     ([:raw "quoted code"])]
			      [:text "quoted text"]
				 [:h2 "quoted h2"])]
		    [:text ".,?#><\\#### #    >"])  ;; "\\" here
						    ;; should be "\"
						    ;; in the file,
						    ;; but will always
						    ;; be printed in
						    ;; the (slime)
						    ;; repl as "\\".
	result (parse (read-lines "ex/test-parse.md"))]
    ;(pprint result)
    ;(pprint expected)
    (is (= result expected))))
