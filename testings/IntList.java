import jh61b.junit.In;

public class IntList {
    public int first;
    public IntList rest;

    public IntList(int f, IntList r){
        first = f;
        rest = r;
    }

    public int size(){
        if(rest == null){
            return 1;
        }
        return 1 + this.rest.size();
    }
    public void addFirst(int x){
        first = x;
    }

    /* return size of the list using iteration*/
    public int iterativeSize(){
        int listSize = 1;
        //IntList temp = new IntList(this.first, this.rest);
        IntList temp = this;
        while (temp.rest != null){
            listSize += 1;
            temp = temp.rest;
        }
        return listSize;
    }

    public int get(int i){
        if(i == 0){
            return first;
        }
        return rest.get(i - 1);
    }


    public static void main(String[] args){
        IntList L = new IntList(15,null);
//        L.first = 5;
//        L.rest = null;
//
//        L.rest = new IntList();
//        L.rest.first = 10;
//        L.rest.rest = new IntList();
//        L.rest.rest.first = 15;
        L = new IntList(10, L);
        L = new IntList(5, L);
        L = new IntList(0, L);
        int length = L.size();
        int length2 = L.iterativeSize();
        System.out.println("The length of L is " + length2);
        System.out.println(L.get(0));
        System.out.println(L.get(1));
        System.out.println(L.get(2));
        System.out.println(L.get(3));
    }
}
