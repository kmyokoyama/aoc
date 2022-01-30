(ns aoc.day01.solution-test
  (:require [clojure.test :refer :all]
            [aoc.day01.solution :as day01]))

(def sample [199
             200
             208
             210
             200
             207
             240
             269
             260
             263])

(def filepath "src/aoc/day01/input.txt")

(deftest sample-part1-test
  (is (= 7 (day01/solve-part1 sample))))

(deftest sample-part2-test
  (is (= 5 (day01/solve-part2 sample))))

(deftest part1-test
  (is (= 1466 (-> filepath day01/read-input day01/solve-part1))))

(deftest part2-test
  (is (= 1491 (-> filepath day01/read-input day01/solve-part2))))