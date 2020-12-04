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

import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import space.arim.libertybans.bootstrap.StartupException;
import space.arim.libertybans.core.Part;
import space.arim.libertybans.core.config.Configs;

@Singleton
public class EnvironmentManager implements Part {

	private final Environment environment;
	private final Configs configs;
	
	private final List<PlatformListener> listeners = new ArrayList<>();
	
	@Inject
	public EnvironmentManager(Environment environment, Configs configs) {
		this.environment = environment;
		this.configs = configs;
	}

	@Override
	public void startup() {
		listeners.addAll(environment.createListeners());
		for (String alias : configs.getMainConfig().commands().aliases()) {
			listeners.add(environment.createAliasCommand(alias));
		}
		listeners.forEach(PlatformListener::register);
	}
	
	@Override
	public void restart() {
		throw new StartupException("Internal error, EnvironmentManager#restart should not be called");
	}

	@Override
	public void shutdown() {
		listeners.forEach(PlatformListener::unregister);
	}
	
}
