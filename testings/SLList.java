//An sllist is a list of integers.
public class SLList {
    private IntNode first;
    private int size;
    private IntNode sentinel;
    private IntNode last;
    public SLList(){
        sentinel = new IntNode(63, null);
        last = new IntNode(64,null);
        size = 0;
    }
    public SLList(int x) {
        sentinel = new IntNode(63, null);
        sentinel.next = new IntNode(x, null);
        last = sentinel.next;
        size = 1;
    }

    public void addFirst(int x) {
        sentinel.next = new IntNode(x, first);
        size += 1;
    }
    public int getFirst() {
        return sentinel.next.item;
    }
    public void addLast(int x){
        IntNode p = sentinel;
        while (p.next != null) {
            p = p.next;
        }
        p.next = new IntNode(x, null);
        size += 1;
    }
    public int getLast(){
        IntNode p = sentinel;
        while (p.next != null){
            p = p.next;
        }
        return p.item;
    }

    public void addLast2(int x){
        last.next = new IntNode(x, null);
        last = last.next;
        size += 1;
    }

    private static int size(IntNode p){
        if (p.next == null){
            return 1;
        }
        return 1 + size(p.next);
    }
    public int size(){
        return size;
    }
    public static void main(String[] args){
        SLList L = new SLList();
        L.addFirst(11);
        L.addFirst(12);
        L.addFirst(13);
        L.addLast(20);
        System.out.println(L.getLast());
        System.out.println(L.size());
        L.addLast2(21);
        System.out.println(L.getLast());
        System.out.println(L.size());
    }

}
