package org;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
https://www.youtube.com/watch?v=spWnqkbFgH4&t=261s

 */

@Path("/users")
public class Controller {

    public static List<User> users = new ArrayList<>();

    @GET
    @Produces (MediaType.APPLICATION_JSON)
    public Response getMovies(){
        return Response.ok(users).build();
    }

    @GET
    @Produces (MediaType.TEXT_PLAIN)
    @Path("/size")
    public Integer countMovies(){
        return users.size();
    }


    /*
    test query

    {
  "userId":1,
  "name":"MyFirstJSONMovie"
    }
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(User newUser){
        users.add(newUser);
        return Response.ok(users).build();
    }

    @PUT
    @Path("{userId}/{name}")
    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMovie (
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
    public Response deleteMovie(
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
