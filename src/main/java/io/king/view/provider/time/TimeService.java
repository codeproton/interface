package io.king.view.provider.time;

import io.king.core.api.service.Service;

@Service
public final class TimeService {

    public String formatTime(long time) {
        final StringBuilder builder = new StringBuilder();

        for (TimeUnit value : TimeUnit.values()) {
            final long timeValue = value.convertTime(time);
            if (timeValue < 1) continue;

            String name = value.name().toLowerCase();
            if (timeValue > 1) name += "s";

            return builder.append(timeValue)
                    .append(" ")
                    .append(name)
                    .append(" ago").toString();
        }

        return null;
    }
}
