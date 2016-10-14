package ateam.rehashprot2.service;

import ateam.rehashprot2.data.Channel;

/**
 * Created by User on 7/10/2016.
 */

public interface WeatherServiceCallback {
    void serviceSuccess(Channel channel);

    void serviceFailure(Exception exception);
}

