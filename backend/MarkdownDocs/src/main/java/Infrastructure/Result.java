package Infrastructure;

public class Result<TError> {

    private TError error;
    private boolean isSuccess;

    public Result(TError error, boolean isSuccess) {
        this.error = error;
        this.isSuccess = isSuccess;
    }

    public TError getError() {
        return error;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
