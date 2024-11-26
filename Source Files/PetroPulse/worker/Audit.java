package worker;
public class Audit {
    private int entityID;
    private String entity;
    private String action;

    // Constructor
    public Audit(int entityID, String entity, String action) {
        this.entityID = entityID;
        this.entity = entity;
        this.action = action;
    }

    // Getters and Setters
    public int getEntityID() {
        return entityID;
    }

    public void setEntityID(int entityID) {
        this.entityID = entityID;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
