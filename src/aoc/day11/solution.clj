(ns aoc.day11.solution
  (:require [clojure.set :as set]))

(defn read-input
  [filepath]
  (-> filepath slurp (clojure.string/split #"\n")
      (->> (mapv (fn [r] (-> r (clojure.string/split #"") (->> (mapv #(Integer/parseInt %)))))))))

(def indices (for [i (range 10) j (range 10)] [i j]))

(defn idx-adjacents
  [[i j]]
  (for [iadj [(dec i) i (inc i)]
        jadj [(dec j) j (inc j)]
        :when (and (not= [iadj jadj] [i j])
                   (<= 0 iadj 9)
                   (<= 0 jadj 9))]
    [iadj jadj]))

(defn val-adjacents
  [m idx]
  (for [idx-adj (idx-adjacents idx)]
    (get-in m idx-adj)))

(defn inc-adjacents
  [m idx]
  (reduce (fn [acc adj] (update-in acc adj inc)) (mapv vec m) (idx-adjacents idx)))

(defn idx-above-9
  [m]
  (->> (map vector (map #(get-in m %) indices) indices)
       (filter #(> (first %) 9))
       (map second)
       set))

(defn adjacents-above-9
  [m idx]
  (->> (map vector (val-adjacents m idx) (idx-adjacents idx))
       (filter #(> (first %) 9))
       (map second)
       set))

(defn all-zero?
  [m]
  (every? zero? (flatten m)))

(defn step
  [m]
  (loop [m* (mapv (fn [r] (mapv inc r)) m)
         o  #{}
         n  (idx-above-9 m*)]
    (if (seq n)
      (let [idx (first n)]
        (let [m** (inc-adjacents m* idx)
              o*  (conj o idx)
              n*  (set/difference (set/union n (adjacents-above-9 m** idx)) o*)]
          (recur m** o* n*)))
      [(reduce (fn [acc idx] (assoc-in acc idx 0)) m* o) (count o)])))

(defn solve-part1
  [m]
  (second (reduce (fn [[m n] _] (let [[m* n*] (step m)] [m* (+ n n*)])) [m 0] (range 100))))

(defn solve-part2
  [m]
  (loop [n 0
         m* m]
    (if (all-zero? m*)
      n
      (recur (inc n) (first (step m*))))))

(def answer-part1
  (-> "src/aoc/day11/input.txt" read-input solve-part1))

(def answer-part2
  (-> "src/aoc/day11/input.txt" read-input solve-part2))

;; Helper

(defn prm
  [m]
  (doseq [row m] (apply println (map (partial format "%3d") row))))