; This file is a part of node-hickory
(ns node-hickory.core)

(def ^:dynamic *options*
  {})

(def ^:private htmlparser
  (js/require "htmlparser"))

(def ^:private DomHandler
  (.-DefaultHandler htmlparser))

(def ^:private Parser
  (.-Parser htmlparser))

(defn parse
  "Returns a channel that will have a value pushed on parsing done

  Pushes an error object on error"
  ([s callback] (parse s {} callback))
  ([s options callback]
   (let [handler (fn [err dom]
                   (callback err dom))
         dom-handler (DomHandler. handler)
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
