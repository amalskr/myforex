package com.ceylonapz.myforex.util.interfaces;

import com.google.gson.JsonObject;

public interface StatusInterface {
    void success(JsonObject jsonObject);

    void fail(String message);
}
