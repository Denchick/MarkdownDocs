package markdowndocs.infrastructure;

public class ValueResult<TValue, TError> {
	private TValue Value;
	private TError Error;
	private boolean IsSuccess;

	ValueResult(TValue value, TError error, boolean isSuccess) {
		Value = value;
		Error = error;
		IsSuccess = isSuccess;
	}

	public TValue getValue() {
		return Value;
	}

	public TError getError() {
		return Error;
	}

	public boolean isSuccess() {
		return IsSuccess;
	}
}
