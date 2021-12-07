(ns aoc.day05.solution
  (:require [clojure.string :as string]
            [clojure.set :as set]))

(defn ->int
  [s]
  (Integer/parseInt s))

(defn read-input
  [filepath]
  (->> filepath
       slurp
       string/split-lines
       (map (fn [line] (string/split line #" -> ")))
       (map (fn [[p1 p2]] [(string/split p1 #",") (string/split p2 #",")]))
       (map (fn [ps] (mapv (fn [[x y]] [(->int x) (->int y)]) ps)))
       vec))

(defn vertical?
  [[[x1 y1] [x2 y2]]]
  (and (= x1 x2) (not= y1 y2)))

(defn horizontal?
  [[[x1 y1] [x2 y2]]]
  (and (= y1 y2) (not= x1 x2)))

(defn points
  [[[x1 y1] [x2 y2] :as segment]]
  (cond (horizontal? segment) (map vector (range (min x1 x2) (inc (max x1 x2))) (repeat y1))
        (vertical? segment) (map vector (repeat x1) (range (min y1 y2) (inc (max y1 y2))))
        :else (map vector
                   (if (< x1 x2) (range x1 (inc x2)) (range x1 (dec x2) -1))
                   (if (< y1 y2) (range y1 (inc y2)) (range y1 (dec y2) -1)))))

(defn overlap
  [segment1 segment2]
  (clojure.set/intersection (-> segment1 points set) (-> segment2 points set)))

(defn find-overlaps
  [segments]
  (loop [i      0
         j      1
         result #{}]
    (if (= i (dec (count segments)))
      (->> result (remove nil?) set)
      (if (= j (dec (count segments)))
        (recur (inc i) (-> i inc inc) (set/union result (overlap (nth segments i) (nth segments j))))
        (recur i (inc j) (set/union result (overlap (nth segments i) (nth segments j))))))))

#_(def input (read-input "src/aoc/day05/input.txt"))

;; Uncomment to solve the exercise. It may take a while.
#_(def overlapping-points (->> input
                               ;;(filter #(or (horizontal? %) (vertical? %))) ;; Uncomment for first half.
                               find-overlaps))
                             ;;count))