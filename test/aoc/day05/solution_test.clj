(ns aoc.day05.solution-test
  (:require [clojure.test :refer :all]
            [aoc.day05.solution :as day05]
            [mockfn.macros :refer [providing]]))

(def sample [[[0 9] [5 9]]
             [[8 0] [0 8]]
             [[9 4] [3 4]]
             [[2 2] [2 1]]
             [[7 0] [7 4]]
             [[6 4] [2 0]]
             [[0 9] [2 9]]
             [[3 4] [1 4]]
             [[0 0] [8 8]]
             [[5 5] [8 2]]])

(def filepath "mock-input.txt")

(def input "0,9 -> 5,9\n8,0 -> 0,8\n9,4 -> 3,4\n2,2 -> 2,1\n7,0 -> 7,4\n6,4 -> 2,0\n0,9 -> 2,9\n3,4 -> 1,4\n0,0 -> 8,8\n5,5 -> 8,2")

(deftest read-input-test
  (providing [(slurp filepath) input]
    (are [expected s]
      (= expected (day05/read-input s))
      sample filepath)))

(deftest vertical?-test
  (are [expected point]
    (= expected (day05/vertical? point))
    true [[0 0] [0 9]]
    true [[-10 -20] [-10 30]]
    false [[0 0] [10 0]]
    false [[-10 10] [30 10]]
    false [[0 0] [0 0]]
    false [[0 0] [3 3]]))

(deftest horizontal?-test
  (are [expected point]
    (= expected (day05/horizontal? point))
    false [[0 0] [0 9]]
    false [[-10 -20] [-10 30]]
    true [[0 0] [10 0]]
    true [[-10 10] [30 10]]
    false [[0 0] [0 0]]
    false [[0 0] [3 3]]))

(deftest points-test
  (are [expected segment]
    (= expected (day05/points segment))
    [[0 0] [1 0] [2 0] [3 0] [4 0] [5 0]] [[0 0] [5 0]]
    [[0 0] [0 1] [0 2] [0 3] [0 4] [0 5]] [[0 0] [0 5]]
    [[-5 0] [-4 0] [-3 0] [-2 0] [-1 0] [0 0]] [[0 0] [-5 0]]
    [[0 -5] [0 -4] [0 -3] [0 -2] [0 -1] [0 0]] [[0 0] [0 -5]]
    [[-3 -3] [-2 -2] [-1 -1] [0 0] [1 1] [2 2] [3 3]] [[-3 -3] [3 3]]
    [[3 -3] [2 -2] [1 -1] [0 0] [-1 1] [-2 2] [-3 3]] [[3 -3] [-3 3]]
    [[3 3] [2 2] [1 1] [0 0] [-1 -1] [-2 -2] [-3 -3]] [[3 3] [-3 -3]]
    [[-3 3] [-2 2] [-1 1] [0 0] [1 -1] [2 -2] [3 -3]] [[-3 3] [3 -3]]))

(deftest overlap-test
  (are [expected segment1 segment2]
    (= expected (day05/overlap segment1 segment2))
    #{[2 2]} [[0 2] [4 2]] [[2 0] [2 5]]
    #{[2 2]} [[0 0] [3 3]] [[4 0] [0 4]]))

(deftest find-overlaps-test
  (are [expected input]
    (= expected (day05/find-overlaps input))
    #{[7 1] [2 2] [7 4] [3 4] [7 3] [1 9] [5 3] [2 9] [0 9] [6 4] [5 5] [4 4]} sample))