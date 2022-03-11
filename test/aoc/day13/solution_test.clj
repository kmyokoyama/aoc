(ns aoc.day13.solution-test
  (:require [clojure.test :refer :all]
            [aoc.day13.solution :as day13]))

(defn long-str [& strings] (str (clojure.string/join "\n" strings) "\n"))

(def sample "6,10\n0,14\n9,10\n0,3\n10,4\n4,11\n6,0\n6,12\n4,1\n0,13\n10,12\n3,4\n3,0\n8,4\n1,10\n2,14\n8,10\n9,0\n\nfold along y=7\nfold along x=5")

(def sample-output-part2 (long-str "XXXXX"
                                   "X   X"
                                   "X   X"
                                   "X   X"
                                   "XXXXX"
                                   "     "
                                   "     "))

(def output-part2 (long-str "X  X XXXX  XX  XXX  XXXX X  X XXX  XXX  "
                            "X  X X    X  X X  X    X X X  X  X X  X "
                            "XXXX XXX  X    X  X   X  XX   X  X X  X "
                            "X  X X    X    XXX   X   X X  XXX  XXX  "
                            "X  X X    X  X X X  X    X X  X    X X  "
                            "X  X XXXX  XX  X  X XXXX X  X X    X  X "))

(def input {:marks [[6 10]
                    [0 14]
                    [9 10]
                    [0 3]
                    [10 4]
                    [4 11]
                    [6 0]
                    [6 12]
                    [4 1]
                    [0 13]
                    [10 12]
                    [3 4]
                    [3 0]
                    [8 4]
                    [1 10]
                    [2 14]
                    [8 10]
                    [9 0]],
            :nrows 15,
            :ncols 11,
            :folds [[:y 7] [:x 5]]})

(def filepath "src/aoc/day13/input.txt")

(deftest sample-part1-test
  (is (= 17 (day13/solve-part1 input))))

(deftest sample-part2-testt
  (is (= sample-output-part2 (with-out-str (day13/solve-part2 input)))))

(deftest read-input-test
  (is (= input (with-redefs [slurp (constantly sample)] (day13/read-input filepath)))))

(deftest part1-test
  (is (= 759 (-> filepath day13/read-input day13/solve-part1))))

(deftest part2-test
  (is (= output-part2 (with-out-str (-> filepath day13/read-input day13/solve-part2)))))