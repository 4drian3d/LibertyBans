/* 
 * ArimBansExtended, an extension for the ArimBans core
 * Copyright © 2019 Anand Beh <https://www.arim.space>
 * 
 * ArimBansExtended is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ArimBansExtended is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ArimBansExtended. If not, see <https://www.gnu.org/licenses/>
 * and navigate to version 3 of the GNU General Public License.
 */
package space.arim.bans.extended;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.yaml.snakeyaml.Yaml;

import space.arim.bans.api.ArimBansLibrary;
import space.arim.bans.api.Subject;
import space.arim.bans.api.util.ConfigUtil;
import space.arim.bans.api.util.ToolsUtil;

public class ArimBansExtended implements AutoCloseable {
	
	private final static String[] COMMANDS = {"ban", "unban", "ipban", "ipunban", "mute", "unmute", "ipmute", "ipunmute", "warn", "unwarn", "ipwarn", "ipunwarn", "kick", "ipkick", "banlist", "ipbanlist", "playerbanlist", "mutelist", "ipmutelist", "playermutelist", "history", "iphistory", "warns", "ipwarns", "status", "ipstatus", "ips", "geoip", "alts", "blame", "rollback"};
	
	private final ArimBansLibrary lib;
	private final File folder;
	private final Logger logger;
	
	private final ConcurrentHashMap<String, Object> cfg = new ConcurrentHashMap<String, Object>();
	
	ArimBansExtended(ArimBansLibrary lib, File folder, Logger logger) {
		Objects.requireNonNull(lib, "ArimBansLibrary must not be null!");
		Objects.requireNonNull(folder, "Folder must not be null!");
		Objects.requireNonNull(logger, "Logger must not be null!");
		this.lib = lib;
		this.folder = folder;
		this.logger = logger;
		loadConfig(folder, cfg);
	}
	
	private Logger logger() {
		return logger;
	}
	
	@SuppressWarnings("unchecked")
	private void loadConfig(File folder, Map<String, Object> cfgMap) {
		if (!folder.exists() && !folder.mkdirs()) {
			throw new IllegalStateException("Directory creation failed!");
		}
		File cfgFile = new File(folder, "config.yml");
		if (!cfgFile.exists()) {
			try {
				if (ConfigUtil.saveFromStream(cfgFile, ArimBansExtended.class.getResourceAsStream(File.separator + "config.yml"))) {
					logger().log(Level.WARNING, "Config saved successfully!");
				} else {
					throw new IllegalStateException("Config copying failed!");
				}
			} catch (IOException ex) {
				throw new IllegalStateException("Config copying failed!", ex);
			}
		}
		try (FileReader reader = new FileReader(cfgFile)) {
			cfgMap.putAll((Map<String, Object>) (new Yaml()).load(reader));
		} catch (IOException ex) {
			throw new IllegalStateException("Config could not be loaded!", ex);
		}
		
	}
	
	public ArimBansLibrary getLib() {
		return lib;
	}
	
	File getFolder() {
		return folder;
	}
	
	private <T> T getCfgObject(Class<T> type, String key, T defaultObj) {
		T obj = ConfigUtil.getFromConfigMap(cfg, key, type);
		return (obj != null) ? obj : defaultObj;
	}
	
	public boolean antiSignEnabled() {
		return getCfgObject(Boolean.class, "options.anti-sign", true);
	}
	
	public void fireCommand(Subject subject, String command, String[] args) {
		lib.simulateCommand(subject, (command + " " + ToolsUtil.concat(args, ' ')).split(" "));
	}
	
	static String[] commands() {
		return COMMANDS;
	}
	
	@Override
	public void close() {
		
	}
}
