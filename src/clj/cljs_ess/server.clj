(ns cljs-ess.server
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]))

(defn respond-hello [request]          
  {:status 200 :body "Hello, world!"})

(def routes
  (route/expand-routes                                   
    #{["/" :get respond-hello :route-name :greet]}))

(defn create-server [host port]
  (let [service-map 
        {::http/secure-headers {:content-security-policy-settings {:object-src "none"}}
         ::http/resource-path "public"
         ::http/routes        routes  
         ::http/type          :jetty  
         ::http/port          port
         ::http/join          false}] 
    (-> service-map 
       http/default-interceptors 
       http/create-server)))     
    
