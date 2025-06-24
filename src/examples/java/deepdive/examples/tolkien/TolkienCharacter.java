package deepdive.examples.tolkien;


import static deepdive.examples.tolkien.Type.*;
import java.util.Arrays;
import java.util.List;


/**
 * Modeled after https://github.com/assertj/assertj-core/blob/main/src/test/java/org/assertj/core/data/TolkienCharacter.java
 */
public class TolkienCharacter
{
	public static final TolkienCharacter frodo 		= new TolkienCharacter("Frodo", 33, HOBBIT);
	public static final TolkienCharacter sam 		= new TolkienCharacter("Sam", 38, HOBBIT);
	public static final TolkienCharacter merry 		= new TolkienCharacter("Merry", 36, HOBBIT);
	public static final TolkienCharacter pippin 	= new TolkienCharacter("Pippin", 28, HOBBIT);
	public static final TolkienCharacter gandalf 	= new TolkienCharacter("Gandalf", 2020, MAIA);
	public static final TolkienCharacter gimli 		= new TolkienCharacter("Gimli", 139, DWARF);
	public static final TolkienCharacter legolas 	= new TolkienCharacter("Legolas", 1000, ELF);
	public static final TolkienCharacter aragorn 	= new TolkienCharacter("Aragorn", 87, MAN);
	public static final TolkienCharacter boromir 	= new TolkienCharacter("Boromir", 37, MAN);
	public static final TolkienCharacter sauron 	= new TolkienCharacter("Sauron", 50000, MAIA);
	public static final TolkienCharacter galadriel 	= new TolkienCharacter("Galadriel", 3000, ELF);
	public static final TolkienCharacter elrond		= new TolkienCharacter("Elrond", 3000, ELF);
	public static final TolkienCharacter guruk 		= new TolkienCharacter("Guruk", 20, ORC);
	public static final TolkienCharacter isildur 	= new TolkienCharacter("Isildur", 100, MAN);
	public static final List<TolkienCharacter> fellowshipOfTheRing = Arrays.asList(
		frodo, sam, merry, pippin, gandalf, legolas, gimli, aragorn, boromir); 
	public static final List<TolkienCharacter> orcsWithHobbitPrisoners = Arrays.asList(
		guruk, merry, pippin); 	 
		  
	private final int age;
	private final String name;
	private final Type type;


	public TolkienCharacter(String name, int age, Type type)
	{
		this.name = name;
		this.age = age;
		this.type = type;
	}
	

	public Type getType()
	{
		return type;
	}
	

	public String getName()
	{
		return name;
	}
	

	public int getAge()
	{
		return this.age;
	}
	

	@Override public String toString()
	{
		return name + ' ' + age + " years old " + type;
	}
}
