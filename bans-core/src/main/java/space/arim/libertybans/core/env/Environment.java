/* 
 * LibertyBans-core
 * Copyright © 2020 Anand Beh <https://www.arim.space>
 * 
 * LibertyBans-core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * LibertyBans-core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with LibertyBans-core. If not, see <https://www.gnu.org/licenses/>
 * and navigate to version 3 of the GNU Affero General Public License.
 */
package space.arim.libertybans.core.env;

import java.util.Set;

import space.arim.uuidvault.api.UUIDVault;

import space.arim.api.env.PlatformHandle;

import space.arim.libertybans.core.Part;

public interface Environment extends Part {
	
	PlatformHandle getPlatformHandle();
	
	EnvEnforcer getEnforcer();
	
	Set<PlatformListener> createListeners();
	
	PlatformListener createAliasCommand(String command);
	
	default UUIDVault getUUIDVault() {
		return UUIDVault.get();
	}
	
}
