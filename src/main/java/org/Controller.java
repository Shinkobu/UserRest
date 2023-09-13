package org;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.Exceptions.InvalidEmailException;
import org.Exceptions.InvalidPasswordException;
import org.Exceptions.InvalidUserNameException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Path("/users")
public class Controller {

    public static List<User> users = new ArrayList<>();
    Repository repository = new Repository();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        return Response.ok(repository.getAllUsers()).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/size")
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
    public Response createUser(User newUser) {
        try {
            if (Validator.isNameValid(newUser.getName()) &&
                    Validator.isEmailValid(newUser.getEmail()) &&
                    Validator.isPasswordValid(newUser.getPassword())) {
                repository.addUser(newUser.getName(), newUser.getEmail(), newUser.getPassword());
//            return Response.ok(repository.getAllUsers()).build();
                return Response.ok("User " + newUser.getName() + " successfully added to database").build();
            }
        } catch (InvalidEmailException | InvalidUserNameException | InvalidPasswordException ex) {
            return Response.ok(ex.getMessage())
                    .status(400)
                    .build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("userId") Integer userId, User user) {
        repository.changeUser(userId, user.getName(), user.getEmail(), user.getPassword());
        return Response.ok(repository.getAllUsers()).build();
    }

    @DELETE
    @Path("{userId}")
//    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteUser(@PathParam("userId") Integer userId) {

        Optional<User> userToDelete = Optional.ofNullable(repository.getUser(userId));

        boolean removed = false;

//        return Response.ok(repository.getAllUsers()).build();

        if (userToDelete.isPresent()) {
            repository.deleteUser(userId);
            removed = true;
        }
        if (removed) {
            return "SUCCESS. User with userId " + userId + " deleted!";
        }
//        return Response.status(Response.Status.BAD_REQUEST).build();

        return "ERROR. No User found with userId = " + userId + "!";
    }

}
