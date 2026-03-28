package mumm.weatherstation.controller.dto;

public class ErrorMessage {

	private String message;

	private String code;

	public ErrorMessage(String message, String code) {
		this.message = message;
		this.code = code;
	}

	public ErrorMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public String getCode() {
		return code;
	}

}
