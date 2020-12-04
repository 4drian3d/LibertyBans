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
package space.arim.libertybans.core;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import space.arim.omnibus.registry.Registration;
import space.arim.omnibus.registry.RegistryPriorities;

import space.arim.libertybans.api.LibertyBans;
import space.arim.libertybans.core.config.Configs;
import space.arim.libertybans.core.database.DatabaseManager;
import space.arim.libertybans.core.env.EnvironmentManager;
import space.arim.libertybans.core.service.AsynchronicityManager;

@Singleton
public class LifecycleGodfather extends AbstractBaseFoundation {

	private final AsynchronicityManager asyncManager;
	private final Configs configs;
	private final DatabaseManager databaseManager;
	private final EnvironmentManager envManager;

	private final LibertyBans api;
	private Registration<LibertyBans> apiRegistration;

	@Inject
	public LifecycleGodfather(AsynchronicityManager asyncManager, Configs configs, DatabaseManager databaseManager,
			EnvironmentManager envManager, LibertyBans api) {
		this.asyncManager = asyncManager;
		this.configs = configs;
		this.databaseManager = databaseManager;
		this.envManager = envManager;

		this.api = api;
	}

	@Override
	void startup0() {
		asyncManager.startup();
		configs.startup();
		databaseManager.startup();
		envManager.startup();

		apiRegistration = api.getOmnibus().getRegistry()
				.register(LibertyBans.class, RegistryPriorities.LOWEST, api, "Reference impl");
	}

	@Override
	void restart0() {
		envManager.shutdown();
		asyncManager.restart();
		configs.restart();
		databaseManager.restart();
		envManager.startup();
	}

	@Override
	void shutdown0() {
		envManager.shutdown();
		configs.shutdown();
		databaseManager.shutdown();
		asyncManager.shutdown();

		api.getOmnibus().getRegistry().unregister(LibertyBans.class, apiRegistration);
	}
	
}
