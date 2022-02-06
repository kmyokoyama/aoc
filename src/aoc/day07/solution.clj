(ns aoc.day07.solution)

(def input [16 1 2 0 4 2 7 1 2 14])

(defn read-input
  [filepath]
  (-> filepath slurp (clojure.string/split #",") (->> (map #(Integer/parseInt %)))))

(defn median
  [coll]
  (let [sorted (sort coll)
        n (count coll)]
    (if (even? n)
      (/ (+ (nth sorted (dec (/ n 2))) (nth sorted (/ n 2))) 2)
      (nth sorted (dec (/ (inc n) 2))))))

(defn abs
  [a b]
  (if (<= a b) (- b a) (- a b)))

(defn triangular
  [a b]
  (reduce (fn [acc n] (+ acc n)) 0 (range (abs a b) 0 -1)))

(defn sum-dists
  [dist t coll]
  (reduce (fn [acc x] (+ acc (dist t x))) 0 coll))

(defn solve-part1
  [input]
  (sum-dists abs (median input) input))

(defn solve-part2
  [input]
  (let [sorted (sort input)]
    (loop [curr-pos (int (/ (apply max sorted) 2))
           visited #{0 (apply max sorted)}]
      (let [cost (sum-dists triangular curr-pos sorted)
            probe-l (sum-dists triangular (dec curr-pos) sorted)
            probe-r (sum-dists triangular (inc curr-pos) sorted)]
        (cond
          (and (<= cost probe-l) (<= cost probe-r))
          cost

          (< probe-l cost)
          (recur (int (/ (+ curr-pos (apply max (filter #(< % curr-pos) visited))) 2)) (conj visited curr-pos))

          (< probe-r cost)
          (recur (int (/ (+ curr-pos (apply min (filter #(> % curr-pos) visited))) 2)) (conj visited curr-pos)))))))

(def answer-part1 (-> "src/aoc/day07/input.txt" read-input solve-part1))

(def answer-part2 (-> "src/aoc/day07/input.txt" read-input solve-part2))