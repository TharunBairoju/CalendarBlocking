package com.wavelabs.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.wavelabs.model.Message;
import com.wavelabs.model.Receiver;
import com.wavelabs.service.ReceiverService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/receiver")
@Api(value = "/receiver", description = "This API is about Service receiver")
public class ReceiverResource {

	@POST
	@Path("/persist")
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Persist the booker", notes = "This API will persist the recevier which takes the input in the form of JSON")
	@ApiResponses({ @ApiResponse(code = 200, message = "The receiver persisted successfully"),
			@ApiResponse(code = 404, message = "The receiver not persisted"),
			@ApiResponse(code = 500, message = "There is some problem in server") })
	public Response persistConsultant(Receiver receiver) {

		try {
			GenericEntity<Receiver> addReceiver = new GenericEntity<Receiver>(
					ReceiverService.createReceiver(receiver)) {
			};
			return Response.ok(addReceiver).build();
		} catch (Exception e) {
			Message message = new Message();
			message.setStatus(404);
			message.setMessage("Something went wrong with persisting receiver");
			GenericEntity<Message> messages = new GenericEntity<Message>(message) {
			};
			return Response.ok(messages).build();
		}
	}
}
