package si.fri.rso.samples.rents.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.samples.rents.entities.Client;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class ClientsBean {

    private Logger log = Logger.getLogger(ClientsBean.class.getName());

    @Inject
    private EntityManager em;

    public List<Client> getClients(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery())
                .defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, Client.class, queryParameters);

    }

    public Client getClient(Integer clientId) {

        Client client = em.find(Client.class, clientId);

        if (client == null) {
            throw new NotFoundException();
        }

        return client;
    }

    public Client createClient(Client client) {

        try {
            beginTx();
            em.persist(client);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return client;
    }

    public Client putClient(Integer clientId, Client client) {

        Client c = em.find(Client.class, clientId);

        if (c == null) {
            return null;
        }

        try {
            beginTx();
            client.setId(c.getId());
            client = em.merge(client);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return client;
    }

    public boolean deleteClient(Integer clientId) {

        Client client = em.find(Client.class, clientId);

        if (client != null) {
            try {
                beginTx();
                em.remove(client);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
            }
        } else
            return false;

        return true;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();
    }

    private void commitTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().commit();
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().rollback();
    }
}
