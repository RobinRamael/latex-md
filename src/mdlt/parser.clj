(ns mdlt.parser
  (:use [clojure.string :only (split trim trimr join)]))

(declare grammar)

(defn divide-one
  [s lines]
  (cond
   (empty? (first lines))  [s (rest lines)] 
   :else (divide-one (apply str
			    (when (not (empty? (trim s)))
			      (str s \newline))
			    (first lines))
		     (rest lines))))

(defn divide [lines]
  (lazy-seq
   (let [[h t] (divide-one (str) lines)]
     (cons h (when (not (empty? t))
	       (divide t))))))

(defn parse-one [arch s]
  (if (empty? arch)
    nil
    (let [res ((first arch) s)]
      (if (nil? res)
	(parse-one (rest arch) s)
	res))))


(defn parse-block [ls]
  (map #(parse-one grammar %) ls))

(defn parse [ls]
  (parse-block (divide ls)))

;; parts

(defn- fst-char? [s c]
  (= (first (trim s)) c))

(defn raw [s]
  (vector :raw
	  s))

(defn text [s]
  (vector :text
	  (join \space (map trim (split s #"\n")))))

(defn header [s]
  (let [header-level (count (first (split (trim s) #"[^#]")))]
    (when (> header-level 0)
      (vector (keyword (str "h" header-level))
	      (trim (second (split s  #"#+")))))))

(defn sourcecode [s]
  (when (.startsWith s "    ")
    (vector :code
	    (map #(raw (trim %)) (split s #"\n")))))


(defn quoted-text [s]
  (when (fst-char? s \>)
    (vector :quoted
	    (parse (map #(apply str (rest (trimr %)))
			(split s #"\n"))))))

(def grammar (list header
		   sourcecode
		   quoted-text
		   text))