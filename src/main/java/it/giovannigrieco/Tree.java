package it.giovannigrieco;

import java.util.List;

public class Tree <T>{

    private final Node <T> root;

    public Tree(){
        this.root = new Node<>(null, null);
    }

    public Node<T> getRoot() {
        return this.root;
    }

    public List<Node<T>> getLeafs(){
        return root.getLeafs();
    }

    public String toString(){
        return root.toString();
    }
}
