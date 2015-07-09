package me.thijmen321.buildingutil;

/**
 * Copyright (C) 2015 Bram Hagens & Thijmen Kuipers
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
