(ns aoc.day09.solution)

(def input [[2 1 9 9 9 4 3 2 1 0]
            [3 9 8 7 8 9 4 9 2 1]
            [9 8 5 6 7 8 9 8 9 2]
            [8 7 6 7 8 9 6 7 8 9]
            [9 8 9 9 9 6 5 6 7 8]])

(def n-rows (atom (count input)))
(def n-cols (atom (-> input first count)))

(defn read-input
  [filepath]
  (->> filepath
       slurp
       clojure.string/split-lines
       (mapv (fn [line] (-> line
                            (clojure.string/split #"")
                            (->> (mapv #(Integer/parseInt %))))))))

(defn adjacents-idx
  [[i j]]
  (remove (fn [[i j]] (or (neg? i) (neg? j) (>= i @n-rows) (>= j @n-cols)))
          [[(dec i) j] [i (inc j)] [(inc i) j] [i (dec j)]]))

(defn adjacents
  [idx input]
  (vec (for [adj-idx (adjacents-idx idx)]
         (get-in input adj-idx))))

(defn low-point?
  [point adjacents]
  (every? #(> % point) adjacents))

(defn -low-points
  [input]
  (for [i (-> input count range)
        j (-> input first count range)
        :let [point (get-in input [i j])
              adjs (adjacents [i j] input)]
        :when (low-point? point adjs)]
    [[i j] point]))

(defn low-points
  [input]
  (map second (-low-points input)))

(defn low-points-idx
  [input]
  (map first (-low-points input)))

(defn basin
  [idx input]
  (loop [to-visit-idx #{idx}
         basin-idx #{idx}]
    (let [current (first to-visit-idx)
          adj-idx (adjacents-idx current)
          in-basin-idx (clojure.set/difference (set (remove #(= 9 (get-in input %)) adj-idx)) basin-idx)
          to-visit-idx* (-> to-visit-idx (disj current) (into in-basin-idx))]
      (if (seq to-visit-idx*)
        (recur to-visit-idx* (into basin-idx in-basin-idx))
        basin-idx))))

(defn update-dim!
  [input]
  (reset! n-rows (count input))
  (reset! n-cols (-> input first count)))

(defn solve-part1
  [input]
  (update-dim! input)
  (->> input low-points (map inc) (reduce +)))

(defn solve-part2
  [input]
  (update-dim! input)
  (->> input low-points-idx (map #(basin % input)) (map count) sort reverse (take 3) (apply *)))

(def answer-part1 (-> "src/aoc/day09/input.txt" read-input solve-part1))

(def answer-part2 (-> "src/aoc/day09/input.txt" read-input solve-part2))