(ns aoc.day12.solution)

(defn read-input
  [filepath]
  (reduce (fn [acc [c1 c2]] (cond-> acc
                                    (not= "start" c2)
                                    (update c1 (fnil conj #{}) c2)
                                    (and (not= "start" c1) (not= "end" c2))
                                    (update c2 (fnil conj #{}) c1)))
          {}
          (->> filepath slurp clojure.string/split-lines
               (map (fn [line] (clojure.string/split line #"-"))))))

(defn small-cave?
  [cave]
  (every? #(Character/isLowerCase ^char %) cave))

(defn end?
  [cave]
  (= "end" cave))

(defn small-already-visited?
  [path cave]
  (and (small-cave? cave) (some #{cave} path)))

(defn small-already-visited-twice?
  [path cave]
  (let [freqs (frequencies path)]
    (or (and (small-cave? cave) (= 2 (get freqs cave 0)))
        (and (small-cave? cave)
             (= 1 (get freqs cave 0))
             (some (fn [[k v]] (and (small-cave? k) (not= k cave) (= v 2))) freqs)))))

(defn paths-from
  [constraint cave path caves]
  (cond
    (end? cave) 1

    (constraint path cave) 0

    :else (reduce (fn [acc next-cave]
                    (+ acc (paths-from constraint next-cave (conj path cave) caves)))
                  0
                  (get caves cave))))

(def solve-part1 (partial paths-from small-already-visited? "start" []))

(def solve-part2 (partial paths-from small-already-visited-twice? "start" []))

(def answer-part1 (-> "src/aoc/day12/input.txt" read-input solve-part1))

(def answer-part2 (-> "src/aoc/day12/input.txt" read-input solve-part2))