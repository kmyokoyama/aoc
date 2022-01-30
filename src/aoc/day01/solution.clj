(ns aoc.day01.solution)

(def input [199
            200
            208
            210
            200
            207
            240
            269
            260
            263])

(defn read-input
  [filepath]
  (->> filepath slurp clojure.string/split-lines (map #(Integer/parseInt %))))

(defn solve-part1
  [input]
  (:total (reduce (fn [{:keys [last total]} current] {:last current :total (if (> current last) (inc total) total)})
                  {:last (first input) :total 0}
                  (rest input))))

(defn group-window
  [input]
  (->> input (partition 3 1) (map #(reduce + %))))

(defn solve-part2
  [input]
  (-> input group-window solve-part1))

(def answer-part1 (-> "src/aoc/day01/input.txt" read-input solve-part1))

(def answer-part2 (-> "src/aoc/day01/input.txt" read-input solve-part2))