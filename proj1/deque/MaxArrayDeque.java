package deque;
import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> compare;

    public MaxArrayDeque(){}
    public MaxArrayDeque(Comparator<T> c){
        compare = c;
    }
    public T max(){
        T maxTerm = this.get(0);
        for(int i=0; i<this.size(); i++ ){
            if(compare.compare(this.get(i), maxTerm) >= 0){
                maxTerm = this.get(i);
            }
        }
        return maxTerm;
    }

    public T max(Comparator<T> c){
        compare = c;
        return max();
    }

}
