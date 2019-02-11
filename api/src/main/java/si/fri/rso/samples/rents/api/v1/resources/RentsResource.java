package si.fri.rso.samples.rents.api.v1.resources;

import si.fri.rso.samples.rents.entities.Rent;
import si.fri.rso.samples.rents.services.RentsBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
@Path("/rents")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RentsResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private RentsBean rentsBean;

    @GET
    public Response getRents() {

        List<Rent> rents = rentsBean.getRents(uriInfo);

        return Response.ok(rents).build();
    }

    @GET
    @Path("/{rentId}")
    public Response getRent(@PathParam("rentId") Integer rentId) {

        Rent rent = rentsBean.getRent(rentId);

        if (rent == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(rent).build();
    }

    @GET
    @Path("/full")
    public Response getRentsFull() {
        List<Rent> rents = rentsBean.getRentsFull();
        return Response.ok(rents).build();
    }

    @POST
    public Response createRent(Rent rent) {

        if (rent.getTitle() == null || rent.getTitle().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            rent = rentsBean.createRent(rent);
        }

        if (rent.getId() != null) {
            return Response.status(Response.Status.CREATED).entity(rent).build();
        } else {
            return Response.status(Response.Status.CONFLICT).entity(rent).build();
        }
    }

    @PUT
    @Path("{rentId}")
    public Response putRent(@PathParam("rentId") Integer rentId, Rent rent) {

        rent = rentsBean.putRent(rentId, rent);

        if (rent == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            if (rent.getId() != null)
                return Response.status(Response.Status.OK).entity(rent).build();
            else
                return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }

    @PATCH
    @Path("{rentId}/completed")
    public Response rentCompleted(@PathParam("rentId") Integer rentId) {

        Rent rent = rentsBean.completeRent(rentId);

        if (rent == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            if (rent.getId() != null)
                return Response.status(Response.Status.OK).entity(rent).build();
            else
                return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }

    @DELETE
    @Path("{rentId}")
    public Response deleteRent(@PathParam("rentId") Integer rentId) {

        boolean deleted = rentsBean.deleteRent(rentId);

        if (deleted) {
            return Response.status(Response.Status.GONE).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
