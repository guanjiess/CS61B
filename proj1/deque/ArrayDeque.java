package deque;

public class ArrayDeque {
    private int size = 0;
    private int [] items = new int[8];
    private int nextFirst;
    private int nextLast;

    public void ArrayDeque(){
        items = new int [8];
        for (int i=0; i<8; i++){
            items[i] = 0;
        }
        size = 0;
    }

    public void addFirst(int x){
        int [] temp = new int[8];
        System.arraycopy(items,0,temp,0,size);
        items[0] = x;
        System.arraycopy(temp,0,items,1,size);
        size += 1;
    }

    public void addLast (int item){
        items[size] = item;
        size += 1;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        for (int i=0; i<size; i++){
            System.out.print(items[i]+" ");
        }
    }

    public int removeLast(){
        int last = items[size-1];
        items[size-1] = 0;
        size -= 1;
        return last;
    }
    public int removeFirst(){
        int first = items[0];
        int [] temp = new int [8];
        System.arraycopy(items, 1, temp, 0, size-1);
        items = temp;
        size -= 1;
        return first;
    }

    public int get(int index){
        return items[index];
    }


}
