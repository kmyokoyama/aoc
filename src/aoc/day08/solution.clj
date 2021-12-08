(ns aoc.day08.solution
  (:require [clojure.set :as set]))

#_(def input "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe\nedbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc\nfgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg\nfbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb\naecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea\nfgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb\ndbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe\nbdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef\negadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb\ngcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce")
#_(def patterns (-> input (clojure.string/split #"\n| \| | ") (->> (partition 14) (map #(split-at 10 %)))))

(def patterns (-> "src/aoc/day08/input.txt" slurp (clojure.string/split #"\n| \| | ") (->> (partition 14) (map #(split-at 10 %)))))

;; Part 1

(def answer-part1 (->> patterns (mapcat second) (filter #(#{2 3 4 7} (count %))) count))

;; Part 2

(defn one?
  [pattern]
  (= 2 (count pattern)))

(defn four?
  [pattern]
  (= 4 (count pattern)))

(defn seven?
  [pattern]
  (= 3 (count pattern)))

(defn eight?
  [pattern]
  (= 7 (count pattern)))

(defn two?
  [four pattern]
  (and (= 5 (count pattern))
       (= 2 (count (set/intersection pattern four)))))

(defn five?
  [two pattern]
  (and (= 5 (count pattern))
       (= 3 (count (set/intersection pattern two)))))

(defn three?
  [two pattern]
  (and (= 5 (count pattern))
       (= 4 (count (set/intersection pattern two)))))

(defn nine?
  [three four pattern]
  (and (= 6 (count pattern))
       (= pattern (set/union three four))))

(defn six?
  [eight one five pattern]
  (and (= 6 (count pattern))
       (= pattern (set/union five (set/difference eight one)))))

(defn zero?
  [two four five pattern]
  (and (= 6 (count pattern))
       (empty? (set/intersection pattern (set/intersection two four five)))))

(defn decode
  [patterns output]
  (let [patterns-set   (map set patterns)
        one            (->> patterns-set (filter one?) first)
        four           (->> patterns-set (filter four?) first)
        seven          (->> patterns-set (filter seven?) first)
        eight          (->> patterns-set (filter eight?) first)
        two            (->> patterns-set (filter (partial two? four)) first)
        five           (->> patterns-set (filter (partial five? two)) first)
        three          (->> patterns-set (filter (partial three? two)) first)
        nine           (->> patterns-set (filter (partial nine? three four)) first)
        six            (->> patterns-set (filter (partial six? eight one five)) first)
        zero           (->> patterns-set (filter (partial zero? two four five)) first)
        pattern->digit (zipmap [zero one two three four five six seven eight nine] (range 10))]
    (->> output (map set) (map pattern->digit) (apply str) (#(Integer/parseInt %)))))

(def answer-part2 (reduce (fn [acc [patterns output]] (+ acc (decode patterns output))) 0 patterns))