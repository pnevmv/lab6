package common.interaction;

import java.io.Serializable;

/**
 * Class for get request value.
 */
public class Response implements Serializable {
    private ResponseCode responseCode;
    private String responseBody;

    public Response(ResponseCode responseCode, String responseBody) {
        this.responseCode = responseCode;
        this.responseBody = responseBody;
    }

    /**
     * @return Response of Code.
     */
    public ResponseCode getResponseCode() {
        return this.responseCode;
    }

    /**
     * @return Response of Body
     */
    public String getResponseBody() {
        return this.responseBody;
    }

    @Override
    public String toString() {
        return "Response[" + responseCode + ", " + responseBody + "]";
    }
}
