(ns aoc.day02.solution)

(def input [["forward" 5]
            ["down" 5]
            ["forward" 8]
            ["up" 3]
            ["down" 8]
            ["forward" 2]])

(defn read-input
  [filepath]
  (->> filepath slurp clojure.string/split-lines
       (map #(clojure.string/split % #" "))
       (map (fn [[dir x]] [dir (Integer/parseInt x)]))))

(defn solve-part1
  [input]
  (apply * ((juxt :hor :ver) (reduce (fn [m [dir x]]
                                       (case dir
                                         "forward" (update m :hor + x)
                                         "down" (update m :ver + x)
                                         "up" (update m :ver - x)))
                                     {:hor 0 :ver 0}
                                     input))))

(defn solve-part2
  [input]
  (apply * ((juxt :hor :ver) (reduce (fn [{:keys [aim] :as m} [dir x]]
                                       (case dir
                                         "forward" (-> m
                                                       (update :hor + x)
                                                       (update :ver + (* x aim)))
                                         "down" (update m :aim + x)
                                         "up" (update m :aim - x)))
                                     {:hor 0 :ver 0 :aim 0}
                                     input))))


(def answer-part1 (-> "src/aoc/day02/input.txt" read-input solve-part1))

(def answer-part2 (-> "src/aoc/day02/input.txt" read-input solve-part2))