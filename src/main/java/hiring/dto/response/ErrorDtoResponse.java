package hiring.dto.response;

import hiring.exception.ServerErrorCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ErrorDtoResponse {
    private List<ServerErrorCode> errorCode;

    public ErrorDtoResponse(List<ServerErrorCode> errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorDtoResponse(ServerErrorCode errorCode) {
        this.errorCode = new ArrayList<>(Collections.singletonList(errorCode));
    }

    public List<ServerErrorCode> getErrorCodes() {
        return errorCode;
    }
}
