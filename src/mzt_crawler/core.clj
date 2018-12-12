(ns mzt-crawler.core
  (:gen-class)
  (:use selmer.parser)
  (:require [clojure.string :as string])
  (:require [clojure.java.io :as io])
  (:import [org.jsoup Jsoup])
  (:import [org.jsoup.nodes Element])
  (:import [org.jsoup.select Elements]))

;; constants
(def URL "https://www.w24j.com/cn/vl_update.php?&mode=&page=")
(def TMPL "tmpl.html")

(defn pwd
  "Returns current working directory as a String.  (Like UNIX 'pwd'.)
  Note: In Java, you cannot change the current working directory."
  []
  (System/getProperty "user.dir"))

(defn http-get
  "http get response"
  [page]
  (.get (Jsoup/connect (str URL page))))

(defn replace-links
  "replace lagre image link"
  [links]
  (map (fn [x] (string/replace x #"ps" "pl")) links))

(defn crawler
  "crawl and generate html"
  [page]
  (let [html (http-get page)
        elems (.select html "div.video")
        ids (map #(.text (.select % "a > .id")) elems)
        thumbs (map #(str "http:" (.attr (.select % "a > img") "src")) elems)
        pics (replace-links thumbs)
        data (apply map vector [ids thumbs pics])]
    (selmer.parser/set-resource-path! (pwd))
    (println (render-file TMPL {:data data}))))

;; main
(defn -main [& args]
  (let [page (first args)
        random (int (rand 1024))]
    (crawler (if-not (nil? page) page random))))
