package org;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.Exceptions.EmailExistsException;
import org.Exceptions.InvalidEmailException;
import org.Exceptions.InvalidPasswordException;
import org.Exceptions.InvalidUserNameException;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Path("/users")
@Tag(name = "Users Controller", description = "Users REST APIs")
public class Controller {

    public static List<User> users = new ArrayList<>();
    Repository repository = new Repository();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "getAllUsers",
            summary = "getAllUsers",
            description = "Get all users from database"
    )
    @APIResponse(
            responseCode = "200",
            description = "Operation completed",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response getAllUsers() {
        return Response.ok(repository.getAllUsers()).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/size")
    @Operation(
            operationId = "countUsers",
            summary = "countUsers",
            description = "Get all users from database"
    )
    @APIResponse(
            responseCode = "200",
            description = "Operation completed",
            content = @Content(mediaType = MediaType.TEXT_PLAIN)
    )
    public Integer countUsers() {
        return repository.getAllUsers().size();
    }


    /*
    test query

        {
        "name": "Alla",
        "email": "alla@google.com",
        "password": "asdf"
        }
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "createUser",
            summary = "createUser",
            description = "Create a new user"
    )
    @APIResponse(
            responseCode = "201",
            description = "User created",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "Exception or error occured",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response createUser(
            @RequestBody(
                    description = "User to create",
                    required = true,
                    content = @Content(schema = @Schema(implementation = User.class))
            )
            User newUser) {
        try {
            if (Validator.isNameValid(newUser.getName()) &&
                    Validator.isEmailValid(newUser.getEmail()) &&
                    Validator.isPasswordValid(newUser.getPassword()) &&
                    !repository.isEmailPresent(newUser.getEmail())) {

                repository.addUser(newUser.getName(), newUser.getEmail(), newUser.getPassword());

                return Response.status(Response.Status.CREATED)
                        .entity("User " + newUser.getName() + " successfully added to database").build();
            } // TODO one exception handler and edit the annotations
        } catch (InvalidEmailException | InvalidUserNameException |
                 InvalidPasswordException | EmailExistsException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Error occurred with user: " + newUser.getName()).build();
    }

    @PUT
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "updateUser",
            summary = "updateUser",
            description = "Update an existing user"
    )
    @APIResponse(
            responseCode = "200",
            description = "User updated",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response updateUser(
            @Parameter(
                    description = "The id of the user to be updated",
                    required = true
            )
            @PathParam("userId") Integer userId,
            @Parameter(
                    description = "New user data",
                    required = true
            )
            User user) {
        repository.changeUser(userId, user.getName(), user.getEmail(), user.getPassword());
        return Response.ok(repository.getAllUsers()).build();
    }

    @DELETE
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            operationId = "deleteUser",
            summary = "deleteUser",
            description = "Delete an existing user"
    )
    @APIResponse(
            responseCode = "204",
            description = "User deleted",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "User id not found",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response deleteUser(
            @Parameter(
                    description = "The id of the user to be deleted",
                    required = true
            )
            @PathParam("userId") Integer userId) {

        try {
            User user = repository.getUser(userId);
        } catch (NoResultException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ERROR. No User found with userId = " + userId + "!").build();
        }
        repository.deleteUser(userId);
//        return Response.status(Response.Status.fromStatusCode(204))
//                .entity("SUCCESS. User with userId " + userId + " deleted!").build();
//        return Response.status(Response.Status.fromStatusCode(204))
//                .entity("SUCCESS. User with userId " + userId + " deleted!").build();
        return Response.ok("SUCCESS. User with userId " + userId + " deleted!")
                .status(Response.Status.fromStatusCode(204))
                .build(); //TODO


//        Optional<User> userToDelete = Optional.ofNullable(repository.getUser(userId));
//        boolean removed = false;
//
//        if (userToDelete.isPresent()) {
//            repository.deleteUser(userId);
//            removed = true;
//        }
//        if (removed) {
//            return Response.status(Response.Status.fromStatusCode(204))
//                    .entity("SUCCESS. User with userId " + userId + " deleted!").build();
//        } else {
////        return Response.status(Response.Status.BAD_REQUEST).build();
//            return Response.status(Response.Status.BAD_REQUEST)
//                    .entity("ERROR. No User found with userId = " + userId + "!").build();
//        }
    }
}
