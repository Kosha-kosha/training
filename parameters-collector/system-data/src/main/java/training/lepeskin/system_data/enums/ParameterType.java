package training.lepeskin.system_data.enums;

public enum ParameterType {
    SCALAR("Scalar"),
    VECTOR("Vector"),
    TABLE("Table");

    private String text;

    ParameterType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
