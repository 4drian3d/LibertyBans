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

package space.arim.libertybans.env.sponge;

import jakarta.inject.Inject;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.exception.CommandException;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import space.arim.api.env.AudienceRepresenter;
import space.arim.libertybans.core.config.InternalFormatter;
import space.arim.libertybans.core.env.AbstractEnvEnforcer;
import space.arim.libertybans.core.env.Interlocutor;
import space.arim.omnibus.util.concurrent.CentralisedFuture;
import space.arim.omnibus.util.concurrent.FactoryOfTheFuture;

import java.net.InetAddress;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public final class SpongeEnforcer extends AbstractEnvEnforcer<ServerPlayer> {

	private final Game game;

	@Inject
	public SpongeEnforcer(FactoryOfTheFuture futuresFactory, InternalFormatter formatter,
						  Interlocutor interlocutor, Game game) {
		super(futuresFactory, formatter, interlocutor, AudienceRepresenter.identity());
		this.game = game;
	}

	@SuppressWarnings("unchecked")
	private CentralisedFuture<Void> runSync(Runnable command) {
		// Technically an inaccurate cast, but it will never matter
		return (CentralisedFuture<Void>) futuresFactory().runSync(command);
	}

	@Override
	protected CentralisedFuture<Void> doForAllPlayers(Consumer<ServerPlayer> callback) {
		return runSync(() -> game.server().onlinePlayers().forEach(callback));
	}

	@Override
	public CentralisedFuture<Void> doForPlayerIfOnline(UUID uuid, Consumer<ServerPlayer> callback) {
		return runSync(() -> game.server().player(uuid).ifPresent(callback));
	}

	@Override
	public void kickPlayer(ServerPlayer player, Component message) {
		player.kick(message);
	}

	@Override
	public UUID getUniqueIdFor(ServerPlayer player) {
		return player.uniqueId();
	}

	@Override
	public InetAddress getAddressFor(ServerPlayer player) {
		return player.connection().address().getAddress();
	}

	@Override
	public boolean hasPermission(ServerPlayer player, String permission) {
		return player.hasPermission(permission);
	}

	@Override
	public CompletableFuture<Void> executeConsoleCommand(String command) {
		return runSync(() -> {
			try {
				game.server().commandManager().process(command);
			} catch (CommandException ex) {
				Logger logger = LoggerFactory.getLogger(getClass());
				logger.warn("Exception occurred while executing console command {}", command, ex);
			}
		});
	}

}
