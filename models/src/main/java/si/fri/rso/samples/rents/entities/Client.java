package si.fri.rso.samples.rents.entities;

import javax.persistence.*;

@Entity(name = "clients")
@NamedQueries(value =
        {
                @NamedQuery(name = "Client.getAll", query = "SELECT o FROM rents o"),
                @NamedQuery(name = "Client.findByClient", query = "SELECT o FROM rents o WHERE o.id = " +
                        ":id")
        })
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String surname;

    private String phone;

    private String address;

    private boolean enabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
