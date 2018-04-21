package hu.fuz.service;

// a swagger api generálás miatt szükséges

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Enumeration;

@Path("/hello_im")
@Api(value = "/hello_im", description = "Callback listener")
public class CallbackService {


    @POST
//    @Consumes({
//            MediaType.TEXT_PLAIN,
//            MediaType.
//            MediaType.APPLICATION_JSON})
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
            @Context HttpServletRequest request,
            @ApiParam(value = "Body callback message", required = false) String message) throws IOException {
        StringBuilder msg = new StringBuilder();
        msg.append("<################## I called with message: ##################");
        msg.append("\n");
        msg.append("METHOD: " + request.getMethod());
        msg.append("\n");
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            msg.append("Header Name - " + headerName + ", Value - " + request.getHeader(headerName));
            msg.append("\n");
        }

        Enumeration<String> params = request.getParameterNames();
        while(params.hasMoreElements()){
            String paramName = params.nextElement();
            msg.append("Parameter Name - "+paramName+", Value - "+request.getParameter(paramName));
            msg.append("\n");
        }

        msg.append("\n" + "BODY:" + "\n");
        msg.append(message);
        msg.append("\n");

        msg.append(">################## I called with message: ##################");
        System.out.println(msg.toString());
        return "Thank you IM!";
    }
}
