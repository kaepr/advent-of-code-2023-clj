(ns aoc2023.day1
  (:require [clojure.string :as str]))

(def sample
  "1abc2
pqr3stu8vwx
a1b2c3d4e5f
treb7uchet")

(defn digit [c]
  (Character/digit c 10))

(defn sum [v]
  (reduce + 0 v))

(defn solution-1 [input]
  (->> input
       (str/split-lines)
       (filter not-empty)
       (map #(str/replace % #"[^\d]" ""))
       (filter not-empty)
       (map #(vector (digit (first %)) (digit (last %))))
       (map #(+ (* 10 (first %)) (last %)))
       (sum)))

(solution-1 sample)

(def sample-2
  "
two1nine
eightwothree
abcone2threexyz
xtwone3four
4nineeightseven2
zoneight234
7pqrstsixteen
 ")

(def char-to-num-map
  {:one "1"
   :two "2"
   :three "3"
   :four "4"
   :five "5"
   :six "6"
   :seven "7"
   :eight "8"
   :nine "9"})

(def numbers ["1" "2" "3" "4" "5" "6" "7" "8" "9"]) 
(def digits ["one" "two" "three" "four" "five" "six" "seven" "eight" "nine"])
(def digits-reversed (map #(str/reverse %) digits))

(defn find-first [f coll]
  (first (filter f coll)))

(defn get-digit [s reversed?]
  (let [s (if reversed? (str/reverse s) s)
        digits (if reversed? digits-reversed digits)]
    (reduce 
      (fn [acc cur]
        (let [s (str acc cur)
              number-found (find-first #(.contains s %) numbers)]
          (if number-found
            (reduced number-found)
            (let [digit-found (find-first #(.contains s %) digits)]
              (if digit-found
                (reduced ((keyword (if reversed? 
                                     (str/reverse digit-found)
                                     digit-found)) char-to-num-map))
               s)))))
      ""
      s)))

(defn has-digit [s]
  (.contains  "1234566789" (get-digit s false)))

(defn solution-2 [input]
  (->> 
    input
       (str/split-lines)
       (filter not-empty)
       (filter has-digit)
       (filter not-empty)
       (map #(vector (get-digit % false) (get-digit % true)))
       (map #(vector (read-string (first %)) (read-string (last %))))
       (map #(+ (* 10 (first %)) (last %)))
       (sum)))

(solution-2 sample-2)


