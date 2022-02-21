(ns aoc.day10.solution)

(def points {\) 3 \] 57 \} 1197 \> 25137})
(def scores {\) 1 \] 2 \} 3 \> 4})

(defn read-input
  [filepath]
  (-> filepath slurp clojure.string/split-lines))

(defn match-closing?
  [opening closing]
  (boolean (some (fn [[o c]] (and (= o opening) (= c closing))) (map vector #{\( \[ \{ \<} #{\) \] \} \>}))))

(defn parse-chunk
  [chunk]
  (loop [remaining (seq chunk)
         parsed []]
    (if-let [next-char (first remaining)]
      (cond
        (#{\( \[ \{ \<} next-char) (recur (rest remaining) (conj parsed next-char))
        (#{\) \] \} \>} next-char) (if (match-closing? (peek parsed) next-char)
                                     (recur (rest remaining) (pop parsed))
                                     {:corrupted next-char})
        :else {:invalid next-char})
      (if (seq parsed)
        {:incomplete (clojure.string/join parsed)}
        {:ok chunk}))))

(defn solve-part1
  [input]
  (reduce (fn [acc [char times]] (+ acc (* (points char) times)))
          0
          (reduce (fn [acc chunk] (let [[status ret] (first (parse-chunk chunk))]
                                    (condp = status
                                      :ok acc
                                      :incomplete acc
                                      :corrupted (update acc ret inc))))
                  (zipmap [\) \] \} \>] (repeat 0))
                  input)))

(defn complete
  [parsed]
  (reduce (fn [acc to-close] (conj acc ({\( \) \[ \] \{ \} \< \>} to-close))) [] (reverse parsed)))

(defn score
  [to-complete]
  (reduce (fn [acc next-char] (+ (* 5 acc) (scores next-char))) 0 (complete to-complete)))

(defn middle
  [scores]
  (-> scores sort (nth (/ (dec (count scores)) 2))))

(defn solve-part2
  [input]
  (middle (reduce (fn [acc chunk] (let [[status ret] (first (parse-chunk chunk))]
                                    (condp = status
                                      :ok acc
                                      :incomplete (conj acc (score ret))
                                      :corrupted acc)))
                  []
                  input)))

(def answer-part1 (-> "src/aoc/day10/input.txt" read-input solve-part1))

(def answer-part2 (-> "src/aoc/day10/input.txt" read-input solve-part2))