(ns aoc.day07.solution-test
  (:require [clojure.test :refer :all]
            [aoc.day07.solution :as day07]))

(def sample [16 1 2 0 4 2 7 1 2 14])

(def filepath "src/aoc/day07/input.txt")

(deftest sample-part1-test
  (is (= 37 (day07/solve-part1 sample))))

(deftest sample-part2-test
  (is (= 168 (day07/solve-part2 sample))))

(deftest part1-test
  (is (= 340056 (-> filepath day07/read-input day07/solve-part1))))

(deftest part2-test
  (is (= 96592275 (-> filepath day07/read-input day07/solve-part2))))