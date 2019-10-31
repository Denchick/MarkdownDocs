package markdowndocs.infrastructure;

public class ResultsFactory {
	public static <TValue, TError> ValueResult<TValue, TError> Success(TValue value) {
		return new ValueResult<TValue, TError>(value, null, true);
	}
	
	

	public static <TValue, TError> ValueResult<TValue, TError> Failed(TError error) {
		return new ValueResult<TValue, TError>(null, error, false);
	}

	public static <TError> Result Success() {
		return new Result<TError>(null, true);
	}

	public static <TError> Result FailedWith(TError error) {
		return new Result<TError>(error, false);
	}

}
