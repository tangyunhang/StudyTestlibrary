package com.abc.listener.listener;


import com.abc.listener.event.Event;

import java.util.EventListener;


public interface TyhEventListener<E extends Event> extends EventListener {
    public void onEvent(E event);
    public Class<? extends Event> getEventType();
}
