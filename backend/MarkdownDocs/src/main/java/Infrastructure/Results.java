package Infrastructure;

public class Results {

    public static <TValue> ValueResult<TValue, String> Success(TValue value) {
        return new ValueResult<TValue, String>(value, null, true);
    }

    public static <TValue, TError> ValueResult<TValue, TError> Failed(TError error) {
        return new ValueResult<>(null, error, false);
    }

    public static <TError> Result Success() {
        return new Result<TError>(null, true);
    }

    public static <TError> Result FailedWith(TError error) {
        return new Result<TError>(error, false);
    }
}
