package com.societegenerale.bank.core_api.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
public class BaseEvent <T> {
    @Getter
    private T id;

    @Getter
    private Date date;

    @Override
    public String toString() {
        return "BaseEvent{" +
                "id=" + id +
                ", date=" + date +
                '}';


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEvent<?> baseEvent = (BaseEvent<?>) o;
        return Objects.equals(id, baseEvent.id) && Objects.equals(date.toString(), baseEvent.date.toString());
    }

}
