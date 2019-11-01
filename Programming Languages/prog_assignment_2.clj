;;@author: Michael Kirkham
;;@version: 11/7/2017
;;CS 380
;;Programming Assignment 2

(ns prog_assignment_2)

;;takes in a character as a paramter
;;changes the character based on it's case
;;if it's not in the character ascii range it just returns itself
(defn change-char
  [s]
  (cond
      (Character/isLowerCase s)(char (+ (mod (+ 13 (- (int s) (int \a))) 26) (int \a)))
      (Character/isUpperCase s)(char (+ (mod (+ 13 (- (int s) (int \A))) 26) (int \A)))
      :else s))

;;takes in a string as a parameter
;;applies the function change-char to each of the characters in the string
(defn rot13
  [text]
  (apply str (map change-char text)))

;;takes in a group of characters and a string as parameters
;;gets the set of characters you put in
;;gets rid of the characters from the string individually
(defn zap-chars
  [chars text]
  (apply str (remove #((set chars) %) text)))
  
;;takes in a list as the parameter
;;puts the first element of the string in a new list and calls 
;;itself on the rest of the list
;;recurses until the list is empty
(defn shallow-reverse
  [lst]
  (if (empty? lst)
    []
    (conj (shallow-reverse (rest lst)) (first lst))))
    
;;takes in a list as the only parameter
;;if the list is empty it will return an empty element
;;if the list is not empty it returns the concatination of:
;;1. the recursion of the rest of the list and,
;;2. either the recursion of the first of the list (first = list) or just the first
;;of the list (first = element)
;;The second concatination item depends on whether or not the first
;;element in the list is a list, or just an element
(defn deep-reverse
  [lst]
  (if (empty? lst)
    []
    (concat (deep-reverse (rest lst))
	          (list (if (list? (first lst))
		                (deep-reverse (first lst))
		                (first lst))))))
    