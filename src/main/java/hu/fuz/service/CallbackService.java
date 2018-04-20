package hu.fuz.service;

// a swagger api generálás miatt szükséges

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello_im")
@Api(value = "/hello_im", description = "Callback listener")
public class CallbackService {


    @POST
    @Consumes({
            MediaType.TEXT_PLAIN,
            MediaType.APPLICATION_JSON})
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(
            value = "Callback service",
            notes = "This is a simple callback service")
    @ApiResponse(
            code = 200,
            response = String.class,
            message = "This is only your original message.")
    @Path("callback")
    public String hello(
            @ApiParam(value = "Body callback message", required = true) String message){
        System.out.println("<################## I called with message: ##################"
                + "\n" + message + "\n"
                + ">################## I called with message: ##################");
        return message;
    }
}
