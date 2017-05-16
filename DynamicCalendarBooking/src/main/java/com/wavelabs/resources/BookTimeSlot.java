package com.wavelabs.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.wavelabs.model.Bookings;
import com.wavelabs.model.Ids;
import com.wavelabs.model.Message;
import com.wavelabs.service.TimeSlotService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/booking")
@Api(value = "/booking", description = "This API will work on the bookings")
public class BookTimeSlot {

	Logger logger = Logger.getLogger(BookTimeSlot.class);

	@PUT
	@Path("/timeslots/{id}")
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "The timeslot successfully booked"),
			@ApiResponse(code = 404, message = "the timeslot is not booked"),
			@ApiResponse(code = 500, message = "There is problem in server") })
	@ApiOperation(value = "booking will done by the receiver", notes = "This resource will book the timeslot for particular receiver based on the receiver_id and timeslot_id")
	public Response bookTimeSlot(
			@ApiParam(name = "receiverid", value = "The id of the receiver") @QueryParam("receiverid") int receiverid,
			@ApiParam(name = "id", value = "The id of the timeslot") @PathParam("id") String ids) {
		System.out.println("receiverid id  " + receiverid);
		System.out.println("timeslot id  " + ids);
		logger.info("-----------------BOOKING A TIMESLOT is launched-----------------");
		try {
			GenericEntity<String> bookslot = new GenericEntity<String>(TimeSlotService.bookTimeSlot(receiverid, ids)) {
			};
			return Response.ok(bookslot).build();
		} catch (Exception e) {
			Message message = new Message();
			message.setStatus(404);
			message.setMessage("Something went wrong with booking");
			GenericEntity<Message> messages = new GenericEntity<Message>(message) {
			};
			return Response.ok(messages).build();
		}
	}

	@PUT
	@Path("/confirm")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "The bookings confirmed"),
			@ApiResponse(code = 404, message = "the bookings not confirmed"),
			@ApiResponse(code = 500, message = "There is problem in server") })
	@ApiOperation(value = "booking confirmation will done by the provider", notes = "This resource confirms the bookings")
	public Response confirmTimeSlot(Ids[] ids) {
		System.out.println("timeslot id  " + ids);
		logger.info("-----------------------CONFIRM BOOKING launched-----------------");

		try {
			GenericEntity<String> confirmbook = new GenericEntity<String>(TimeSlotService.confirmBooking(ids)) {
			};
			return Response.ok(confirmbook).build();
		} catch (Exception e) {
			Message message = new Message();
			message.setStatus(404);
			message.setMessage("Something went wrong with confirm bookings");
			GenericEntity<Message> messages = new GenericEntity<Message>(message) {
			};
			return Response.ok(messages).build();
		}
	}

	@PUT
	@Path("/cancel")
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "The bookings cancellation success"),
			@ApiResponse(code = 404, message = "the bookings cancelltion fail"),
			@ApiResponse(code = 500, message = "There is problem in server") })
	@ApiOperation(value = "booking cancellation will done by the provider", notes = "This resource will cancel the booking of a particuler timeslot_id")
	public Response cancelTimeSlot(Ids[] ids) {

		try {
			logger.info("--------------------CANCELLING BOOKING launched-----------------------");
			GenericEntity<String> cancellbook = new GenericEntity<String>(TimeSlotService.cancelBooking(ids)) {
			};
			return Response.ok(cancellbook).build();

		} catch (Exception e) {
			Message message = new Message();
			message.setStatus(404);
			message.setMessage("Something went wrong with cancelling bookings");
			GenericEntity<Message> messages = new GenericEntity<Message>(message) {
			};
			return Response.ok(messages).build();
		}
	}

	@PUT
	@Path("/modify/{ids}")
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "The timeslots modified"),
			@ApiResponse(code = 404, message = "the timeslot is not modified"),
			@ApiResponse(code = 500, message = "There is problem in server") })
	@ApiOperation(value = "booking modifications will done by the provider", notes = "This resource will modifies the bookings")
	public Response modifyTimeSlot(@PathParam("ids") String ids) {
		try {
			logger.info("--------------------MODIFYING BOOKING launched-----------------------");
			GenericEntity<String> modifybooking = new GenericEntity<String>(TimeSlotService.modifyBooking(ids)) {
			};
			return Response.ok(modifybooking).build();
		} catch (Exception e) {
			Message message = new Message();
			message.setStatus(404);
			message.setMessage("Something went wrong with modifying bookings");
			GenericEntity<Message> messages = new GenericEntity<Message>(message) {
			};
			return Response.ok(messages).build();
		}
	}

	@PUT
	@Path("/decline/{ids}")
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "The decline process done"),
			@ApiResponse(code = 404, message = "the decline process fail"),
			@ApiResponse(code = 500, message = "There is problem in server") })
	@ApiOperation(value = "booking decline will done by the provider", notes = "This resource will bookings")
	public Response declineTimeSlot(@PathParam("ids") String ids) {
		try {
			logger.info("--------------------DECLINE BOOKING launched-----------------------");
			GenericEntity<String> declinebooking = new GenericEntity<String>(TimeSlotService.declineBooking(ids)) {
			};
			return Response.ok(declinebooking).build();
		} catch (Exception e) {
			Message message = new Message();
			message.setStatus(404);
			message.setMessage("Something went wrong with decline booking");
			GenericEntity<Message> messages = new GenericEntity<Message>(message) {
			};
			return Response.ok(messages).build();
		}
	}
	
	@GET
	@Path("/process/{id}")
	@Produces(MediaType.APPLICATION_JSON )
	@ApiResponses(value = { @ApiResponse(code = 200, message = "The get bookings process done"),
			@ApiResponse(code = 404, message = "the get bookings process fail"),
			@ApiResponse(code = 500, message = "There is problem in server") })
	@ApiOperation(value = "provider will gets all the bookings need to accept", notes = "Getting all bookings need to acept")
	public Response getBookings(@PathParam("id") int id) {
		try {
			logger.info("--------------------GETTING BOOKINGS launched-----------------------");
			GenericEntity<List<Bookings>> declinebooking = new GenericEntity<List<Bookings>>(TimeSlotService.getBookings(id)) {
			};
			return Response.ok(declinebooking).build();
		} catch (Exception e) {
			Message message = new Message();
			message.setStatus(404);
			message.setMessage("Something went wrong with getting bookings");
			GenericEntity<Message> messages = new GenericEntity<Message>(message) {
			};
			return Response.ok(messages).build();
		}
	}
}
