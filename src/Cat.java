public class Cat implements Comparable<Cat>{

    String name;
    int cuteness;

    public Cat(int cuteness,String name){
        this.name  =name;
        this.cuteness = cuteness;
    }

    @Override
    public int compareTo(Cat o) {
        return this.cuteness - o.cuteness;
    }
}
