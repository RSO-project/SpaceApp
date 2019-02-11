package si.fri.rso.samples.rents.entities;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Entity(name = "rents")
@NamedQueries(value =
        {
                @NamedQuery(name = "Rent.getAll", query = "SELECT o FROM rents o"),
                @NamedQuery(name = "Rent.findByClient", query = "SELECT o FROM rents o WHERE o.clientId = " +
                        ":clientId"),
                @NamedQuery(name = "Rent.fullData", query = "SELECT o, c, b FROM rents o LEFT JOIN clients c ON o.clientId = c.id LEFT JOIN bikes b ON o.bikeId = b.id")
        })
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String status;

    private String title;

    @Column(name = "bike_id")
    private Integer bikeId;

    @Column(name = "client_id")
    private Integer clientId;

    public Double price;

    public Instant startDate;

    public Instant endDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getBikeId() {
        return bikeId;
    }

    public void setBikeId(Integer bikeId) {
        this.bikeId = bikeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
