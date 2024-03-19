package complementaries.comparators;

public class PairedInt implements Comparable<PairedInt>{
    
    public int number;
    public int frequency;

    public PairedInt(int number, int frequency) {
        this.number = number;
        this.frequency = frequency;
    }

    @Override
    public int compareTo(PairedInt other) {
        return (other.frequency - this.frequency);
    }
}
