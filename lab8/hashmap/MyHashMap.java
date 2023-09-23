package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author gsx
 */
public class MyHashMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int sizeOfBuckets;
    private int size;
    private double loadFactor;
    private double currentLF;

    /** Constructors */
    public MyHashMap() {
        sizeOfBuckets = 16;
        size = 0;
        loadFactor = 0.75;
        buckets = new Collection[sizeOfBuckets];
    }

    public MyHashMap(int initialSize) {
        size = 0;
        sizeOfBuckets = initialSize;
        loadFactor = 0.75;
        buckets = new Collection[sizeOfBuckets];;
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        size = 0;
        sizeOfBuckets = initialSize;
        loadFactor = maxLoad;
        buckets = new Collection[initialSize];
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node> [] table = new Collection[tableSize];
        for (int i=0; i<tableSize; i++){
            table[i] = createBucket();
        }
        return table;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    public void clear(){
        for(int i=0; i<sizeOfBuckets; i++){
            buckets[i] = null;
        }
        size = 0;
    }

    public boolean containsKey(K key){
        int h = hash(key);
        Node target = get(key, h);
        return target != null;
    }

    public V get(K key){
        if(!containsKey(key)){
            return null;
        }
        int h = hash(key);
        Node target = get(key, h);
        if(target == null){
            return null;
        }
        return target.value;
    }

    private Node get(K key, int index){
        if(buckets[index] == null) return null;
        for(Node object : buckets[index]){
            if(object.key.equals(key)){
                return object;
            }
        }
        return null;
    }

    public int size(){return size;}


    //https://github.com/exuanbo/cs61b-sp21/blob/main/lab8/hashmap/MyHashMap.java
    public void put(K key, V value){
        int h = hash(key);
        Node exist = get(key, h);
        if(buckets[h] == null){
            buckets[h] = new LinkedList<Node>();
        }
        if(exist == null){
            size ++;
            currentLF = (double) size / sizeOfBuckets;
            if(currentLF > loadFactor){
                resize();
                return;
            }
            Node tmp = new Node(key, value);
            buckets[h].add(tmp);
        }else {
            exist.value = value;
        }
    }
    private void resize(){
        size = 0;
        Collection<Node> [] bucketsBackup = new Collection[sizeOfBuckets];
        System.arraycopy(buckets,0,bucketsBackup, 0, sizeOfBuckets);
        sizeOfBuckets *= 2;
        buckets = new Collection[sizeOfBuckets];
        for (int i=0; i<sizeOfBuckets/2 -1; i++){
            if(bucketsBackup[i] != null){
                for(Node kv : bucketsBackup[i]){
                    put(kv.key, kv.value);
                }
            }
        }
    }

    private int hash(K key){
        int h = key.hashCode() % sizeOfBuckets;
        if(h < 0) h = sizeOfBuckets + h;
        return h;
    }


    public Set<K> keySet(){
        Set<K> keys = new HashSet<>();
        for(int i=0; i<sizeOfBuckets; i++){
            if(buckets[i] != null){
                for(Node kv : buckets[i]){
                    keys.add(kv.key);
                }
            }
        }
        return keys;
    }

    public V remove(K key){return null;}

    public V remove(K key, V value){return null;}

    public Iterator<K> iterator(){
        return null;
    }

    public static void main(String [] args){
        MyHashMap<String, Integer> students = new MyHashMap<>();
        students.put("gyy", 20);
        students.put("gyy", 22);
        students.put("gsx", 23);
        students.put("xsx", 23);
        students.put("xsz", 43);
        students.put("sdadsx", 33);
        for(int i=0; i<13; i++){
            students.put("dsad"+i, i*i);
        }
        System.out.println(students.containsKey("gyy"));
        System.out.println(students.containsKey("gsx"));
        System.out.println(students.containsKey("gysda"));
        System.out.println(students.get("gyy"));
        System.out.println(students.get("gsx"));
        System.out.println(students.get("xsx"));
        System.out.println(students.get("sdadsx"));
        System.out.println(students.size());


    }

}
