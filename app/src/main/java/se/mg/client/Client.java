package se.mg.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import se.mg.program.Program;

public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<Program> programList;

    public Client() {
        this.programList = new ArrayList<>();
    }

    public List<Program> getProgramList() {
        return programList;
    }

    public void removeProgram(int position) {
        programList.remove(position);
    }

    public void addProgram(Program program) {
        programList.add(program);
    }
}
