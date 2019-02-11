package si.fri.rso.samples.rents.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.samples.rents.entities.Bike;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class BikesBean {

    private Logger log = Logger.getLogger(BikesBean.class.getName());

    @Inject
    private EntityManager em;

    public List<Bike> getBikes(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery())
                .defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, Bike.class, queryParameters);

    }

    public Bike getBike(Integer bikeId) {

        Bike bike = em.find(Bike.class, bikeId);

        if (bike == null) {
            throw new NotFoundException();
        }

        return bike;
    }

    public Bike createBike(Bike bike) {

        try {
            beginTx();
            em.persist(bike);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return bike;
    }

    public Bike putBike(Integer bikeId, Bike bike) {

        Bike c = em.find(Bike.class, bikeId);

        if (c == null) {
            return null;
        }

        try {
            beginTx();
            bike.setId(c.getId());
            bike = em.merge(bike);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return bike;
    }

    public boolean deleteBike(Integer bikeId) {

        Bike bike = em.find(Bike.class, bikeId);

        if (bike != null) {
            try {
                beginTx();
                em.remove(bike);
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
