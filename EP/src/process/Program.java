package process;
import util.*;

class Program {
    private String name;
    private int priority;
    private String[] textSegment;

    public Program(String name, int priority, String[] textSegment) {
        this.name = name;
        this.priority = priority;
        this.textSegment = textSegment;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String[] getTextSegment() {
        return textSegment;
    }

    public void setTextSegment(String[] textSegment) {
        this.textSegment = textSegment;
    }
}