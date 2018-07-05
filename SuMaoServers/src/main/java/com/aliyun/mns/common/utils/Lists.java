package com.aliyun.mns.common.utils;

import java.util.LinkedList;
import java.util.List;

public class Lists {

    public static <T> List<T> newLinkedList(Iterable<T> it) {
        if (it == null)
            return null;

        List<T> list = new LinkedList<T>();
        for (T i : it) {
            list.add(i);
        }
        return list;
    }
}
