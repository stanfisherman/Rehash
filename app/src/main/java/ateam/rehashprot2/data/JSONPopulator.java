package ateam.rehashprot2.data;

/**
 * Created by User on 7/10/2016.
 */

import org.json.JSONObject;

public interface JSONPopulator {
    void populate(JSONObject data);

    JSONObject toJSON();
}