package hu.bmrk.geposszerakobackend.model.enums;

public enum CPUManufacturer {

    AMD ("AMD"),
    INTEL ("Intel");

    private final String name;

    CPUManufacturer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
