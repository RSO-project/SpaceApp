package si.fri.rso.samples.rents.entities;

import javax.persistence.*;

@Entity(name = "bikes")
@NamedQueries(value =
        {
                @NamedQuery(name = "Bike.getAll", query = "SELECT o FROM bikes o"),
                @NamedQuery(name = "Bike.findByBike", query = "SELECT o FROM bikes o WHERE o.id = " +
                        ":id")
        })
public class Bike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String model;

    private String size;

    private boolean enabled = true;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public boolean isEnabled(){ return enabled; }

    public void setEnabled(boolean enabled) { this.enabled = enabled;}

}
