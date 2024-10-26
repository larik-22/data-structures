# Algorithms and classes

## MijnArrayList
Implementation: [mijn implementatie](../src/nl/saxion/cds/solution/data_structures/MyArrayList.java)

As I started building my own utility Csv Reader, based on the ArrayList, I had to add one additional method `clear()`. 

This needed  as I had to clear my own list instance on every line read. Otherwise, it would be eventually filled with all the data from the file and reading column by index wouldn't work properly.
This method simply sets the size of the list to 0 and internal array to default minimum size.

## DoublyLinkedList
Implementation: [my implementation](../src/nl/saxion/cds/solution/data_structures/DoublyLinkedList.java);

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

It is O(Log(N)) because it has to divide the array in half log(N) times to find the element.

I have implemented binary search during the lecture, as our teacher were explaining the algorithm and showed us some pseudocode.
The whole concept is fairly straightforward, but I always messed up the indexes.

### My linear search algorithm
Classification: O(N)

It is O(N) because it has to go through the whole input N to find the element.

The linear search was already implemented in the `MyArrayList` class, so I didn't have to implement it again.
It is by far the simplest search algorithm and I have used it many times before.

### My QuickSort algorithm
Classification: O(n log(n))


Quicksort is O(N log N) because it works by repeatedly splitting the list into smaller halves, which takes log N steps. Then it sorts each part, which takes N.

I had the most struggles with the QuickSort algorithm. It is beautiful and elegant, I was getting the concept, but I just couldn't wrap my head around the implementation.

I have spent more than two hours watching videos on YouTube and it was getting more and more confusing, because apparently there is different ways to implement it.

I have finally found a course on Algorithms, given by famous engineer and blogger PrimeAgen. He had taught his own course on algorithms, and luckily there were two lectures dedicated to it.
I found his explanation the best to me. He implemented algorithm with the audience using TypeScript and used pivot as the last element.
However, I, for the sake of practice, have decided to use the first element as a pivot. 

Reference: [PrimeAgen QuickSort](https://frontendmasters.com/courses/algorithms/)

### My selectionSort algorithm

Classification: O(n^2)

It's O(n^2) because it has two nested loops.
One loop to select an element of Array one by one
Another loop to compare that element with every other Array element

We were given a choice to implement either selection or insertion sort. I have decided to try both.
It was fairly easy, but I still had to watch explanatory video on YouTube to get the concept right.

### My insertionSort algorithm

Classification: O(n^2)

It is O(n^2) because it also has two nested loops.
One loop to select an element of the array one by one.
Another loop to compare that element with the already sorted elements in the array and shift them if needed.

I have implemented insertion sort as well, as I wanted to try both sorting algorithms. It was a bit more challenging to me than selection sort, but I have managed to implement it 
also with help of YouTube videos.

## My BST
Implementation: [my implementation](../src/nl/saxion/cds/solution/data_structures/MyBST.java);

I have created a normal binary search tree (without balancing) first to refresh my memory on how it works.
I have struggled the most with `.remove()` method and had to watch a couple of tutorials to get the concept right.
At the end of the day, I have managed to implement it and test all possible cases, achieving 100% coverage.

## My AVL
Implementation: [my implementation](../src/nl/saxion/cds/solution/data_structures/MyAVLTree.java);

This was by far the most challenging task for me. During the lectures, I thought I understood it, however I couldn't wrap my head around actual implementation.

I have spent roughly 4 hours watching tutorials and asking majesty AI for help. I have encountered a video of a respective gentleman explaining AVL trees in a very simple way (like for kids).
With his help I build up the structure, but still my rotations didn't work correctly and most of the time, random nodes just disappeared from the tree.

I got stuck, until I then found a simple python implementation online, and tried to replicate it in Java and with my set up. It worked. 
I have tested all the methods and achieved 100% coverage. I have tested it according to a video of a respective gentleman.

Reference: [AVL Tree explanation](https://www.youtube.com/watch?v=jDM6_TnYIqE) (around minute 32)

## MyHashMap (Separate Chaining)
Implementation: [my implementation](../src/nl/saxion/cds/solution/data_structures/MySpHashMap.java);

I have decided to pick separate chaining hash map as it is the most common and easiest to implement.
I have tried doing the open addressing one with linear probing, but I have been struggling with implementing `rehash()` method properly.

When it comes to my implementation, I have used `MyArrayList` as a bucket for storing the elements.
I also track all the keys in a separate list, so I can easily iterate over them when needed.
I have tested most of the methods, achieving 95% coverage, but fairly speaking, some of the tests are not really necessary.
I have spent an hour or so trying to test the inner class `Entry` and SPECIFICALLY the `equals()` method. After some time, I found a stack overflow thread helping me to test it properly.

## My MinHeap
Implementation:

## MyQueue
Implementation: [my implementation](../src/nl/saxion/cds/solution/data_structures/MyQueue.java);

I have implemented queue with the help of `MyDoublyLinkedList`.
I understand, that it might be slightly overkill to use doubly linked list for queue, and singly linked list would be enough. We don't actually move in two directions and all this extra functionality is not needed.

However, I have decided to use doubly linked list, as I have already implemented and it was fairly easy to adjust it to be used as a queue.

## MyStack
Implementation: [my implementation](../src/nl/saxion/cds/solution/data_structures/MyStack.java);

Same as with the queue, I have implemented stack with the help of `MyDoublyLinkedList`.
Again, it might be not the best choice to use doubly linked list, but I have decided to use it, as I have already created it.

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