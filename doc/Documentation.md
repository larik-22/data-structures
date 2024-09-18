# Algorithms and classes

## MijnArrayList
Implementation: [mijn implementatie](../src/nl/saxion/cds/data_structures/MyArrayList.java)

As I started building my own utility Csv Reader, based on the ArrayList, I had to add one additional method `clear()`. 

This needed  as I had to clear my own list instance on every line read. Otherwise, it would be eventually filled with all the data from the file and reading column by index wouldn't work properly.
This method simply sets the size of the list to 0 and internal array to default minimum size.

## DoublyLinkedList
Implementation: [my implementation](../src/nl/saxion/cds/data_structures/DoublyLinkedList.java);

Implementing a doubly linked list was the assignment of the first week to get a fresh reminder of the basic data structures.
In the last year, I was lucky to have taken a course "Introduction to Algorithms and Data Structures" where I already implemented both Singly, Doubly and Circular linked list structures, which helped me to get started quickly.
I have rewritten most of the code myself, but it is still very much similar to my previous implementation.
I was confused with method `remove()` at first, as it wasn't clear if it should remove the first encountered element or all of them. Therefore, I have made it remove only the first encounter, but I have also added a method `removeAll()` which removes all occurrences of the given element.

I have had some troubles with `graphViz()` method as I have never used `DOT` language before. 
After spending some time reading the documentation and still not being able to visualize the nodes, I began to surf through internet. 
I stumbled upon a [stack overflow post](https://stackoverflow.com/questions/70441786/draw-doubly-linked-list-using-graphviz)   where I found a script for visualizing doubly linked list.
After that I have used chatGPT to achieve getting same script output based on elements inside my list.

When it comes to testing, I went a bit overboard and tested every method with multiple test cases, including edge cases.
I have achieved 100% coverage just out of curiosity, but I believe it was a good practice to do so.

## Utilities

### CsvReader
Implementation: [my implementation](../src/nl/saxion/cds/utils/CsvReader.java);

For reading data from Csv files, I have implemented my own utility class `CsvReader`.
I had one implemented during SDP course, so I took most of the code from there and adjusted it to use `MyArrayList` instead of `ArrayList`.
I have tested most of the methods, achieving 95% coverage.

### LambdaReader
Implementation: [my implementation](../src/nl/saxion/cds/utils/LambdaReader.java);

This class is basically a CsvReader, but more advanced. It is useful, when you need to read a specific Class type from the file and put them in the list.
I learned this technique during the SDP course and had implementation ready, however I also adjusted it to use `MyArrayList` instead of `ArrayList`.

### My binary search algorithm
Classification: O(Log(N))

Implementation:

### My linear search algorithm
Classification: O(N)

It is O(N) because it has to go through the whole input N to find the element.

Implementation:

### My QuickSort algorithm
Classification: O(n log(n))

The N log(N) comes from the fact that it has to divide the array log(N) times and then sort the array n times.

Implementation:

### My selectionSort algorithm

Classification: O(n^2)

It is O(n^2) because it has to go through the whole input N to find the smallest element and then swap it with the first element. Then it has to go through the rest of the array N-1 to find the next smallest element and so on.

Implementation:

### My insertionSort algorithm

Classification: O(n^2)

It is O(n^2) because it 

## My BST
Implementation:

## My HashMap
Implementation:

## My MinHeap
Implementation:

## My Stack
Implementation:

## My Queue
Implementation:

## My Graph
Implementation:

### My iterative depth first search algorithms
Classification:

Implementation:

### My iterative breadth first search algorithm
Classification:

Implementation:

### My Dijkstra algorithm
Classification:

Implementation:

### My A* algorithm
Classification:

Implementation:

### My MCST algorithm  
Classification:

Implementation:

# Technical design My Application

## Class diagram and reading the data

# Station search by station code

# Station search based on the beginning of the name

## Implementation shortest route

## Implementation minimum cost spanning tree 

## Implementation graphic representation(s)