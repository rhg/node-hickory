; This file is a part of node-hickory
(ns node-hickory.core
  (:require [cljs.nodejs :as node]))

(defn parse
  "Calls `callback` with an two args

  If an error occured the first arg will be an error object else nil
  In the case it succeeded the second arg will be a dom object"
  ([s callback] (parse s {} callback))
  ([s options callback]
   (let [htmlparser (node/require "htmlparser")
         DomHandler (.-DefaultHandler htmlparser)
         Parser (.-Parser htmlparser)
         handler (fn [err dom]
                   (callback err dom))
         dom-handler (DomHandler. handler (clj->js options))
         parser (Parser. dom-handler)]
     (.parseComplete parser s))))

(defn as-hiccup*
  [obj]
  (when obj
    (case (aget obj "type")
      "tag"
      [(keyword (aget obj "name"))
       (js->clj (aget obj "attribs"))
       (map as-hiccup* (aget obj "children"))]
      (aget obj "raw"))))

(defn as-hiccup
  [dom]
  (map as-hiccup* dom))

(defn as-hickory*
  [obj]
  (when obj
  (case (aget obj "type")
    "tag"
    {:type :element
     :tag (keyword (aget obj "name"))
     :attrs (as-hickory* (aget obj "attribs"))
     :content (map as-hickory* (aget obj "children"))}
   (aget obj "raw"))))

(defn as-hickory
  [dom]
  (map as-hickory* dom))
