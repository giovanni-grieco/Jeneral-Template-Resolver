package it.giovannigrieco;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Node <T> {
    private T content;
    private final Node<T> parent;
    private final List<Node<T>> children;

    public Node (Node<T> parent, T content){
        this.parent = parent;
        this.content = content;
        this.children = new ArrayList<>();
    }

    public void setContent(T content){
        this.content = content;
    }

    public T getContent(){
        return content;
    }

    public void addChildren(Node<T> child){
        this.children.add(child);
    }

    public List<Node<T>> getChildren(){
        return this.children;
    }

    public List<Node<T>> getLeafs() {
        Stack<Node<T>> stack = new Stack<>();
        List<Node<T>> leafs = new ArrayList<>();
        stack.push(this);
        while(!stack.isEmpty()){
            Node<T> current = stack.pop();
            if(current.getChildren().isEmpty()){
                leafs.add(current);
            } else {
                for(Node<T> child : current.getChildren()){
                    stack.push(child);
                }
            }
        }
        return leafs;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(content );
        sb.append("\n ");
        for(Node<T> n : this.children){
            sb.append(n.toString());
        }
        return sb.toString();
    }

    public List<Node<T>> pathToRoot() {
        List<Node<T>> pathToRoot = new ArrayList<>();
        Node<T> cursor = this;
        while(cursor.parent != null){
            pathToRoot.add(cursor);
            cursor = cursor.parent;
        }
        return pathToRoot;
    }
}
