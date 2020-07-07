package io.king.view.provider.command;

import io.king.core.api.di.Inject;
import io.king.view.provider.view.ModuleGui;
import me.saiintbrisson.minecraft.command.Execution;
import me.saiintbrisson.minecraft.command.annotations.Command;
import org.bukkit.entity.Player;

public final class ModuleView {

    @Inject
    private ModuleGui moduleGui;

    @Command(name = "module")
    public void mainModule(Execution execution) {
        final Player player = execution.getPlayer();
        moduleGui.showInventory(player);
    }
}
