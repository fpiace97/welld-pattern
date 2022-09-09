package util;

import lombok.Data;

@Data
public class ManageResponse {

    private final String status;
    private final String message;

    public ManageResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }


}
