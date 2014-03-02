#lang racket

;Thomas Benge
;CSCI 431
;Program 3
;Binary Search Trees in Scheme

(define make-tree
  (lambda (key left right)
    (list key left right))) ;looks like (root (left subtree) (right subtree))

(define empty-tree?
  (lambda (tree)
    (null? tree)))

(define root
  (lambda (tree) 
    (car tree)))

(define left-child
  (lambda (tree) 
    (car (cdr tree))))

(define right-child
  (lambda (tree) 
    (car (cdr (cdr tree)))))

(define insert ;finds where to put a new key by recursively calling itself
  (lambda (tree key)
    (cond
      ((empty-tree? tree)   ;until it finds the correct empty spot        
       (make-tree key '() '())) 
      ((< key (root tree))
       (make-tree (root tree) 
                   (insert (left-child tree) key) ;recursion on appropriate side
                   (right-child tree))) ;just re-writes the other side
      ((> key (root tree))    
       (make-tree (root tree)
                   (left-child tree)
                   (insert (right-child tree) key)))
      (else tree))))


(define find
  (lambda (tree key)
    (cond
      ((empty-tree? tree) #f) ;returns false if key is not in tree
      ((= key (root tree)) tree)
      ((< key (root tree)) (find (left-child tree) key))
      (else (find (right-child tree) key)))))

(define count-keys
  (lambda (tree)
    (cond
      ((empty-tree? tree) 0)
      (else (+ 1 ;+1 for the root
               (count-keys (left-child tree)) ;recursively calls each side to
               (count-keys (right-child tree)))))));find all the keys


(define pre-order
  (lambda (tree)
    (cond
      ((empty-tree? tree) '())
      (else (append
             (list (root tree)) ;roots first
             (pre-order (left-child tree))
             (pre-order (right-child tree)))))))

(define in-order
  (lambda (tree)
    (cond
      ((empty-tree? tree) '())
      (else (append
             (in-order (left-child tree))
             (list (root tree)) ;this way sorts it least to greatest
             (in-order (right-child tree)))))))

(define post-order
  (lambda (tree)
    (cond
      ((empty-tree? tree) '())
      (else (append
             (post-order (left-child tree))
             (post-order (right-child tree))
             (list (root tree))))))) ;roots last
