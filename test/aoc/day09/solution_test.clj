(ns aoc.day09.solution-test
  (:require [clojure.test :refer :all]
            [aoc.day09.solution :as day09]))

(def sample [[2 1 9 9 9 4 3 2 1 0]
             [3 9 8 7 8 9 4 9 2 1]
             [9 8 5 6 7 8 9 8 9 2]
             [8 7 6 7 8 9 6 7 8 9]
             [9 8 9 9 9 6 5 6 7 8]])

(def filepath "src/aoc/day09/input.txt")

(deftest sample-part1-test
  (is (= 15 (day09/solve-part1 sample))))

(deftest sample-part2-test
  (is (= 1134 (day09/solve-part2 sample))))

(deftest part1-test
  (is (= 496 (-> filepath day09/read-input day09/solve-part1))))

(deftest part2-test
  (is (= 902880 (-> filepath day09/read-input day09/solve-part2))))