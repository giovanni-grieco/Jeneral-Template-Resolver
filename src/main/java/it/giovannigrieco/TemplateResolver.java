package it.giovannigrieco;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class TemplateResolver {

    private final Template template;
    private final Map<String, List<String>> valuesMap;
    private final List<Map<String, String>> combinations;

    public TemplateResolver(Template template, List<String> valuesFiles) throws IOException {
        this.template = template;
        this.valuesMap = new HashMap<>();
        this.combinations = new ArrayList<>();
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

    private void generateCombinations(List<String> placeholderNames, int depth, Map<String, String> current) {
        if (depth == placeholderNames.size()) {
            combinations.add(new HashMap<>(current));
            return;
        }

        String placeholderName = placeholderNames.get(depth);
        List<String> values = valuesMap.get(placeholderName);

        for (String value : values) {
            current.put(placeholderName, value);
            generateCombinations(placeholderNames, depth + 1, current);
            current.remove(placeholderName);
        }
    }

    public String generate() {
        StringBuilder result = new StringBuilder();
        result.append("//----------------------------------------------------------\n");
        result.append("//Generated with Giovanni Pio Grieco's Template Tool\n");
        result.append("//----------------------------------------------------------\n");

        List<String> placeholderNames = new ArrayList<>(valuesMap.keySet());
        generateCombinations(placeholderNames, 0, new HashMap<>());

        for (Map<String, String> combination : combinations) {
            String generated = template.getTemplate();
            for (Placeholder placeholder : template.getPlaceholderList()) {
                String value = combination.get(placeholder.getName());
                if (placeholder.isArray()) {
                    String[] values = value.split("\\|");
                    for (int i = 0; i < values.length; i++) {
                        generated = generated.replace("$" + placeholder.getName() + "[" + i + "]$", values[i]);
                    }
                } else {
                    generated = generated.replace("$" + placeholder.getName() + "$", value);
                }
            }
            result.append(generated).append("\n");
        }

        return result.toString();
    }
}