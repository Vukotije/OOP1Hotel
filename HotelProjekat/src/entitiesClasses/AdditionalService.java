package entitiesClasses;

public class AdditionalService extends BaseEntity{
	
	private String name;

//	==============================================================================================
//											CONSTRUCTORS
//	==============================================================================================
	
	public AdditionalService(String name) {
		super();
		setName(name);
	}

	public AdditionalService(String name, String id) {
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
		return "AdditionalService [name=" + name + "]";
	}
	
}