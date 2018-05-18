package com.ygy.servicefeign;

import org.springframework.stereotype.Component;

@Component
public class SchedualServiceHiImpl implements SchedualServiceHi {
    @Override
    public String sayHiFrom(String name) {
        return "sorry  "+name;
    }
}