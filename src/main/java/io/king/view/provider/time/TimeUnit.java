package io.king.view.provider.time;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@Getter
@RequiredArgsConstructor
public enum TimeUnit {

    YEAR(aLong -> aLong / 31536000),
    MONTH(aLong -> (aLong % 31536000) / 2629746),
    DAY(aLong -> ((aLong % 31536000) % 2629746) / 86400),
    HOUR(aLong -> (((aLong % 31536000) % 2629746) % 86400) / 3600),
    MINUTE(aLong -> ((((aLong % 31536000) % 2629746) % 86400) % 3600) / 60),
    SECOND(aLong -> ((((aLong % 31536000) % 2629746) % 86400) % 3600) % 60);

    private final Function<Long, Long> function;

    public long convertTime(long duration) {
        return function.apply(duration);
    }
}
