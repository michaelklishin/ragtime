(ns leiningen.ragtime
  (:require [leinjacker.deps :as deps])
  (:use [leiningen.run :only (run)]))

(defn- add-ragtime-deps [project]
  (-> project
      (deps/add-if-missing '[clojurewerkz/ragtime.core "0.3.9"])
      (deps/add-if-missing '[clojurewerkz/ragtime.sql "0.3.9"])))

(defn ragtime
  "Run ragtime.main with the options specified in the project file."
  [project command & args]
  (let [migrations (-> project :ragtime :migrations str)
        database   (-> project :ragtime :database)
        project    (add-ragtime-deps project)]
    (apply run project
           "-m" "ragtime.main"
           "-r" "ragtime.sql.database"
           "-d" database
           "-m" migrations
           command args)))
