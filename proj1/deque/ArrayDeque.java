package deque;

public class ArrayDeque {
    private int size ;
    private int capacity ;
    private int [] items ;
    private int front ;
    private int rear ;

    public  ArrayDeque(int head , int tail, int new_capacity){
        items = new int [new_capacity];
        capacity = new_capacity;
        front = head;
        rear = tail;
        size = 0;
    }
    public ArrayDeque(){
        items = null;
        front = -1;
        rear = -1;
        size = 0;
    }

    public void addFirst(int x){
        /*int [] temp = new int[8];
        System.arraycopy(items,0,temp,0,size);
        items[0] = x;
        System.arraycopy(temp,0,items,1,size);*/
        if(size == capacity){
            resize(2*capacity);
        }
        if(front == -1) {
            front = 0;
            rear = 0;
        } else if (front == 0) {
            front = capacity - 1;
        } else {
            front = front - 1;
        }
        items[front] = x;
        size += 1;
    }

    public void addLast (int x){
        if(size == capacity){
            resize(2*capacity);
        }
        if(front == -1){
            front = 0;
            rear = 0;
        }
        else if(rear == capacity - 1){
            rear = 0;
        } else {
            rear = rear + 1;
        }
        items[rear] = x;
        size += 1;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public boolean isFull(){
        return size == capacity | (front == 0 & rear ==capacity - 1) | (front == rear + 1);
    }

    public int size(){
        return size;
    }

    public void resize(int newCapacity){
        // all the items must be copied to the new array
        // and the order must be follow the original order, front -> rear
        // for example,  4 3 2 1(rear) 5(front) 6 7 8, after resize, the sequence need to be
        //5 6 7 8 4 3 2 1 0 0 0 0 ~~~~
        int [] a = new int [newCapacity];
        for(int i=0; i<capacity; i++){
            int target = (front + i) % capacity;
            a[i] = items[target];
        }
        items = a;
        front = 0;
        rear = capacity - 1;
        capacity = newCapacity;
    }

    public void printDeque(){
        for (int i=0; i<size; i++){
            int index = (front + i) % capacity;
            System.out.print(items[index]+" ");
        }
        System.out.println();
    }

    public int removeLast(){
        /*int last = items[rear];
        items[size-1] = 0;
        size -= 1;*/
        if(isEmpty()){
            return 0;
        }
        int last = items[rear];
        items[rear] = 0;
        if(size == 1){
            front = -1;
            rear = -1;
        } else if(rear == 0){
            rear = capacity - 1;
        } else {
            rear = rear - 1;
        }
        size -= 1;
        return last;
    }
    public int removeFirst(){
        /*int first = items[0];
        int [] temp = new int [8];
        System.arraycopy(items, 1, temp, 0, size-1);
        items = temp;
        size -= 1;*/
        if(isEmpty()){
            return 0;
        }
        int first = items[front];
        items[front] = 0;
        if(size == 1){
            front = -1;
            rear = -1;
        } else if (front == capacity -1) {
            front = 0;
        } else {
            front = front + 1;
        }
        size -= 1;
        return first;
    }

    public int get(int index){
        int wanted = 0;
        if(rear - front > 0){
            wanted = items[front+index];
        } else if (rear - front < 0) {
            int target = (front + index) % capacity;
            wanted = items[target];
        }
        return wanted;
    }

    public void printItems(){
        for(int k=0; k<capacity; k++){
            System.out.print(items[k]+" ");
        }
        System.out.println();
    }
}
