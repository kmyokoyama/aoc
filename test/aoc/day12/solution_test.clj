(ns aoc.day12.solution-test
  (:require [clojure.test :refer :all]
            [aoc.day12.solution :as day12]))

(def sample {"start" #{"A" "b"}
             "A" #{"b" "c" "end"}
             "b" #{"A" "d" "end"}
             "c" #{"A"}
             "d" #{"b"}})

(def input {"xx" #{"xh" "cu" "ny" "DR"},
            "wf" #{"cu"},
            "xh" #{"xx" "cu" "DR"},
            "vx" #{"AP" "qc" "LO" "end"},
            "AP" #{"vx"},
            "start" #{"xx" "xh" "DR"},
            "cu" #{"xx" "wf" "xh" "ny" "LO" "DR"},
            "ny" #{"xx" "cu" "LO" "end" "DR"},
            "qc" #{"vx" "DR"},
            "LO" #{"vx" "cu" "ny" "end"},
            "end" #{"ny" "LO"},
            "DR" #{"xx" "xh" "cu" "ny" "qc"}})

(def filepath "src/aoc/day12/input.txt")

(deftest sample-part1-test
  (is (= 10 (day12/solve-part1 sample))))

(deftest sample-part2-test
  (is (= 36 (day12/solve-part2 sample))))

(deftest read-input-test
  (is (= input (day12/read-input filepath))))

(deftest part1-test
  (is (= 4167 (-> filepath day12/read-input day12/solve-part1))))

(deftest part2-test
  "This may take a while"
  (is (= 98441 (-> filepath day12/read-input day12/solve-part2))))