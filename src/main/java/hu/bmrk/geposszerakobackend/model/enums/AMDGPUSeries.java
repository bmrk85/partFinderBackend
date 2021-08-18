package hu.bmrk.geposszerakobackend.model.enums;

public enum AMDGPUSeries {

    RX5XX ("RX 500"),
    RX5XXX ("RX 5000"),
    RX6XXX ("RX 6000");


    private final String name;

    AMDGPUSeries(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
