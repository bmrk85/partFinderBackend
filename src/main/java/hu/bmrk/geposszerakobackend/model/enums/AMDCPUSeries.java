package hu.bmrk.geposszerakobackend.model.enums;

public enum AMDCPUSeries {

    RYZEN3 ("Ryzen 3"),
    RYZEN5 ("Ryzen 5"),
    RYZEN7 ("Ryzen 7");


    private final String name;

    AMDCPUSeries(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
