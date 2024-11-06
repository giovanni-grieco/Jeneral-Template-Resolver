package it.giovannigrieco;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class TemplateResolver {

    private final Template template;
    private final Map<String, List<String>> valuesMap;

    public TemplateResolver(Template template, List<String> valuesFiles) throws IOException {
        this.template = template;
        this.valuesMap = new HashMap<>();
        for (String valuesFile : valuesFiles) {
            List<String> lines = Files.readAllLines(Path.of(valuesFile));
            String placeholderName = Path.of(valuesFile).getFileName().toString().split("\\.")[0];
            valuesMap.put(placeholderName, lines);
        }
        verifyPlaceholders();
    }

    private void verifyPlaceholders() {
        for (Placeholder placeholder : template.getPlaceholderList()) {
            if (!valuesMap.containsKey(placeholder.getName())) {
                throw new IllegalArgumentException("No value file found for placeholder: " + placeholder.getName());
            }
        }
    }



    public Tree<Value> generateTree(){
        Tree<Value> tree = new Tree<>();
        // p1, p2, p3 etc
        List<String> placeholderNames = new ArrayList<>(valuesMap.keySet());
        Stack<TreeCreationStack> stack = new Stack<>();
        stack.push(new TreeCreationStack(0, tree.getRoot()));
        //List<Node<Value>> valueNodes = new ArrayList<>();
        boolean firstLoop = true;
        while( firstLoop || !stack.isEmpty()){
            TreeCreationStack tcs = stack.pop();
            if(tcs.depth != placeholderNames.size()) {
                Placeholder ph = template.findPlaceholderByName(placeholderNames.get(tcs.depth));
                List<String> valuesContentList = valuesMap.get(ph.getName());
                for (String s : valuesContentList) {
                    Value v = new Value(ph, s);
                    Node<Value> nv = new Node<>(tcs.nodo, v);
                    tcs.nodo.addChildren(nv);
                    stack.push(new TreeCreationStack(tcs.depth+1, nv));
                }
            }
            firstLoop=false;
        }
        System.out.println(tree);
        return tree;
    }


    public String generate(){
        StringBuilder result = new StringBuilder();
        Tree<Value> tree = generateTree();
        List<Node<Value>> leafs = tree.getLeafs();
        Collections.reverse(leafs);
        for(Node<Value> leaf : leafs){
            List<Value> lineValues = leaf.pathToRoot().stream().map(Node::getContent).collect(Collectors.toList());
            Collections.reverse(lineValues);
            String concreteLine = makeConcreteLine(lineValues);
            result.append(concreteLine).append("\n");
        }

        return result.toString();
    }

    private String makeConcreteLine(List<Value> lineValues) {
        String generated = template.getTemplate();
        for(Value value : lineValues){
            Placeholder placeholder = value.getPlaceholder();
            if(placeholder.isArray()){
                String[] values = value.getConcreteValue().split("\\|");
                for(int i = 0; i < values.length; i++){
                    generated = generated.replace("$" + placeholder.getName() + "[" + i + "]$", values[i]);
                }
            }else{
                generated = generated.replace("$" + placeholder.getName() + "$", value.getConcreteValue());
            }
        }
        return generated;
    }

    private class TreeCreationStack {
        public int depth;
        public Node<Value> nodo;

        public TreeCreationStack(int depth, Node<Value> nodo){
            this.depth=depth;
            this.nodo=nodo;
        }
    }
}