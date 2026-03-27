package mumm.weatherstation.controller.dto;

import java.util.Collections;
import java.util.List;

public class Response<T> {

    private T data;
    private List<ErrorMessage> errors;

    public Response(T data, List<ErrorMessage> errors) {
        this.data = data;
        this.errors = errors;
    }

    public Response(T data) {
        this.data = data;
        this.errors = Collections.emptyList();
    }

    public T getData() {
        return data;
    }

    public List<ErrorMessage> getErrors() {
        return errors;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setErrors(List<ErrorMessage> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "Response{" +
                "data=" + data +
                ", errors=" + errors +
                '}';
    }
}
