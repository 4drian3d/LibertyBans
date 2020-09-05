/* 
 * LibertyBans-integrationtest
 * Copyright © 2020 Anand Beh <https://www.arim.space>
 * 
 * LibertyBans-integrationtest is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * LibertyBans-integrationtest is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with LibertyBans-integrationtest. If not, see <https://www.gnu.org/licenses/>
 * and navigate to version 3 of the GNU Affero General Public License.
 */
package space.arim.libertybans.it.env.platform;

import java.util.UUID;

import space.arim.uuidvault.plugin.TestableUUIDVault;

public class QuackUUIDVault extends TestableUUIDVault {

	private final QuackPlatform platform;
	
	public QuackUUIDVault(QuackPlatform platform) {
		this.platform = platform;
	}
	
	@Override
	public UUID resolveNatively(String name) {
		QuackPlayer player = platform.getPlayer(name);
		return (player == null) ? null : player.getUniqueId();
	}
	
	@Override
	public String resolveNatively(UUID uuid) {
		QuackPlayer player = platform.getPlayer(uuid);
		return (player == null) ? null : player.getName();
	}
	
}
