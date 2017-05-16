package com.wavelabs.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.wavelabs.model.Message;
import com.wavelabs.model.TimeSlots;
import com.wavelabs.service.TimeSlotService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/timeslot")
@Api(value = "/timeslot", description = "This API will gives all actions of the timeslots")
public class TimeSlotResource {

	@GET
	@Path("/all/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all timeslots", notes = "This resources gives you the all the timeslots in the database")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully gets the all the timeslots"),
			@ApiResponse(code = 404, message = "The timeslots not return properly"),
			@ApiResponse(code = 500, message = "There is problem in server side") })
	public Response getAllTimeSlots(@ApiParam(name = "id", value = "provider id") @PathParam("id") int id) {
		try {
			GenericEntity<List<TimeSlots>> allTimeslots = new GenericEntity<List<TimeSlots>>(
					TimeSlotService.getTimeSlotsByProvider(id)) {
			};
			return Response.ok(allTimeslots).build();

		} catch (Exception e) {
			Message message = new Message();
			message.setStatus(404);
			message.setMessage("Something went wrong with getting all timeslots of bookie");
			GenericEntity<Message> messages = new GenericEntity<Message>(message) {
			};
			return Response.ok(messages).build();
		}

	}
}
