package datamodel;

public class Batter {

    String id;

    String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Batter{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
