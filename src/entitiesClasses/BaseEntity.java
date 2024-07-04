package entitiesClasses;

import java.util.UUID;

public abstract class BaseEntity {
	
    private String id;

//	==============================================================================================
//											CONSTRUCTORS
//	==============================================================================================
	
    public BaseEntity() {
        this.id = generateId();
    }


//	==============================================================================================
//											GETTERS AND SETTERS
//	==============================================================================================
	
    public String getId() {
        return this.id;
    }

	public void setId(String id) {
		this.id = id;
	}

//	==============================================================================================
//											OTHER METHODS
//	==============================================================================================
	
    private String generateId() {
        return UUID.randomUUID().toString();
    }
    
}
