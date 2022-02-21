(ns aoc.day10.solution-test
  (:require [clojure.test :refer :all]
            [aoc.day10.solution :as day10]))

(def sample [[\[ \( \{ \( \< \( \( \) \) \[ \] \> \[ \[ \{ \[ \] \{ \< \( \) \< \> \>]
             [\[ \( \( \) \[ \< \> \] \) \] \( \{ \[ \< \{ \< \< \[ \] \> \> \(]
             [\{ \( \[ \( \< \{ \} \[ \< \> \[ \] \} \> \{ \[ \] \{ \[ \( \< \( \) \>]
             [\( \( \( \( \{ \< \> \} \< \{ \< \{ \< \> \} \{ \[ \] \{ \[ \] \{ \}]
             [\[ \[ \< \[ \( \[ \] \) \) \< \( \[ \[ \{ \} \[ \[ \( \) \] \] \]]
             [\[ \{ \[ \{ \( \{ \} \] \{ \} \} \( \[ \{ \[ \{ \{ \{ \} \} \( \[ \]]
             [\{ \< \[ \[ \] \] \> \} \< \{ \[ \{ \[ \{ \[ \] \{ \( \) \[ \[ \[ \]]
             [\[ \< \( \< \( \< \( \< \{ \} \) \) \> \< \( \[ \] \( \[ \] \( \)]
             [\< \{ \( \[ \( \[ \[ \( \< \> \( \) \) \{ \} \] \> \( \< \< \{ \{]
             [\< \{ \( \[ \{ \{ \} \} \[ \< \[ \[ \[ \< \> \{ \} \] \] \] \> \[ \] \]]])

(def filepath "src/aoc/day10/input.txt")

(deftest sample-part1-test
  (is (= 26397 (day10/solve-part1 sample))))

(deftest sample-part2-test
  (is (= 288957 (day10/solve-part2 sample))))

(deftest part1-test
  (is (= 319329 (-> filepath day10/read-input day10/solve-part1))))

(deftest part2-test
  (= 3515583998 (-> filepath day10/read-input day10/solve-part2)))