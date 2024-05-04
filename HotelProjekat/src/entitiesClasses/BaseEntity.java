package entitiesClasses;

import java.util.UUID;

public abstract class BaseEntity {
	
    private final String id;

    public BaseEntity() {
        this.id = generateId();
    }

    public String getId() {
        return this.id;
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }
}
