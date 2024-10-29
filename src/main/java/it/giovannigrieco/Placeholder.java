package it.giovannigrieco;

public class Placeholder {
    private final String name;
    private final boolean array;
    private int size;

    public Placeholder(String name, boolean array, int size) {
        this.name = name;
        this.array = array;
        this.size = size;
    }

    public Placeholder(String name) {
        this(name, false, 0);
    }

    public String getName() {
        return name;
    }

    public boolean isArray() {
        return array;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int hashCode() {
        return name.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Placeholder)) {
            return false;
        }
        Placeholder ph = (Placeholder) obj;
        return name.equals(ph.name);
    }


    public String toString() {
        return name + (array ? "[" + size + "]" : "");
    }

}
