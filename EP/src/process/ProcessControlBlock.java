package process;
import util.*;

public class ProcessControlBlock {
    private int programCounter;
    private ProcessState state;
    private int priority;
    private int creditCounter;
    private int[] generalRegisters;
    private int textSegmentRef;

    public ProcessControlBlock(int programCounter, ProcessState state, int priority, int creditCounter, int[] generalRegisters, int textSegmentRef) {
        this.programCounter = programCounter;
        this.state = state;
        this.priority = priority;
        this.creditCounter = creditCounter;
        this.generalRegisters = generalRegisters;
        this.textSegmentRef = textSegmentRef;
    }

    // Getters and Setters
    public int getProgramCounter() {
        return programCounter;
    }

    public void setProgramCounter(int programCounter) {
        this.programCounter = programCounter;
    }

    public ProcessState getState() {
        return state;
    }

    public void setState(ProcessState state) {
        this.state = state;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getCreditCounter() {
        return creditCounter;
    }

    public void setCreditCounter(int creditCounter) {
        this.creditCounter = creditCounter;
    }

    public int[] getGeneralRegisters() {
        return generalRegisters;
    }

    public void setGeneralRegisters(int[] generalRegisters) {
        this.generalRegisters = generalRegisters;
    }

    public int getTextSegmentRef() {
        return textSegmentRef;
    }

    public void setTextSegmentRef(int textSegmentRef) {
        this.textSegmentRef = textSegmentRef;
    }
}