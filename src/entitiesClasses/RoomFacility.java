package entitiesClasses;

public class RoomFacility extends BaseEntity{
	
	private String name;

//	==============================================================================================
//											CONSTRUCTORS
//	==============================================================================================
	
	public RoomFacility(String name) {
		super();
		setName(name);
	}

	public RoomFacility(String name, String id) {
		setName(name);
		setId(id);
	}


//	==============================================================================================
//											GETERS & SETERS
//	==============================================================================================
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

//	==============================================================================================
//											TO STRING
//	==============================================================================================
	

	@Override
	public String toString() {
		return "RoomFacility [name=" + name + "]";
	}
	
}