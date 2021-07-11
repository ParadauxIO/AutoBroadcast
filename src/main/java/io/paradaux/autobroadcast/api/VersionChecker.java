/*
 * This class contains code obtained from https://www.spigotmc.org/wiki/creating-an-update-checker-that-checks-for-updates/
 * This is licensed under GPL by virtue of being a Bukkit-derived work.
 *
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.autobroadcast.api;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Consumer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class VersionChecker {

    private final Plugin plugin;
    private final int resourceId;

    public VersionChecker(Plugin plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public VersionChecker(int resourceId) {
        this.plugin = null;
        this.resourceId = resourceId;
    }

    public void getVersion(final Consumer<String> consumer) {
        if (plugin != null) {
            Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
                try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream();
                     Scanner scanner = new Scanner(inputStream)) {
                    if (scanner.hasNext()) {
                        consumer.accept(scanner.next());
                    }
                } catch (IOException exception) {
                    LocaleLogger.error("system.autobroadcast.update.error", exception.getMessage());
                }
            });
        } else {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream();
                 Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException exception) {
                LocaleLogger.error("system.autobroadcast.update.error", exception.getMessage());
            }
        }
    }

}
