/* 
 * LibertyBans-api
 * Copyright © 2020 Anand Beh <https://www.arim.space>
 * 
 * LibertyBans-api is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * LibertyBans-api is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with LibertyBans-api. If not, see <https://www.gnu.org/licenses/>
 * and navigate to version 3 of the GNU Affero General Public License.
 */
package space.arim.libertybans.api;

/**
 * A server operator able to punish victims
 * 
 * @author A248
 *
 */
public class Operator {
	
	private final OperatorType type;
	
	Operator(OperatorType type) {
		this.type = type;
	}
	
	/**
	 * Gets the type of the operator
	 * 
	 * @return the type
	 */
	public OperatorType getType() {
		return type;
	}

	/**
	 * Type of operators
	 * 
	 * @author A248
	 *
	 */
	public enum OperatorType {
		
		/**
		 * A player with appropriate permissions, e.g. a player with the Admin rank
		 * 
		 */
		PLAYER,
		/**
		 * The server console, which has all permissions
		 * 
		 */
		CONSOLE
		
	}
	
}
