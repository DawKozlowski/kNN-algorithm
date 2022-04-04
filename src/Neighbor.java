public class Neighbor implements Comparable<Neighbor>{

   public double distance;

   public String atribute;

    public Neighbor(double distance, String atribute) {
        this.distance = distance;
        this.atribute = atribute;
    }


    @Override
    public int compareTo(Neighbor o) {
        return Double.compare(this.distance, o.distance);
    }
}