package org;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.Exceptions.UserRestException;

/**
 * Обработчик исключений из пакета org.Exceptions
 */
@Provider
public class ExceptionHandler implements ExceptionMapper<UserRestException> {

    @Override
    public Response toResponse(UserRestException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
}
