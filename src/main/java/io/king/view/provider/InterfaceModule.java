package io.king.view.provider;

import io.king.core.api.cycle.LifeContext;
import io.king.core.api.cycle.LifeCycle;
import io.king.core.api.module.ModuleConfig;
import io.king.core.provider.cycle.event.ModuleInitialized;
import io.king.core.api.module.Module;
import io.king.core.api.module.ModulePriority;
import io.king.view.provider.command.ModuleView;
import io.king.view.provider.time.TimeService;
import io.king.view.provider.view.EntityGui;
import io.king.view.provider.view.ViewService;

import java.util.logging.Logger;

@Module(
        services = {
                TimeService.class, EntityGui.class, ViewService.class
        },
        priority = ModulePriority.SYSTEM,
        commands = {ModuleView.class},
        config = InterfaceConfig.class
)
public final class InterfaceModule extends LifeCycle {

    @Override
    public void init(LifeContext context) {
        final Logger logger = context.getLogger();

        context.registerEvent(ModuleInitialized.class, instance -> {
            final ModuleConfig moduleConfig = instance.getModuleObject().getModuleConfig();

            final String logMessage = String.format(
                    "Module %s has been loaded.",
                    moduleConfig.getName()
            );
            logger.info(logMessage);
        });
    }

    @Override
    public void dispose(LifeContext context) {
        context.getLogger().info("Interface has been unloaded");
    }
}
