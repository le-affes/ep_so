package process;
import util.*;

class Process {
    private int id;
    private Program program;
    private ProcessControlBlock BCP;

    public Process(int id, Program program, ProcessControlBlock BCP) {
        this.id = id;
        this.program = program;
        this.BCP = BCP;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public ProcessControlBlock getBCP() {
        return BCP;
    }

    public void setBCP(ProcessControlBlock BCP) {
        this.BCP = BCP;
    }
}

