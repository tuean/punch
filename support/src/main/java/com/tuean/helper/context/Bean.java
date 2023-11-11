package com.tuean.helper.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bean {

    private String name;

    private Class clazz;

    private Object instance;



}
