package deque;

import java.util.Iterator;

public class LinkedListDeque<T> {
    private int size ;
    private Node sentinal;
    private Node tail ;
    public LinkedListDeque(){
        sentinal = new Node(null,null, null);
        size = 0;
    }
    public LinkedListDeque(Node p){
        sentinal = new Node(null,null, null);
        sentinal.next = p;
        size += 1;
    }
    public LinkedListDeque(Node senti, Node tai, int length){
        sentinal = senti;
        tail = tai;
        size = length;
    }
    public class Node{
        public T item;
        public Node next;
        public Node prev;
        public Node(T i, Node n, Node p){
            item = i;
            prev = p;
            next = n;
        }
    }

    public void addFirst(T item){
        Node first = new Node(item, null, null);
        if(size == 0){
            sentinal.next = first;
            first.prev = sentinal;
            tail = sentinal.next;
        } else{
            first.next = sentinal.next;
            sentinal.next.prev = first;
            sentinal.next = first;
            first.prev = sentinal;
        }
        size += 1;
    }

    public void addLast(T item){
        Node last  = new Node(item, null, null);
        if(size == 0) {
            sentinal.next = last;
            last.prev = sentinal;
            tail = sentinal.next;
            tail.prev = sentinal;
        }
        else{
//            beforeTail = tail;
            last.prev = tail;
            tail.next = last;
            tail = tail.next;
        }
        size += 1;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        Node temp = sentinal.next;
        for(int i=0; i<size; i++){
            System.out.print(temp.item+" ");
            temp = temp.next;
        }
        System.out.println();
    }

    public T removeLast(){
        if(size == 0){
            return null;
        }
        T last = tail.item;
        if(size == 1){
            sentinal.next.prev = null;
            sentinal.next = null;
            tail = null;
        }
        if (size >= 2) {
            tail = tail.prev;
            tail.next = null;
        }
        size = size - 1;
        return last;
    }

    public T removeFirst(){
        if(size == 0){
            return null;
        }
        T first = sentinal.next.item;
        Node temp = sentinal.next;
        if(size == 1){
            temp.prev = null;
            sentinal.next = null;
            tail = null;
        }
        if(size >= 2){
            temp.next.prev = sentinal;
            sentinal.next = temp.next;
            temp.next = null;
            temp.prev = null;
        }
        size = size - 1;
        return first;
    }

    public T get(int index){
        if(size == 0){
            return null;
        } else {
            Node temp = sentinal.next;
            for (int i=0; i<index; i++){
                temp = temp.next;
            }
            return temp.item;
        }
    }

    public T getRecursive(int index){
        if(size == 0){
            return null;
        }
        if(index == 0){
            return (T)sentinal.next.item;
        } else{
            LinkedListDeque L = new LinkedListDeque(sentinal.next, tail, size-1);
            return (T)L.getRecursive(index-1);
        }
    }

//    public boolean equals(Object o){
//
//    }

//    public Iterator<T> iterator(){
//
//    }

    public static void  main(String[] args){
        LinkedListDeque L = new LinkedListDeque();
//        L.addLast(999);
//        for (int k=0; k<5;k++){
//            L.addLast(k);
//        }
        for (int i=0; i<6; i++){
            L.addFirst(i);
            L.printDeque();
            System.out.println("add one element, current size is: "+L.size());
        }
        for (int i=0; i<5; i++){
            System.out.println(L.removeFirst());
            L.printDeque();
            System.out.println("remove one element, current size is: "+L.size());
        }
        for (int i=0; i<5; i++){
            L.addLast(i);
            L.printDeque();
            System.out.println("add one element, current size is: "+L.size());
        }
        for (int i=0; i<5; i++){
            System.out.println(L.removeLast());
            L.printDeque();
            System.out.println("remove one element, current size is: "+L.size());
        }
        for(int i=0; i<7 ; i++){
            L.addLast(i*i);
            L.printDeque();
        }
        L.printDeque();
        for(int i=0; i<L.size(); i++){
            System.out.println(L.get(i));
            System.out.println(L.getRecursive(i));
        }
    }
}
