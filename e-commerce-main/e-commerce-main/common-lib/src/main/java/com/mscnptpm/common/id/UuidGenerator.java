package com.mscnptpm.common.id;

import java.util.UUID;

public class UuidGenerator implements IdGenerator {
    @Override
    public String generate(String prefix) {
        var base = UUID.randomUUID().toString().replace("-", "");
        return (prefix == null || prefix.isBlank()) ? base : prefix + base;
    }
}
