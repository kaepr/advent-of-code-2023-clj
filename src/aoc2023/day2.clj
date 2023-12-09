(ns aoc2023.day2)

(def max-red-cubes 12)
(def max-green-cubes 13)
(def max-blue-cubes 14)

(defn valid-configuration [r g b]
  (and (<= r max-red-cubes)
       (<= g max-green-cubes)
       (<= b max-blue-cubes)))

(def s "1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue")

s
