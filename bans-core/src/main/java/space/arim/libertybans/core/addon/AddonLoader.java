/*
 * LibertyBans
 * Copyright © 2022 Anand Beh
 *
 * LibertyBans is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * LibertyBans is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with LibertyBans. If not, see <https://www.gnu.org/licenses/>
 * and navigate to version 3 of the GNU Affero General Public License.
 */

package space.arim.libertybans.core.addon;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;

public final class AddonLoader {

	private final ServiceLoader<AddonProvider> addonProviders;

	private AddonLoader(ServiceLoader<AddonProvider> addonProviders) {
		this.addonProviders = addonProviders;
	}

	private Set<AddonBindModule> addonBindModules() {
		Set<AddonBindModule> addonBindModules = new HashSet<>();
		for (AddonProvider addonProvider : addonProviders) {
			addonBindModules.addAll(Arrays.asList(addonProvider.bindModules()));
		}
		return Collections.unmodifiableSet(addonBindModules);
	}

	public static Set<AddonBindModule> loadAddonBindModules() {
		return new AddonLoader(
				ServiceLoader.load(AddonProvider.class, AddonLoader.class.getClassLoader())
		).addonBindModules();
	}

}
