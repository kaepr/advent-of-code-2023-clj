(ns aoc2023.day2
  (:require [clojure.test :as t]
            [clojure.string :as str]))

(def max-red-cubes 12)
(def max-green-cubes 13)
(def max-blue-cubes 14)

(defn valid-configuration [{r :r, b :b, g :g, :or {r 0, b 0, g 0}}]
  (and (<= r max-red-cubes)
       (<= g max-green-cubes)
       (<= b max-blue-cubes)))

(def example-input
 "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green")

(defn parse-number [s]
  (Integer/parseInt (re-find #"\d+" s)))

(defn parse-segment [s]
  (let [colors (str/split (str/trim s) #",")
        create-color-map (fn [c]
                           (cond
                             (str/includes? c "red") {:r (parse-number c)}
                             (str/includes? c "blue") {:b (parse-number c)}
                             (str/includes? c "green") {:g (parse-number c)}))]
    (reduce 
      (fn [acc val] (merge acc (create-color-map (str/trim val)))) 
      {} 
      colors)))

(defn parse-line [s]
  (let [splitted (str/split s #":")
        game-id-split (nth splitted 0)
        colors-split (nth splitted 1)
        colors (map parse-segment (str/split colors-split #";"))]
   {:id (parse-number game-id-split)
    :colors colors}))

(defn sol-1 [input]
  (->> (str/split-lines input) 
    (map parse-line)
    (filter (fn [{colors :colors}]
              (every? valid-configuration colors)))
    (map (fn [c] (:id c)))
    (reduce + 0)))

(defn make-max-color-map [colors]
  (reduce 
    (fn [acc v] (merge-with max acc v))
    {:r 0 :b 0 :g 0} 
    colors))

(defn sol-2 [input]
  (->> (str/split-lines input) 
    (map parse-line)
    (map (fn [c] (:colors c)))
    (map make-max-color-map)
    (map (fn [c] (* (:r c) (:g c) (:b c))))
    (reduce + 0)))

(t/deftest example-test
  (t/is (= 8 (sol-1 example-input)))
  (t/is (= 2286 (sol-2 example-input))))

(t/run-tests)

