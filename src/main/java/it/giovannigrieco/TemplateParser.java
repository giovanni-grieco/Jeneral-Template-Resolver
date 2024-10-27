package it.giovannigrieco;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateParser {

    private String pathToTemplateFile;
    private List<String> templateFile;

    public TemplateParser(String pathToTemplateFile) throws IOException {
        this.pathToTemplateFile = pathToTemplateFile;
        Path path = Path.of(pathToTemplateFile);
        templateFile = Files.readAllLines(Path.of(pathToTemplateFile));
    }

    private boolean isPlaceholderArray(String placeholder){
        return placeholder.contains("[") && placeholder.contains("]");
    }

    private int getArrayIndex(String placeholder){
        String index = placeholder.split("\\[")[1].split("\\]")[0];
        return Integer.parseInt(index);
    }

    private String getPlaceholderName(String placeholder){
        return placeholder.split("\\[")[0];
    }

    private Set<Placeholder> parsePlaceholders(){
        // placeholders are confined between dollar signs
        // e.g. $placeholder$
        // we need to find all placeholders in the line
        Pattern pattern = Pattern.compile("\\$(.*?)\\$");
        Set<Placeholder> placeHolderSet = new HashSet<>();
        for(String line : templateFile){
            Matcher matcher = pattern.matcher(line);
            while(matcher.find()){
                String placeholder = matcher.group(1);
                String phName = getPlaceholderName(placeholder);
                boolean isArray = isPlaceholderArray(placeholder);
                int index = 0;
                if(isArray){
                    index = getArrayIndex(placeholder);
                }
                Placeholder ph = new Placeholder(phName, isArray, index);
                if(!placeHolderSet.contains(ph)){
                    placeHolderSet.add(ph);
                }else{
                    placeHolderSet.remove(ph);
                    placeHolderSet.add(ph);
                }
            }
        }
        return placeHolderSet;
    }

    public Template parse(){
        Set<Placeholder> placeholderList = parsePlaceholders();
        System.out.println("Placeholders found: " + placeholderList);
        return new Template(String.join("\n", templateFile), new ArrayList<>(placeholderList));
    }


}
