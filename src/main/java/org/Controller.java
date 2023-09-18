package org;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.Exceptions.UserRestException;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 * REST API Контроллер
 */
@Path("/users")
@Tag(name = "Users Controller", description = "Users REST APIs")
public class Controller {

    Repository repository = new Repository();

    /**
     * Возвращает всех пользователей из базы данных
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "getAllUsers",
            summary = "getAllUsers",
            description = "Получить всех пользователей из базы данных"
    )
    @APIResponse(
            responseCode = "200",
            description = "Операция успешно выполнена",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response getAllUsers() {
        return Response.ok(repository.getAllUsers()).build();
    }

    /**
     * Возвращает число пользователей в базе данных
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/size")
    @Operation(
            operationId = "countUsers",
            summary = "countUsers",
            description = "Получить число пользователей в базе данных"
    )
    @APIResponse(
            responseCode = "200",
            description = "Операция успешно выполнена",
            content = @Content(mediaType = MediaType.TEXT_PLAIN)
    )
    public Integer countUsers() {
        return repository.getAllUsers().size();
    }

    /**
     * Создаёт нового пользователя
     *
     * @param newUser - новый пользователь
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "createUser",
            summary = "createUser",
            description = "Создать нового пользователя"
    )
    @APIResponse(
            responseCode = "201",
            description = "Пользователь создан",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "Возникла ошибка",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response createUser(
            @RequestBody(
                    description = "Пользователь, который будет создан",
                    required = true,
                    content = @Content(schema = @Schema(implementation = User.class))
            )
            User newUser) throws UserRestException {

        try {
            // проверка на валидность и наличие пользователя с email в базе данных
            if (Validator.validator(newUser) && !repository.isEmailPresent(newUser.getEmail())) {
                repository.addUser(newUser.getName(), newUser.getEmail(), newUser.getPassword());
                return Response.status(Response.Status.CREATED)
                        .entity("Пользователь " + newUser.getName() + " успешно добавлен в базу данных").build();
            }
        } catch (UserRestException ex) {
            throw new UserRestException(ex.getMessage());
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Возникла ошибка при создании пользователя: " + newUser.getName()).build();
    }

    /**
     * Обновление данных существующего пользователя
     *
     * @param userId - Id пользователя для обновления
     * @param user - обновлённый пользователь
     */
    @PUT
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "updateUser",
            summary = "updateUser",
            description = "Обновить данные существующего пользователя"
    )
    @APIResponse(
            responseCode = "200",
            description = "Пользователь обновлён",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "Возникла ошибка",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response updateUser(
            @Parameter(
                    description = "id пользователя, которого нужно обновить",
                    required = true
            )
            @PathParam("userId") Integer userId,
            @Parameter(
                    description = "Обновлённый пользователь",
                    required = true
            ) User user) throws UserRestException {


        try {
            // Проверка на наличие пользователя с таким ID
            User oldUser = repository.getUser(userId);
            // проверка на валидность
            if (Validator.validator(user)) {
                // изменение данных
                repository.changeUser(userId, user.getName(), user.getEmail(), user.getPassword());
                return Response.status(Response.Status.OK)
                        .entity("Пользователь " + user.getName() + " обновлён").build();
            }
        }  catch (UserRestException ex) {
            throw new UserRestException(ex.getMessage());
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Возникла при обновлении пользователя: " + user.getName()).build();
    }

    /**
     * Удаление пользователя по id
     *
     * @param userId - id пользователя к удалению
     */
    @DELETE
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "deleteUser",
            summary = "deleteUser",
            description = "Удалить существующего пользователя"
    )
    @APIResponse(
            responseCode = "204",
            description = "Пользователь удалён",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "Возникла ошибка",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response deleteUser(
            @Parameter(
                    description = "id пользователя, которого нужно удалить",
                    required = true
            )
            @PathParam("userId") Integer userId) throws UserRestException {
        try {
            // проверка на наличие пользователя с id в базе данных
            User user = repository.getUser(userId);
        } catch (UserRestException ex) {
            throw new UserRestException(ex.getMessage());
        }
        repository.deleteUser(userId);
        return Response.status(Response.Status.OK).entity("Пользователь с Id " + userId + " удалён").build();


    }
}
