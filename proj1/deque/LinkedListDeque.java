package deque;

import java.util.Iterator;

public class LinkedListDeque  {
    private int size ;
    private Node sentinal;
    private Node tail;
//    private Node beforeTail;
    public LinkedListDeque(){
        sentinal = new Node(0,null);
        size = 0;
    }
    public LinkedListDeque(Node p){
        sentinal = new Node(0,null);
        sentinal.next = p;
        size += 1;
    }
    public LinkedListDeque(Node senti, Node tai, int length){
        sentinal = senti;
        tail = tai;
        size = length;
    }
    public class Node{
        public int item;
        public Node next;
        public Node(int i, Node n){
            item = i;
            next = n;
        }
    }

    public void addFirst(int item){
        Node first = new Node(item, null);
        first.next = sentinal.next;
        sentinal.next = first;
        if(size == 0){
            tail = sentinal.next;
        }
        size += 1;
    }

    public void addLast(int item){
        Node temp  = new Node(item, null);
        if(size == 0){
            sentinal.next = temp;
            tail = sentinal.next;
        } else{
            tail.next = temp;
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

    public int removeLast(){
        if(size == 0){
            return 0;
        }
        else {
            int last = tail.item;
            Node beforeTail = sentinal.next;
            int k = 0;
            while (k < size-2){
                beforeTail = beforeTail.next;
                k++;
            }
            tail = beforeTail;
            tail.next = null;
            size = size - 1;
            return last;
        }
    }

    public int removeFirst(){
        if(size == 0){
            return 0;
        } else{
            int first = sentinal.next.item;
            Node temp = sentinal.next;
            sentinal.next = temp.next;
            temp.next = null;
            size = size - 1;
            return first;
        }
    }

    public int get(int index){
        if(size == 0){
            return 0;
        } else {
            Node temp = sentinal.next;
            for (int i=0; i<index; i++){
                temp = temp.next;
            }
            return temp.item;
        }
    }

    public int getRecursive(int index){
        if(index == 0){
            return sentinal.next.item;
        } else{
            LinkedListDeque L = new LinkedListDeque(sentinal.next, tail, size-1);
            return L.getRecursive(index-1);
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
        for(int k=0; k<5; k++){
            L.addLast(k);
        }
        System.out.println(L.size());
        L.addFirst(1);
        System.out.println(L.size());
        L.addLast(2);
        L.printDeque();
        System.out.println(L.removeFirst());
        L.printDeque();
        System.out.println(L.removeLast());
        L.printDeque();
        for(int i=0; i<L.size(); i++){
            System.out.println(L.get(i));
            System.out.println(L.getRecursive(i));
        }
    }
}
