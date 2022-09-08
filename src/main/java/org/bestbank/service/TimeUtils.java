package org.bestbank.service;

import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class TimeUtils {

    public OffsetDateTime getTime(){
        OffsetDateTime time = OffsetDateTime.now();
        return time;
    }
}
