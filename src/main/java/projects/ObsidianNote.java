package projects;

public class ObsidianNote {

    private String fileName;
    private String id;

    public ObsidianNote(String fileName, String id) {
        this.id = id;
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getId() {
        return id;
    }
}
