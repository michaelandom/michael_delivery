package com.michael_delivery.backend.model;

import java.io.Serializable;

public abstract class BaseModel<ID extends Serializable> {

    /**
     * Get id
     * @return id
     */
    public abstract ID getId();

}
