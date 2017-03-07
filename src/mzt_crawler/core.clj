(ns mzt-crawler.core
  (:gen-class)
  (:use [clojure.string :only (split)])
  (:require [clojure.java.io :as io])
  (:import [org.jsoup Jsoup])
  (:import [org.jsoup.nodes Element])
  (:import [org.jsoup.select Elements]))

;; constants
(def URL "http://www.dbmeinv.com/dbgroup/rank.htm?pager_offset=")
(def PATH "./mzt/")

;; http get response
(defn http-get [page]
  (.get (Jsoup/connect (str URL page))))

;; css select
(defn css-select [page css]
  (.select page css))

;; download image
(defn download-image [url]
  (def filename (str PATH (last (split url #"/"))))
  (with-open [in (io/input-stream url)
              out (io/output-stream filename)]
    (io/copy in out)))
    
;; crawler
(defn crawler [page]
  (def html (http-get page))
  (def elems (css-select html "img.height_min"))
  (doseq [e elems]
    (def src (.attr e "src"))
    (def title (.attr e "alt"))
    (printf "Title: %s\n" title)
    (printf "Download: [%s]\n\n" src)
    (download-image src)))

;; main
(defn -main [& args]
  (def page (first args))
  (def random (int (rand 900)))
  (crawler (if-not (nil? page) page random)))