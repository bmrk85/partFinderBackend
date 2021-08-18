package hu.bmrk.geposszerakobackend.model.enums;

public enum IntelCPUSeries {

    I3 ("Core i-3"),
    I5 ("Core i-5"),
    I7 ("Core i-7");


    private final String name;

    IntelCPUSeries(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
