package deepdive.examples.tolkien;


import static deepdive.examples.tolkien.Alignment.*;


public enum Type
{
	HOBBIT("Hobbit", false, GOOD), 
	MAIA("Maia", true, GOOD), 
	MAN("Man", false, NEUTRAL),
	ELF("Elf", true, GOOD),
	DWARF("Dwarf", false, GOOD), 
	ORC("Orc", false, EVIL);

	private final String name;
	public final boolean immortal;
	public final Alignment alignment;


	Type(String name, boolean immortal, Alignment alignment)
	{
		this.name = name;
		this.immortal = immortal;
		this.alignment = alignment;
	}

	
	public String getName()
	{
		return name;
	}

	
	public String getFullname()
	{
		return immortal ? "immortal " + name : name;
	}
	

	@Override public String toString()
	{
		return "Type [name=" + name + ", immortal=" + immortal + "]";
	}
}