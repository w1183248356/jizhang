package com.viewhigh.libs.utils;

/**
 * Created by huntero on 17-7-17.
 */

public final class Objects {
    private Objects(){};
    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }
}
