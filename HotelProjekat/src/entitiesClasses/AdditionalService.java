package entitiesClasses;

public class AdditionalService extends BaseEntity{
	
	private String name;

	public AdditionalService(String name) {
		super();
		setName(name);
	}

	@Override
	public String toString() {
		return "AdditionalService [name=" + name + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
