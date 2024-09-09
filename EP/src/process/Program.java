package process;

import java.util.List;

public class Program {
    private String name;
    private Integer priority;
    private List<String> textSegment;

    public Program(String name, List<String> textSegment) {
        this.name = name;
        this.textSegment = textSegment;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public List<String> getTextSegment() {
        return textSegment;
    }

    public void setTextSegment(List<String> textSegment) {
        this.textSegment = textSegment;
    }

}