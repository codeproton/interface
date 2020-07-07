package io.king.view.provider.view;

import io.king.core.provider.CorePlugin;
import me.saiintbrisson.minecraft.paginator.PaginatedView;
import me.saiintbrisson.minecraft.view.View;

import java.util.List;

public final class ModuleGui extends PaginatedView<ViewItemGui> {

    private final static String[] LAYOUT_TEMPLATE = new String[]{
            "OOOOOOOOO",
            "OXXXXXXXO",
            "OXXXXXXXO",
            "OXXXXXXXO",
            "OOOOOOOOO",
            "OOO<O>OOO"
    };

    public ModuleGui(CorePlugin owner, View<ViewItemGui> gui, List<ViewItemGui> moduleItems) {
        super(owner, "List of Modules", LAYOUT_TEMPLATE, () -> moduleItems);

        setItemProcessor((player, viewItem, event) -> {
            gui.createNode(viewItem).show(player);
        });
    }
}
