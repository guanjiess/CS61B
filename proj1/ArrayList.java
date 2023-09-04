public class ArrayList {
    private int size;
    private int [] items;

    public ArrayList(){
        items = new int[100];
        size = 0;
    }

    public void resize(int capacity){
        int [] a = new int[capacity];
        System.arraycopy(items, 0, a, 0, capacity);
        items = a;
    }
    public void addLast(int x){
        if(size == items.length){
            resize(size*2);
        }
        items[size] = x;
        size += 1;
    }

    public int getLast(){
        return items[size-1];
    }
    public void addFirst(int x){
        int [] temp = new int[100];
        System.arraycopy(items,0,temp,0,size);
        for(int k = 0; k < size; k++){
            items[k+1] = temp[k];
        }
        items[0] = x;
        size += 1;
    }
    public int get(int i){
        return items[i];
    }

    public int getSize() {
        return size;
    }
    public int removeLast(){
        int lastItem = items[size-1];
        size = size - 1;
        return lastItem;
    }


    public static void main(String [] args){
        ArrayList arrayList = new ArrayList() ;
        arrayList.addFirst(4);
        arrayList.addFirst(5);
        arrayList.addFirst(6);
        arrayList.addLast(7);
        System.out.println(arrayList.get(0));
        System.out.println(arrayList.get(1));
        System.out.println(arrayList.get(2));
        System.out.println(arrayList.getLast());
        System.out.println(arrayList.getSize());
        System.out.println(arrayList.removeLast());
        System.out.println(arrayList.getSize());

    }

}
