package hu.fuz.service;

// a swagger api generálás miatt szükséges
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/hello_im")
@Api(value = "/hello_im", description = "Callback listener")
public class CallbackService {


    @GET
    @Consumes(MediaType.TEXT_PLAIN)
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
        System.out.println("I called with message:" + message);
        return message;
    }
}
