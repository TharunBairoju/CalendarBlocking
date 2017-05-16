package com.wavelabs.resources;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.wavelabs.model.Message;
import com.wavelabs.model.Provider;
import com.wavelabs.model.SlotInfo;
import com.wavelabs.service.ProviderrService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/provider")
@Api(value = "/provider", description = "This API is mainly concentrate on the role of provider, all possible operations of provider will done in this API")
public class ProviderResource {

	@Path("/persist")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "persist the provider", notes = "This method takes the input as json provider object and gives the persisted provider object")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "The bookie successfully created"),
			@ApiResponse(code = 404, message = "The provider not created"),
			@ApiResponse(code = 500, message = "there is some problem in server") })
	public Response persistDoctor(Provider provider) {
		try {
			GenericEntity<Provider> persistedprovider = new GenericEntity<Provider>(
					ProviderrService.persistProvider(provider)) {
			};
			return Response.ok(persistedprovider).build();
		} catch (Exception e) {
			Message message = new Message();
			message.setStatus(404);
			message.setMessage("Something went wrong with persisting");
			GenericEntity<Message> messages = new GenericEntity<Message>(message) {
			};
			return Response.ok(messages).build();
		}
	}

	@POST
	@Path("/generateCalendar")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Generates a calendar", notes = "This will creates the slot accoding to input provided by the provider")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "calendar created successfully"),
			@ApiResponse(code = 404, message = "there is some problem in slot creation"),
			@ApiResponse(code = 500, message = "Problem in server") })
	public Response createSlots(@ApiParam(value = "Id of the provider to create slot") @QueryParam("id") int id,
			@ApiParam(value = "starting date of slot creation") @FormParam("fromdate") String fromdate,
			@ApiParam(value = "Ending date of slot creation") @FormParam("todate") String todate,
			@ApiParam(value = "starting time of provider availability") @FormParam("fromtime") String fromtime,
			@ApiParam(value = "End time of provider availability") @FormParam("totime") String totime,
			@ApiParam(value = "slot duration in minutes") @FormParam("slotDuration") int slotDuration)
			throws ParseException {
		try {
			DateFormat parser = new SimpleDateFormat("dd/MM/yyyy");
			Date fromDate = parser.parse(fromdate);
			Date toDate = parser.parse(todate);
			List<SlotInfo> createslotlist = ProviderrService.providerSlots(id, fromDate, toDate, Time.valueOf(fromtime),
					Time.valueOf(totime), slotDuration);
			GenericEntity<List<SlotInfo>> slotlist = new GenericEntity<List<SlotInfo>>(createslotlist) {
			};
			return Response.ok(slotlist).build();

		} catch (Exception e) {
			Message message = new Message();
			message.setStatus(404);
			message.setMessage("Something went wrong with calendar generation");
			GenericEntity<Message> messages = new GenericEntity<Message>(message) {
			};
			return Response.ok(messages).build();
		}
	}

	@PUT
	@Path("/createAvailability/{id}")
	@Produces(MediaType.TEXT_HTML)
	@ApiOperation(value = "create availability", notes = "This will creates the availability accoding to input provided by the provider")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "slot availability creation done"),
			@ApiResponse(code = 404, message = "there is some problem in slot creation"),
			@ApiResponse(code = 500, message = "Problem in server") })
	public Response createavailability(
			@ApiParam(name = "id", value = "ids of the timeslots") @PathParam("id") String id) {

		try {
			String status = ProviderrService.createAvailability(id);
			GenericEntity<String> message = new GenericEntity<String>(status) {
			};
			return Response.ok(message).build();
		} catch (Exception e) {
			Message message = new Message();
			message.setStatus(404);
			message.setMessage("Something went wrong in creation of availability");
			GenericEntity<Message> messages = new GenericEntity<Message>(message) {
			};
			return Response.ok(messages).build();
		}

	}

	@GET
	@Path("/all")
	@ApiOperation(value = "Gets all the provider", notes = "This will gets the all the providers available in the DB")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "providers successfully got from the DB"),
			@ApiResponse(code = 404, message = "there is some problem in getting providers"),
			@ApiResponse(code = 500, message = "Problem in server") })
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDoctorsList() {
		try {
			GenericEntity<List<Provider>> providerlist = new GenericEntity<List<Provider>>(
					ProviderrService.getAllProviders()) {
			};
			return Response.ok(providerlist).build();
		} catch (Exception e) {
			Message message = new Message();
			message.setStatus(404);
			message.setMessage("Something went wrong in getting all bookie");
			GenericEntity<Message> messages = new GenericEntity<Message>(message) {
			};
			return Response.ok(messages).build();
		}

	}
	
	/*@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("bookingsInfo/{id}")
	public Response getBookingInfo(@PathParam("id") int id){
		try {
			return Response.ok(ProviderrService.getBookedInformation(id)).build();
		} catch (Exception e) {
			Message message = new Message();
			message.setStatus(404);
			message.setMessage("Something went wrong with getting booked slot data");
			GenericEntity<Message> messages = new GenericEntity<Message>(message) {
			};
			return Response.ok(messages).build();
		}
	}*/
	
	
	

	@GET
	@Path("/slotdata")
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "slotnfo data of a provider", notes = "This will gets the particular provider's slotinfodata based on the provider_id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The slotinfo data retrieved based on provider_id successfully"),
			@ApiResponse(code = 404, message = "there is some problem in getting slotinfo"),
			@ApiResponse(code = 500, message = "Problem in server") })
	public Response getBookedSlotData(@QueryParam("id") int id) {
		try {
			return Response.ok(ProviderrService.getBookedInformation(id)).build();
		} catch (Exception e) {
			Message message = new Message();
			message.setStatus(404);
			message.setMessage("Something went wrong with getting booked slot data");
			GenericEntity<Message> messages = new GenericEntity<Message>(message) {
			};
			return Response.ok(messages).build();
		}
	}

}
