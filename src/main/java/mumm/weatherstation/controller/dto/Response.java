package mumm.weatherstation.controller.dto;

import java.util.List;

public final class Response<T> {

	private final T data;

	private final List<ErrorMessage> errors;

	private Response(T data, List<ErrorMessage> errors) {
		this.data = data;
		this.errors = List.copyOf(errors);
	}

	public static <T> Response<T> success(T data) {
		return new Response<>(data, List.of());
	}

	public static <T> Response<T> error(String code, String message) {
		return new Response<>(null, List.of(new ErrorMessage(code, message)));
	}

	public T getData() {
		return data;
	}

	public List<ErrorMessage> getErrors() {
		return errors;
	}

}
