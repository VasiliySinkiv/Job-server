package hiring.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServerException extends Exception {
    private List<ServerErrorCode> errorCodes;

    public ServerException(List<ServerErrorCode> errorCodes) {
        this.errorCodes = errorCodes;
    }

    public ServerException(ServerErrorCode errorCode) {
        this.errorCodes = new ArrayList<>(Collections.singletonList(errorCode));
    }

    public List<ServerErrorCode> getErrorCodes() {
        return errorCodes;
    }
}
