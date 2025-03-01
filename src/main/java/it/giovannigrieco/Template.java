package it.giovannigrieco;

import java.util.List;

public class Template {

    private final List<Placeholder> placeholderList;
    private final String template;

    public Template(String template, List<Placeholder> placeholderList) {
        this.template = template;
        this.placeholderList = placeholderList;
    }

    public String getTemplate() {
        return template;
    }

    public List<Placeholder> getPlaceholderList() {
        return placeholderList;
    }

    public Placeholder findPlaceholderByName(String placeholderName){
        for(Placeholder p : placeholderList){
            if(p.getName().equals(placeholderName))
                return p;
        }
        return null;
    }

    @Override
    public String toString(){
        return "Template: " + template + "\nPlaceholders: " + placeholderList.toString();
    }
}
