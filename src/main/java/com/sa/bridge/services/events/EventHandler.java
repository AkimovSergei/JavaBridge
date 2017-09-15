package com.sa.bridge.services.events;

import com.sa.bridge.sides.Side;

/**
 * Created by sergeiakimov
 */
public interface EventHandler<T> {

    void onUpdateCount(Side side, T msg);

}
