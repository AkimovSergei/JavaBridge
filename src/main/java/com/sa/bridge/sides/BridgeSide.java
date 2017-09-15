package com.sa.bridge.sides;

import com.sa.bridge.services.events.EventHandler;
import com.sa.bridge.sides.contacts.BridgeSideInterface;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * Created by sergeiakimov
 */
public class BridgeSide extends Thread implements BridgeSideInterface {

    private Logger LOG = Logger.getLogger(BridgeSide.class.getName());

    private EventHandler<Integer> eventHandler;
    private final Side sideName;
    private AtomicInteger carsWaiting;
    private Boolean hasGreenLight;
    private final long SLEEP_TIME = 500L;

    public BridgeSide(Side bridgeSide, int carsWaiting, EventHandler<Integer> handler) {
        this.sideName = bridgeSide;
        this.carsWaiting = new AtomicInteger();
        this.carsWaiting.addAndGet(carsWaiting);
        this.eventHandler = handler;
    }

    @Override
    public void addCarsWaiting(int additionalCars) {
        final int carsAmount = this.carsWaiting.addAndGet(additionalCars);
        Optional.ofNullable(eventHandler)
                .ifPresent(handler -> handler.onUpdateCount(sideName, carsAmount));
    }

    @Override
    public void setGreenLight() {
        LOG.info("GREEN light was set for " + sideName.name());
        hasGreenLight = true;
    }

    @Override
    public void setRedLight() {
        LOG.info("RED light was for " + sideName.name());
        hasGreenLight = false;
    }

    @Override
    public Boolean getHasGreenLight() {
        return hasGreenLight;
    }

    @Override
    public void run() {
        super.run();

        LOG.fine(sideName.name() + " setup with " + carsWaiting);
        LOG.fine(sideName.name() + " green light by init: " + getHasGreenLight());

        while (true) {
            if (hasGreenLight != null) {
                if (carsWaiting.get() > 0 && hasGreenLight) {
                    carsWaiting.decrementAndGet();
                    Optional.ofNullable(eventHandler)
                            .ifPresent(handler -> handler.onUpdateCount(sideName, carsWaiting.get()));
                }
            }
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException ex) {
                LOG.warning("Thread has been interrupted");
            }
        }
    }

}
