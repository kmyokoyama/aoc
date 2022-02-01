(ns aoc.day03.solution)

(def input ["00100"
            "11110"
            "10110"
            "10111"
            "10101"
            "01111"
            "00111"
            "11100"
            "10000"
            "11001"
            "00010"
            "01010"])

(defn read-input
  [filepath]
  (-> filepath slurp clojure.string/split-lines))

(defn solve-part1
  [input]
  (->> input
       (map #(clojure.string/split % #""))
       (apply map vector)
       (map frequencies)
       (map #(vector (key (apply max-key val %)) (key (apply min-key val %))))
       (apply map vector)
       (map clojure.string/join)
       (map #(Long/parseLong % 2))
       (apply *)))

(defn solve-part2
  [input]
  (loop [oxy-input input
         co2-input input
         k 0]
    (let [oxy-freq-k (nth (->> oxy-input
                               (map #(clojure.string/split % #""))
                               (apply map vector)
                               (map frequencies)) k)
          co2-freq-k (nth (->> co2-input
                               (map #(clojure.string/split % #""))
                               (apply map vector)
                               (map frequencies)) k)
          most (if (= (get oxy-freq-k "0") (get oxy-freq-k "1")) "1" (key (apply max-key val oxy-freq-k)))
          least (if (= (get co2-freq-k "0") (get co2-freq-k "1")) "0" (key (apply min-key val co2-freq-k)))
          oxy-input* (filter #(= most (subs % k (inc k))) oxy-input)
          co2-input* (filter #(= least (subs % k (inc k))) co2-input)]
      (if (= 1 (count oxy-input*) (count co2-input*))
        (->> [oxy-input* co2-input*] (map clojure.string/join) (map #(Long/parseLong % 2)) (apply *))
        (recur oxy-input* co2-input* (inc k))))))

(def answer-part1 (-> "src/aoc/day03/input.txt" read-input solve-part1))

(def answer-part2 (-> "src/aoc/day03/input.txt" read-input solve-part2))