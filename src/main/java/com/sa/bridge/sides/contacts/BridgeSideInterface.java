package com.sa.bridge.sides.contacts;

/**
 * Created by sergeiakimov
 */
public interface BridgeSideInterface {

    /**
     * Adds amount of cars to the queue
     *
     * @param carsWaiting amount
     */
    void addCarsWaiting(int carsWaiting);

    /**
     * Sets green light
     */
    void setGreenLight();

    /**
     * Sets red light
     */
    void setRedLight();

    /**
     * Gets information if stub has green light
     *
     * @return boolean-value representing is green light on
     */
    Boolean getHasGreenLight();

    /**
     * Starts thread
     */
    void start();


}
