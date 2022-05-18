package id.go.kominfo.andi.sqlitepractice.model;

import java.io.Serializable;
import java.util.Objects;

public class Friend implements Serializable {
    private int id;
    private String name;
    private String address;
    private String phone;

    public Friend(int id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public Friend(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friend friend = (Friend) o;
        return id == friend.id && name.equals(friend.name) && Objects.equals(address, friend.address) && Objects.equals(phone, friend.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, phone);
    }

    @Override
    public String toString() {
        return "Friend{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
