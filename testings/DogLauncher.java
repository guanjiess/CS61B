public class DogLauncher {
    public static void main(String[] args){
        Dogs d = new Dogs(35);
        /*d.weight = 35;*/
        d.makenoise(); /* a specific dog that makes noise*/
        Dogs smalldog = new Dogs(5);
        Dogs hugedog = new Dogs(150);
        smalldog.makenoise();
        hugedog.makenoise();
    }
}
