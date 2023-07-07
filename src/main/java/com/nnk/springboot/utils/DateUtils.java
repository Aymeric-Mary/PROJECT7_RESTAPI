package com.nnk.springboot.utils;

import lombok.experimental.UtilityClass;

import java.time.Instant;

@UtilityClass
public class DateUtils {

    public static Instant now() {
        return Instant.now();
    }
}
