package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap <K extends Comparable<K>, V> implements Map61B <K, V>{

    private Node root;

    private class Node {
        private K key;
        private V value;
        private Node left;
        private Node right;
        private int size; //number of nodes in subtree
        public Node(K newKey, V newVal, int newSize){
            this.key = newKey;
            this.value = newVal;
            this.size = newSize;
        }
    }

    public BSTMap(){}
    public BSTMap(K key, V val){
        root = new Node(key, val, 1);
    }
    public BSTMap(Node p){
        root = p;
    }

    public void clear(){
        root.left = null;
        root.right = null;
        root = null;
    }

    public boolean containsKey(K key){
        Node target = thisSideMayContain(key, root);
        return target != null;
    }
    private Node thisSideMayContain(K key, Node p){
        if(p == null){
            return null;
        }
        int compare = key.compareTo(p.key);
        if(compare < 0){
            return thisSideMayContain(key, p.left);
        } else if (compare > 0) {
            return thisSideMayContain(key, p.right);
        } else {
            return p;
        }
    }

    public V get(K key){
        Node target = thisSideMayContain(key, root);
        if(target == null)
            return null;
        return target.value;
    }

    public int size(){
        return size(root);
    }
    private int size(Node x){
        if(x == null){
            return 0;
        } else {
            return x.size;
        }
    }

    public void put(K key, V value){
        if(root == null){
            root = put(key, value, root);
            root.size += 1;
        }
        root = put(key, value, root);
    }
    private Node put(K key, V value, Node p){
        if(p == null){
            return new Node(key, value, 0);
        }
        if(p.key == key){
            return p;
        }
        if(key.compareTo(p.key) < 0){
            p.left = put(key, value, p.left);
        } else if (key.compareTo(p.key) > 0) {
            p.right = put(key, value, p.right);
        }
        p.size += 1;
        return p;
    }

    public Set<K> keySet(){
        return null;
    }

    public V remove(K key){
        return null;
    }

    public V remove(K key, V value){
        return null;
    }

    public Iterator<K> iterator(){
        return null;
    }

    public static void main(String [] args){
        String name = "gyy";
        int age = 20;
        BSTMap students = new BSTMap(name, age);
        System.out.println(students.size());
        students.put("gsx", 188);
        students.put("gzz", 23);
        students.put("zsx", 23);
        System.out.println(students.containsKey("gyy"));
        System.out.println(students.containsKey("gyz"));
        System.out.println(students.containsKey("gzz"));
        System.out.println(students.get("gyy"));
        System.out.println(students.get("gsx"));
    }

}
