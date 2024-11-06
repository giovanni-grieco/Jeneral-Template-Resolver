package it.giovannigrieco;

import java.util.Objects;

public class Value {

    private Placeholder placeholder;

    private String concreteValue;

    public Value(Placeholder placeholder, String concreteValue) {
        this.placeholder = placeholder;
        this.concreteValue = concreteValue;
    }

    public Placeholder getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(Placeholder placeholder) {
        this.placeholder = placeholder;
    }

    public String getConcreteValue() {
        return concreteValue;
    }

    public void setConcreteValue(String concreteValue) {
        this.concreteValue = concreteValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Value value = (Value) o;
        return Objects.equals(placeholder, value.placeholder) && Objects.equals(concreteValue, value.concreteValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placeholder, concreteValue);
    }

    @Override
    public String toString(){
        return this.placeholder+" - "+this.concreteValue;
    }
}
