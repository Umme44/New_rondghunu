package com.bits.hr.service.event;

import com.bits.hr.service.dto.ManualAttendanceEntryDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AttendanceRegularizationEvent extends ApplicationEvent {

    ManualAttendanceEntryDTO manualAttendanceEntryDTO;
    EventType type;

    public AttendanceRegularizationEvent(Object source, ManualAttendanceEntryDTO manualAttendanceEntryDTO, EventType type) {
        super(source);
        this.manualAttendanceEntryDTO = manualAttendanceEntryDTO;
        this.type = type;
    }
}