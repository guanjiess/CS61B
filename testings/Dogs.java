public class Dogs {
/**
    public static void main(String[] args){
        System.out.println(args[3]);
    }
    */
    public int weight;

    public Dogs(int w){
        weight = w;
    }
    public void makenoise(){
        if(weight < 10){
            System.out.println("yip");
        } else if (weight < 30) {
            System.out.println("Bark, Bark");
        } else {
            System.out.println("Woofffffffff!");
        }
    }

}
