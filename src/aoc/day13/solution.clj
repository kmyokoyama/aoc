(ns aoc.day13.solution)

(defn read-input
  [filepath]
  (let [lines (->> filepath slurp clojure.string/split-lines)
        marks (->> lines
                   (take-while #(re-matches #"^(\d+),(\d+)$" %))
                   (map #(clojure.string/split % #","))
                   (mapv #(vector (Integer/parseInt ^String (first %)) (Integer/parseInt ^String (second %)))))
        folds (->> lines
                   (filter #(re-matches #"^fold along.*" %))
                   (map #(->> % (re-matches #"fold along (.)=(\d+)") rest))
                   (mapv #(vector (-> % first keyword) (Integer/parseInt (second %)))))]
    {:marks marks
     :nrows (->> marks (map second) (apply max) inc)
     :ncols (->> marks (map first) (apply max) inc)
     :folds folds}))

(defn project-left
  [mx idx-l idx-r]
  (mapv (fn [row] (assoc row idx-l (or (row idx-l) (row idx-r)))) mx))

(defn project-up
  [mx idx-up idx-bottom]
  (assoc mx idx-up (mapv (fn [x y] (or x y)) (mx idx-up) (mx idx-bottom))))

(defn remove-bottom
  [mx at]
  (subvec mx 0 at))

(defn remove-right
  [mx at]
  (mapv (fn [row] (subvec row 0 at)) mx))

(defn fold-up
  [mx at]
  (let [nrows (count mx)
        idx-pairs (map vector (range (dec at) -1 -1) (range (inc at) nrows))]
    (loop [mx* mx
           idx-pairs* idx-pairs]
      (if (seq idx-pairs*)
        (let [[idx-up idx-bottom] (first idx-pairs*)]
          (recur (project-up mx* idx-up idx-bottom) (rest idx-pairs*)))
        (remove-bottom mx* at)))))

(defn fold-left
  [mx at]
  (let [nrows (-> mx first count)
        idx-pairs (map vector (range (dec at) -1 -1) (range (inc at) nrows))]
    (loop [mx* mx
           idx-pairs* idx-pairs]
      (if (seq idx-pairs*)
        (let [[idx-left idx-right] (first idx-pairs*)]
          (recur (project-left mx* idx-left idx-right) (rest idx-pairs*)))
        (remove-right mx* at)))))

(defn count-visibles
  [mx]
  (reduce (fn [acc row] (+ acc (->> row (filter identity) count))) 0 mx))

(defn fold
  [mx folds]
  (loop [mx* mx
         folds* folds]
    (if (seq folds*)
      (case (ffirst folds*)
        :y (recur (fold-up mx* (-> folds* first second)) (rest folds*))
        :x (recur (fold-left mx* (-> folds* first second)) (rest folds*)))
      mx*)))

(defn print-code
  [mx]
  (doseq [row mx]
    (println (->> row (map (fn [e] (if e "X" " "))) clojure.string/join))))

(defn build-paper
  [input]
  (loop [mx (into [] (repeatedly (:nrows input) #(vec (repeat (:ncols input) false))))
         marks (:marks input)]
    (if-let [mark (-> marks first)]
      (recur (assoc-in mx (reverse mark) true) (rest marks)) ;; marks is (x,y) while indexes are [row col], thus reverse.
      mx)))

(defn solve-part1
  [input]
  (-> input build-paper (fold (->> input :folds (take 1))) count-visibles))

(defn solve-part2
  [input]
  (-> input build-paper (fold (:folds input)) print-code))

(def answer-part1 (-> "src/aoc/day13/input.txt" read-input solve-part1))

(solve-part2 (-> "src/aoc/day13/input.txt" read-input))