(ns aoc.day02.solution-test
  (:require [clojure.test :refer :all]
            [aoc.day02.solution :as day02]))

(def sample [["forward" 5]
             ["down" 5]
             ["forward" 8]
             ["up" 3]
             ["down" 8]
             ["forward" 2]])

(def filepath "src/aoc/day02/input.txt")

(deftest sample-part1-test
  (is (= 150 (day02/solve-part1 sample))))

(deftest sample-part2-test
  (is (= 900 (day02/solve-part2 sample))))

(deftest part1-test
  (is (= 2091984 (-> filepath day02/read-input day02/solve-part1))))

(deftest part2-test
  (is (= 2086261056 (-> filepath day02/read-input day02/solve-part2))))