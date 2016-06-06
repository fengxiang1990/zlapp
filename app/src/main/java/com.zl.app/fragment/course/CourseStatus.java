package com.zl.app.fragment.course;

import java.io.Serializable;

/**
 * Created by fxa on 2016/6/6.
 */

public class CourseStatus implements Serializable {

    public CourseStatus(int type,String name){
        this.type = type;
        this.name = name;
    }

    int type;
    String name;

    @Override
    public String toString() {
        return name;
    }
}

