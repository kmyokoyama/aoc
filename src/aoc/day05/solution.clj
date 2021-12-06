(ns aoc.day05.solution)

(def input [[[0 9] [5 9]]
            [[8 0] [0 8]]
            [[9 4] [3 4]]
            [[2 2] [2 1]]
            [[7 0] [7 4]]
            [[6 4] [2 0]]
            [[0 9] [2 9]]
            [[3 4] [1 4]]
            [[0 0] [8 8]]
            [[5 5] [8 2]]])

(defn ->line
  [[[x1 y1] [x2 y2]]]
  (if (= x2 x1)
    {:x x1}
    (let [a (/ (- y2 y1) (- x2 x1))
          b (- y1 (* a x1))]
      {:a a :b b})))

(defn horizontal?
  [line]
  (and (:a line) (zero? (:a line))))

(defn vertical?
  [line]
  (some? (:x line)))

(defn x-or-y-constant?
  [line]
  (or (horizontal? line) (vertical? line)))

(defn parallel?
  [{a1 :a x1 :x} {a2 :a x2 :x}]
  (or (and (some? a1) (some? a2) (= a1 a2))
      (and (some? x1) (some? x2))))

(defn cross-point
  [line1 line2]
  (when-not (parallel? line1 line2)
    (if (horizontal? line1)
      [(:x line2) (:b line1)]
      [(:x line1) (:b line2)])))

(defn find-cross-points
  [lines]
  (loop [i 0
         j 1
         result #{}]
    (if (= i (dec (count lines)))
      (->> result (remove nil?) set)
      (if (= j (dec (count lines)))
        (recur (inc i) (-> i inc inc) (conj result (cross-point (nth lines i) (nth lines j))))
        (recur i (inc j) (conj result (cross-point (nth lines i) (nth lines j))))))))

(def relevant-lines (->> input (map ->line) (filter x-or-y-constant?) vec))

(def answer (-> relevant-lines find-cross-points))

(println answer)