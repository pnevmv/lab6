package common.model;

import java.io.Serializable;

/**
 * Bands studio.
 */
public class Studio implements Serializable {

    private String address; //Поле не может быть null
    public Studio(String address) {
        this.address = address;
    }

    /**
     * @return Address of studio.
     */
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return address;
    }

    @Override
    public int hashCode() {
        return address.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof Studio) {
            Studio studioObj = (Studio) obj;
            return address.equals(studioObj.getAddress());
        }
        return false;
    }
}