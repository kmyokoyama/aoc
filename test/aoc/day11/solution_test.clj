(ns aoc.day11.solution-test
  (:require [clojure.test :refer :all]
            [aoc.day11.solution :as day11]))

(def sample [[5 4 8 3 1 4 3 2 2 3]
             [2 7 4 5 8 5 4 7 1 1]
             [5 2 6 4 5 5 6 1 7 3]
             [6 1 4 1 3 3 6 1 4 6]
             [6 3 5 7 3 8 5 4 7 8]
             [4 1 6 7 5 2 4 6 4 5]
             [2 1 7 6 8 4 1 7 2 1]
             [6 8 8 2 8 8 1 1 3 4]
             [4 8 4 6 8 4 8 5 5 4]
             [5 2 8 3 7 5 1 5 2 6]])

(def input [[4 7 6 4 7 4 5 7 8 4]
            [4 6 4 3 4 5 7 1 7 6]
            [8 3 2 2 6 2 8 4 7 7]
            [7 6 1 7 1 5 2 5 4 6]
            [6 1 3 7 5 1 8 1 6 5]
            [1 5 5 6 7 2 3 1 7 6]
            [2 1 8 7 8 6 1 8 8 6]
            [2 5 5 3 4 2 2 6 2 5]
            [4 8 1 7 5 8 4 6 3 8]
            [3 7 5 4 2 8 5 6 6 2]])

(def filepath "src/aoc/day11/input.txt")

(deftest sample-part1-test
  (is (= 1656 (day11/solve-part1 sample))))

(deftest sample-part2-test
  (is (= 195 (day11/solve-part2 sample))))

(deftest read-input-test
  (is (= input (day11/read-input filepath))))

(deftest part1-test
  (is (= 1588 (-> filepath day11/read-input day11/solve-part1))))

(deftest part2-test
  (= 517 (-> filepath day11/read-input day11/solve-part2)))