package com.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description: Student
 * @Author: 青衣醉
 * @Date: 2022/8/17 2:00 下午
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    String name;
    String className;
    int age;
    List<String> memo;
}
