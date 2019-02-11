package si.fri.rso.samples.rents.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.samples.rents.entities.Rent;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.time.Instant;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class RentsBean {

    private Logger log = Logger.getLogger(RentsBean.class.getName());

    @Inject
    private EntityManager em;

    public List<Rent> getRents(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery())
                .defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, Rent.class, queryParameters);

    }

    public List<Rent> getRentsFull() {
        TypedQuery<Rent> query = em.createNamedQuery("Rent.fullData", Rent.class);

        List<Rent> rents = query.getResultList();

        return rents;
    }

    public Rent getRent(Integer rentId) {

        Rent rent = em.find(Rent.class, rentId);

        if (rent == null) {
            throw new NotFoundException();
        }

        return rent;
    }

    public Rent createRent(Rent rent) {

        try {
            beginTx();
            em.persist(rent);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return rent;
    }

    public Rent putRent(Integer rentId, Rent rent) {

        Rent c = em.find(Rent.class, rentId);

        if (c == null) {
            return null;
        }

        try {
            beginTx();
            rent.setId(c.getId());
            rent = em.merge(rent);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return rent;
    }

    public Rent completeRent(Integer rentId) {

        Rent rent = em.find(Rent.class, rentId);

        if (rent == null) {
            throw new NotFoundException();
        }

        try {
            beginTx();
            rent.setStatus("completed");
            //rent.setCompleted(Instant.now());
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return rent;
    }

    public boolean deleteRent(Integer rentId) {

        Rent rent = em.find(Rent.class, rentId);

        if (rent != null) {
            try {
                beginTx();
                em.remove(rent);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
            }
        } else
            return false;

        return true;
    }

    public void setRentStatus(Integer rentId, String status) {

        Rent rent = em.find(Rent.class, rentId);

        if (rent == null) {
            throw new NotFoundException();
        }

        try {
            beginTx();
            rent.setStatus(status);
            em.merge(rent);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

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
