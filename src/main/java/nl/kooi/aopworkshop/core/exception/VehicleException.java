package nl.kooi.aopworkshop.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class VehicleException extends RuntimeException {

    public VehicleException (String msg) {
        super(msg);
    }
}
