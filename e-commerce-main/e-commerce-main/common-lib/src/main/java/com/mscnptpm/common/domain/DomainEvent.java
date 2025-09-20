package com.mscnptpm.common.domain;

import java.time.Instant;

public interface DomainEvent {
    String eventName();
    String aggregateId();
    Instant occurredAt();
    String version();
}
