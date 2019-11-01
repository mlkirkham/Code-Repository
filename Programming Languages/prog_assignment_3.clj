;;@author: Michael Kirkham
;;@version: 11/15/2017
;;CS 380
;;Programming Assignment 3

(ns prog_assignment_3)

;;function to calculate the average of the approximation
;;and n/approximation. Takes in n and the approximation as
;;the parameters. Outputs a double.
(defn average
    [n guess]
    (double (/ (+ guess (/ n guess)) 2)))

;;function to return the entire sequence of approximations.
;;takes in n and the approximation as parameters.
(defn seq
    [n guess]
    (iterate (partial average n) guess))

;;function to return the square root approximation
;;within epsilon of the true square root. 
;;takes in n, the square, epsilon, the approximation distance,
;;and guess, the approximation. uses the seq function to get the
;;approximation values for the result.
(defn sqrt
    [n epsilon guess]
    (if (neg? n)
        (println "Retry with a positive n value.")    
        (if (< (Math/abs (- guess (average n guess))) epsilon)
            (first (seq n guess))
            (recur n epsilon (second (seq n guess))))))

;;function to return the square root approximation
;;within epsilon of the true square root. 
;;takes in n, the square, epsilon, the approximation distance,
;;and guess, the approximation. uses recursion to calculate
;;approximations and uses the new approximation until the guess is
;;within epsilon from the true root.
(defn sqrt-2
    [n epsilon guess]
    (if (neg? n)
        (println "Retry with a positive n value.")
        (if (< (Math/abs (- guess (average n guess))) epsilon)
            guess
            (recur n epsilon (average n guess)))))