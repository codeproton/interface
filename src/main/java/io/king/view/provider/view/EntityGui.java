package io.king.view.provider.view;

import io.king.core.api.di.Inject;
import io.king.core.api.di.Injectable;
import io.king.core.api.module.Module;
import io.king.core.api.module.ModuleConfig;
import io.king.core.api.service.Service;
import io.king.core.provider.CorePlugin;
import io.king.core.provider.module.ModuleObject;
import io.king.core.provider.module.ModuleProps;
import io.king.view.provider.time.TimeService;
import lombok.Setter;
import me.saiintbrisson.minecraft.ItemBuilder;
import me.saiintbrisson.minecraft.paginator.PaginatedView;
import me.saiintbrisson.minecraft.view.View;
import me.saiintbrisson.minecraft.view.ViewItem;
import me.saiintbrisson.minecraft.view.ViewNode;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@Service
@Injectable
public final class EntityGui extends View<ViewItemGui> {

    @Inject
    private TimeService timeService;

    public EntityGui(@Inject CorePlugin corePlugin) {
        super(corePlugin, "Module Information", 5);
    }

    @Setter
    private PaginatedView<?> paginatedView;

    @Override
    protected void render(ViewNode<ViewItemGui> node, Player player, ViewItemGui object) {
        final String[] information = getInformation(object);

        node.appendItem(new ViewItem<ViewItemGui>()
                .withItem(new ItemBuilder(Material.PAPER)
                        .name("§cInformation about module")
                        .lore(information)
                        .build()
                ).withSlot(1, 1)
                .updateOnClick()
        );

        node.appendItem(new ViewItem<ViewItemGui>()
                .withItem(new ItemBuilder(Material.BEACON)
                        .name("Back")
                        .build()
                ).withSlot(3, 7)
                .openPaginatedView(paginatedView)
        );
    }

    private String[] getInformation(ViewItemGui object) {
        final ModuleObject moduleObject = object.getModuleObject();
        final ModuleConfig moduleConfig = moduleObject.getModuleConfig();
        final ModuleProps moduleProps = moduleObject.getModuleProps();

        final String hashId = moduleConfig.getHashId();
        final String version = hashId == null ?
                "No provided version" :
                hashId;

        final String[] strings = performSoftDepends(moduleObject.getModule());
        final String softDepends = strings.length == 0 ?
                "Has no soft depends" :
                String.join(", ", strings);

        final long systemLoadTime = System.currentTimeMillis() - moduleProps.getLoadedAtTime();
        final String formatTime = timeService.formatTime(systemLoadTime / 1000);

        return new String[]{
                "",
                " §eName » §7" + moduleConfig.getName(),
                " §eAuthor » §7" + moduleConfig.getAuthor(),
                " §eVersion » §7" + version,
                " §eDescription » §7" + moduleConfig.getDescription(),
                " §eStage » §7" + moduleObject.getModuleStage(),
                "",
                " §bSoft Depends » §7" + softDepends,
                " §bLoad time » §7" + formatTime +
                        " (" + moduleObject.getLoadDuration() + "ms)"
        };
    }

    private String[] performSoftDepends(Module module) {
        final Class<?>[] classes = module.softDepend();
        final String[] strings = new String[classes.length];

        for (int i = 0; i < classes.length; i++) {
            strings[i] = classes[i].getSimpleName();
        }

        return strings;
    }
}
