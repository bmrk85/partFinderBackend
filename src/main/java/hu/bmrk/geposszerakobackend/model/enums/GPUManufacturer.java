package hu.bmrk.geposszerakobackend.model.enums;

public enum  GPUManufacturer {

    AMD ("AMD"),
    NVIDIA ("Nvidia");

    private final String name;

    GPUManufacturer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
