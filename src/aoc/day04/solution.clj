(ns aoc.day04.solution
  (:require [clojure.string :as string]
            [clojure.pprint :refer [pprint]]))

#_(def drawns [7 4 9 5 11 17 23 2 0 14 21 24 10 16 13 6 15 25 12 22 18 20 8 19 3 26 1])

#_(def matrices [[[22 13 17 11 0]
                  [8 2 23 4 24]
                  [21 9 14 16 7]
                  [6 10 3 18 5]
                  [1 12 20 15 19]]

                 [[3 15 0 2 22]
                  [9 18 13 17 5]
                  [19 8 7 25 23]
                  [20 11 10 24 4]
                  [14 21 16 12 6]]

                 [[14 21 17 24 4]
                  [10 16 15 9 19]
                  [18 8 23 26 20]
                  [22 11 13 6 5]
                  [2 0 12 3 7]]])

(def lines (-> "./src/aoc/day04/input.txt" slurp string/split-lines))

(def drawns (-> lines first (string/split #",") (->> (mapv #(Integer. %)))))

(def matrices (->> lines
                   rest
                   (mapcat #(string/split % #" "))
                   (remove string/blank?)
                   (map #(Integer. %))
                   (partition 5)
                   (partition 5)))

(defn matrix->coords
  [matrix]
  (reduce (fn [board [i x]]
            (assoc board x [(quot i 5) (mod i 5)]))
          {}
          (map-indexed vector (flatten matrix))))

(defn mark
  [n board]
  (if-let [coord (get-in board [:coords n])] (update board :marked conj coord) board))

(defn winner?
  [board]
  (when (or (some #(= 5 %) (->> board :marked (map first) frequencies vals))
            (some #(= 5 %) (->> board :marked (map second) frequencies vals)))
    (:id board)))

(defn score
  [last-n board]
  (let [marked (-> board :marked set)]
    (* (->> board
            :coords
            (remove #(-> % second marked))
            (map first)
            (reduce +))
       last-n)))

(defn play-until-first-board-wins
  [matrices]
  (loop [boards (map (fn [[i m]] {:id i :coords (matrix->coords m)}) (map-indexed vector matrices))
         drawns drawns]
    (let [marked-boards (map (partial mark (first drawns)) boards)]
      (if-let [winner-id (some winner? marked-boards)]
        (->> marked-boards (filter #(= winner-id (:id %))) first (score (first drawns)))
        (recur marked-boards (rest drawns))))))

(defn play-until-last-board-wins
  [matrices]
  (loop [boards         (map (fn [[i m]] {:id i :coords (matrix->coords m)}) (map-indexed vector matrices))
         drawns         drawns
         last-winner-id nil]
    (let [last-drawn       (first drawns)
          marked-boards    (map (partial mark last-drawn) boards)
          winner-ids       (->> marked-boards (map winner?) (filter some?) set)
          remaining-drawns (rest drawns)
          remaining-boards (when (seq winner-ids) (remove #(winner-ids (:id %)) marked-boards))]
      (if (seq winner-ids)
        (if (and (seq remaining-boards) (seq remaining-drawns))
          (recur remaining-boards remaining-drawns (last winner-ids))
          (->> marked-boards (filter #(winner-ids (:id %))) first (score last-drawn)))
        (if (seq remaining-drawns)
          (recur marked-boards remaining-drawns last-winner-id)
          (->> marked-boards (filter #(= last-winner-id (:id %))) first (score last-drawn)))))))

(def first-half-answer (play-until-first-board-wins matrices))

(def second-half-answer (play-until-last-board-wins matrices))