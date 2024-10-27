package it.giovannigrieco;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class TemplateResolver {

    private Template template;
    private Map<String, List<String>> valuesMap;
    private Map<String, Iterator<String>> valuesIteratorMap;

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

    private void initialiseIterators(){
        valuesIteratorMap = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : valuesMap.entrySet()) {
            valuesIteratorMap.put(entry.getKey(), entry.getValue().iterator());
        }
    }

    public String generate() {
        StringBuilder result = new StringBuilder();
        result.append("//----------------------------------------------------------\n");
        result.append("//Generated with Henderson's Config Template Tool\n");
        result.append("//----------------------------------------------------------\n");
        //System.out.println(valuesMap);
        this.initialiseIterators();
        int iterationAmount = valuesMap.values().stream().map(List::size).reduce(1, (a, b) -> a * b);
        System.out.println("Iteration amount: " + iterationAmount);
        for(int i = 0 ; i < iterationAmount ; i++){
            String generated = template.getTemplate();
            for (Placeholder placeholder : template.getPlaceholderList()) {
                if(!valuesIteratorMap.get(placeholder.getName()).hasNext()){
                    valuesIteratorMap.put(placeholder.getName(), valuesMap.get(placeholder.getName()).iterator());
                }
                String valueLine = valuesIteratorMap.get(placeholder.getName()).next();
                if(placeholder.isArray()){
                    String[] values = valueLine.split("\\|");
                    for(int j = 0 ; j < values.length ; j++){
                        generated = generated.replace("$" + placeholder.getName() + "[" + j + "]$", values[j]);
                    }
                }else{
                    generated = generated.replace("$" + placeholder.getName() + "$", valueLine);
                }

            }
            result.append(generated).append("\n");
        }


        return result.toString();
    }
}
