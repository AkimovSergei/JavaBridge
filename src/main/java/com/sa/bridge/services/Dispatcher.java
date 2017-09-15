package com.sa.bridge.services;

import com.sa.bridge.services.events.EventHandler;
import com.sa.bridge.sides.BridgeSide;
import com.sa.bridge.sides.Side;
import com.sa.bridge.sides.contacts.BridgeSideInterface;

import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Created by sergeiakimov
 */
public class Dispatcher {

    private Logger LOG = Logger.getLogger(Dispatcher.class.getName());

    private BridgeSideInterface bridgeSideWest;
    private BridgeSideInterface bridgeSideEast;

    private final Map<Side, Integer> initialCarNumbers;
    private final EventHandler<Integer> anyCountEventHandler;

    public Dispatcher(Map<Side, Integer> initialCarNumbers, EventHandler<Integer> anyCountEventHandler) {
        this.initialCarNumbers = initialCarNumbers;
        this.anyCountEventHandler = anyCountEventHandler;
        init();
    }

    /**
     * Sets active (green) side
     *
     * @param futureSite side to be green
     */
    public void setActiveSite(Side futureSite) {
        if (futureSite.equals(Side.LEFT)) {
            this.bridgeSideWest.setGreenLight();
            this.bridgeSideEast.setRedLight();
        } else if (futureSite.equals(Side.RIGHT)) {
            this.bridgeSideEast.setGreenLight();
            this.bridgeSideWest.setRedLight();
        } else {
            this.bridgeSideEast.setRedLight();
            this.bridgeSideWest.setRedLight();
        }
    }

    /**
     * Initializes dispatcher: start up 2 threads, switches to NONE active side
     */
    private void init() {
        this.bridgeSideWest = new BridgeSide(Side.LEFT, initialCarNumbers.get(Side.LEFT), getHandler());
        this.bridgeSideEast = new BridgeSide(Side.RIGHT, initialCarNumbers.get(Side.RIGHT), getHandler());

        setActiveSite(Side.NONE);

        this.bridgeSideEast.start();
        this.bridgeSideWest.start();
    }

    /**
     * Returns active side of bridge
     *
     * @return current active side
     */
    public Side getActiveSite() {
        Side activeSide = null;
        if (this.bridgeSideWest.getHasGreenLight()) {
            activeSide = Side.LEFT;
        } else if (this.bridgeSideEast.getHasGreenLight()) {
            activeSide = Side.RIGHT;
        }

        return activeSide;
    }

    /**
     * Adds any number of cars to chosen side
     *
     * @param side chosen side
     * @param cars amount of cars to add
     */
    public void addCarsToSide(Side side, int cars) {
        if (side.equals(Side.RIGHT)) {
            this.bridgeSideEast.addCarsWaiting(cars);
        } else if (side.equals(Side.LEFT)) {
            this.bridgeSideWest.addCarsWaiting(cars);
        }
    }

    private EventHandler<Integer> getHandler() {
        return (side, msg) -> {
            LOG.info(side.name() + " now counts " + msg + " cars");
            Optional.ofNullable(anyCountEventHandler)
                    .ifPresent(integerEventHandler -> integerEventHandler.onUpdateCount(side, msg));
        };
    }

}
