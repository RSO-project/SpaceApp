package si.fri.rso.samples.rents.api.v1.resources;

import si.fri.rso.samples.rents.entities.Client;
import si.fri.rso.samples.rents.services.ClientsBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
@Path("/clients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientsResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private ClientsBean clientsBean;

    @GET
    public Response getClients() {

        List<Client> clients = clientsBean.getClients(uriInfo);

        return Response.ok(clients).build();
    }

    @GET
    @Path("/{clientId}")
    public Response getClient(@PathParam("clientId") Integer clientId) {

        Client client = clientsBean.getClient(clientId);

        if (client == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(client).build();
    }

    @POST
    public Response createClient(Client client) {

        /*if (client.getTitle() == null || client.getTitle().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {*/
            client = clientsBean.createClient(client);
        //}

        if (client.getId() != null) {
            return Response.status(Response.Status.CREATED).entity(client).build();
        } else {
            return Response.status(Response.Status.CONFLICT).entity(client).build();
        }
    }

    @PUT
    @Path("{clientId}")
    public Response putClient(@PathParam("clientId") Integer clientId, Client client) {

        client = clientsBean.putClient(clientId, client);

        if (client == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            if (client.getId() != null)
                return Response.status(Response.Status.OK).entity(client).build();
            else
                return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }

    @DELETE
    @Path("{clientId}")
    public Response deleteClient(@PathParam("clientId") Integer clientId) {

        boolean deleted = clientsBean.deleteClient(clientId);

        if (deleted) {
            return Response.status(Response.Status.GONE).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
