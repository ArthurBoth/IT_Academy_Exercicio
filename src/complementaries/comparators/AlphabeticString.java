package complementaries.comparators;

public class AlphabeticString implements Comparable<AlphabeticString>{

    /**
     * This class exists because 'String.compareTo()' compares the Unicode values 
     * of every 'char' in the String.
     * This means that it gives priority to uppercase letters when comparing.
     * Ex:
     *     "B".compareTo("a") => -31
     * 
     * In a true alphabetical order, it should have been a positive integer since 
     *     'B' comes after 'a', regardless of upper or lower case.
     */

    private String string;

    public AlphabeticString(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }

    @Override
    public int compareTo(AlphabeticString other) {
        return this.string.toLowerCase().compareTo(other.toString().toLowerCase());
    }
}
