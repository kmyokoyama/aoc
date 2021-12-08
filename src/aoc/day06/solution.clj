(ns aoc.day06.solution)

(def initial-state (-> "src/aoc/day06/input.txt"
                       slurp
                       (clojure.string/split #",|\n")
                       (->> (map #(Integer. %))
                            frequencies
                            (merge (zipmap (range 0 9) (repeat 0)))
                            (sort-by first)
                            (map second)
                            (into []))))

;; These solutions are equivalent.

(def total-fishes-loop (loop [state initial-state i 256]
                         (if (zero? i)
                           (reduce + state)
                           (recur (-> (conj (subvec state 1) 0) (update 6 + (nth state 0)) (update 8 + (nth state 0))) (dec i)))))

(def total-fishes-reduce (reduce + (reduce (fn [old-state _day]
                                             (-> (conj (subvec old-state 1) 0) (update 6 + (nth old-state 0)) (update 8 + (nth old-state 0))))
                                           initial-state
                                           (range 256))))

;; Naive approach (it will take forever):

#_(def initial-state-naive (-> "src/aoc/day06/input.txt"
                               slurp
                               (clojure.string/split #",|\n")
                               (->> (map #(Integer. %)))
                               vec))

#_(def total-fishes-naive (count (reduce (fn [old-state day]
                                           (let [[old-xs new-xs] (reduce (fn [[old-xs new-xs] x]
                                                                           [(conj old-xs (if (= x 0) 6 (dec x))) (if (= x 0) (conj new-xs 8) new-xs)])
                                                                         [[] []]
                                                                         old-state)]
                                             (into old-xs new-xs)))
                                         initial-state
                                         (range 256))))