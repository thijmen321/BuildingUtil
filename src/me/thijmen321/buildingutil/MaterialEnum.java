package me.thijmen321.buildingutil;

public enum MaterialEnum {
	DIAMOND(5),
	GOLD(4),
	IRON(3),
	STONE(2),
	WOOD(1);
	
	private int value;
	
	private MaterialEnum(int i)
	{
		value = i;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public static MaterialEnum findByValue(int i)
	{
		for(MaterialEnum m : values())
			if(m.getValue() == i) return m;
		return null;
	}
}
