/*
 * LibertyBans
 * Copyright © 2021 Anand Beh
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
package space.arim.libertybans.core.commands.usage;

import java.util.Locale;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import space.arim.api.jsonchat.adventure.ChatMessageComponentSerializer;

enum UsageSection {

	PUNISH("&e/ban, /ipban &7- ban players",
			"&e/mute, /ipmute &7- mute players",
			"&e/warn, /ipwarn &7- warn players",
			"&e/kick, /ipkick &7- kick players"),
	UNPUNISH("&e/unban, /unbanip &7- undo a ban",
			"&e/unmute, /unmuteip &7- undo a mute",
			"&e/unwarn, /unwarnip &7- undo a warn"),
	LIST("&e/banlist &7- view active bans",
			"&e/mutelist &7- view active mutes",
			"&e/history &7- view a player's history",
			"&e/warns &7- view a player's warns",
			"&e/blame &7- view another staff member's punishments"),
	OTHER("&e/libertybans &7usage - shows this",
			"&e/libertybans &7reload - reload config.yml and language configuration",
			"&e/libertybans &7restart - perform a full restart, reloads everything including database connections",
			"&e/libertybans &7debug - outputs debug information");
	
	private final Component content;
	
	UsageSection(String...commands) {
		String name = name();
		String headerString = "&b" + name.charAt(0) + name.substring(1).toLowerCase(Locale.ROOT) + " commands:";

		ChatMessageComponentSerializer serialiser = new ChatMessageComponentSerializer();
		Component header = serialiser.deserialize(headerString);
		Component body = serialiser.deserialize("\n" + String.join("\n", commands));
		content = TextComponent.ofChildren(header, body);
	}
	
	Component content() {
		return content;
	}
	
}
