package com.ai.baas.mt.web.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by astraea on 2015/7/31.
 */
public class PagerResult<T> implements Serializable {
    private Pager pager;
    private List<T> result;

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
