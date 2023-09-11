package org;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/users")
public class Controller {

    public static List<User> users = new ArrayList<>();
    Repository repository = new Repository();

//    public Controller() {
//        users.add( new User("Alex","alex@google.com","asdf",1));
//        users.add( new User("Nikolay","nick@google.com","qwerty",2));
//        users.add( new User("Peter","peter@google.com","zzccxxvv",3));
//    }

    @GET
    @Produces (MediaType.APPLICATION_JSON)
    public Response getAllUsers(){
        return Response.ok(repository.getAllUsers()).build();
    }

    @GET
    @Produces (MediaType.TEXT_PLAIN)
    @Path("/size")
    public Integer countUsers(){
        return repository.getAllUsers().size();
    }


    /*
    test query

        {
        "name": "Alla",
        "email": "alla@google.com",
        "password": "asdf",
        "userId": 4
        }
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(User newUser){
        repository.addUser(newUser.getName(), newUser.getEmail(), newUser.getPassword());
//        users.add(newUser);
        return Response.ok(repository.getAllUsers()).build();
    }

    @PUT
    @Path("{userId}/{name}")
    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser (
            @PathParam("userId") Integer userId,
            @PathParam("name") String name){
        users = users.stream().map(user -> {
            if (user.getUserId().equals((userId))) {
                user.setName(name);
            }
            return user;
        }).collect((Collectors.toList()));
        return Response.ok(users).build();
    }

    @DELETE
    @Path("{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteUser(
        @PathParam("userId") Integer userId){

        Optional<User> userToDelete = users.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst();
        boolean removed = false;
        if(userToDelete.isPresent()){
            removed = users.remove(userToDelete.get());
        }
        if(removed){
            return Response.noContent().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}
