(ns aoc.day03.solution-test
  (:require [clojure.test :refer :all]
            [aoc.day03.solution :as day03]))

(def sample ["00100"
             "11110"
             "10110"
             "10111"
             "10101"
             "01111"
             "00111"
             "11100"
             "10000"
             "11001"
             "00010"
             "01010"])

(def filepath "src/aoc/day03/input.txt")

(deftest sample-part1-test
  (is (= 198 (day03/solve-part1 sample))))

(deftest sample-part2-test
  (is (= 230 (day03/solve-part2 sample))))

(deftest part1-test
  (is (= 3320834 (-> filepath day03/read-input day03/solve-part1))))

(deftest part2-test
  (is (= 4481199 (-> filepath day03/read-input day03/solve-part2))))