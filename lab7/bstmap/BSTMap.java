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


    public void printInOrder(){
        printInOrderM(root, 0);
        System.out.println();
    }

    private void printInOrderD(Node p){
        if(p == null){
            return;
        }
        printInOrderD(p.left);
        System.out.print(p.key);
        System.out.print("->");
        printInOrderD(p.right);
    }

    private void printInOrderM(Node p, int level){
        if(p == null){
            return;
        }
        printInOrderM(p.right, level+1);
        String layers = "  ".repeat(level);
        System.out.print(layers);
        System.out.println(p.key);
        printInOrderM(p.left, level+1);

    }

    private void printInOrderA(Node p){
        if(p == null){
            return;
        }
        printInOrderA(p.left);
        printInOrderA(p.right);
        System.out.print(p.key);
        System.out.print("->");
    }

    public int height(){
        return height(root);
    }
    private int height(Node p){
        if(p == null){
            return -1;
        }
        return 1 + Math.max(height(p.left), height(p.right));
    }

    public Set<K> keySet(){
        return null;
    }

    public V remove(K key){
        return null;
    }
    private Node remove(Node p, K key){
        return p;
    }
    public V remove(K key, V value){
        return null;
    }

    public Iterator<K> iterator(){
        return null;
    }

    public static void main(String [] args){
        BSTMap<String ,Integer> students = new BSTMap<>();
        students.put("gyy", 20);
        students.put("gxy", 21);
        students.put("gzy", 22);
        students.put("hyy", 20);
        students.put("iyy", 20);
        students.put("hzy", 20);
        students.put("azy", 20);
        students.put("xzy", 20);
        students.printInOrder();
        System.out.println(students.size());
        System.out.println(students.height());
    }


}
