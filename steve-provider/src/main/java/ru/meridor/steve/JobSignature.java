package ru.meridor.steve;

public class JobSignature<T, R> {

    private final String id;

    private final Class<T> inputDataType;

    private final Class<R> returnDataType;

    public JobSignature(String id, Class<T> inputDataType, Class<R> returnDataType) {
        this.id = id;
        this.inputDataType = inputDataType;
        this.returnDataType = returnDataType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobSignature)) return false;

        JobSignature that = (JobSignature) o;

        if (!id.equals(that.id)) return false;
        if (!inputDataType.equals(that.inputDataType)) return false;
        if (!returnDataType.equals(that.returnDataType)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + inputDataType.hashCode();
        result = 31 * result + returnDataType.hashCode();
        return result;
    }


    public String getId() {
        return id;
    }

    public Class<T> getInputDataType() {
        return inputDataType;
    }

    public Class<R> getReturnDataType() {
        return returnDataType;
    }
}
