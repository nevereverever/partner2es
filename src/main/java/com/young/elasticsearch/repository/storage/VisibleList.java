package com.young.elasticsearch.repository.storage;

import com.google.gson.Gson;

import java.util.ArrayList;

/*************************
 * @author: YoungLu
 * @date: 2019/12/19 15:43
 * @description: 一个重写了ArrayList的toString集合类
 **************************/
public class VisibleList<T> extends ArrayList<T> {
    public VisibleList(int initialCapacity) {
        super(initialCapacity);
    }

    public VisibleList() {
        super();
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
