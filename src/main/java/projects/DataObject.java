package projects;

public class DataObject {
    private String id;
    private String text;

    public DataObject(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}