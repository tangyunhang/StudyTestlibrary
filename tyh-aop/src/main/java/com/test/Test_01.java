package com.test;

import com.google.common.base.Function;
import com.test.utils.Builder;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @Description: Test_01
 * @Author: 青衣醉
 * @Date: 2022/8/17 2:01 下午
 */
public class Test_01 {
    @Test
    public void  test (){
        Function<Test_01, Student> getStudent = Test_01::getStudent;
        Student student = getStudent ();
        System.out.println (getStudent());
        Student hang = Builder.of (Student::new)
                .with (Student::setName, "hang")
                .build ();
        System.out.println (hang);

    }

    public Student getStudent(){
        return Student.builder ()
                .name ("唐")
                .className ("001")
                .build ();
    }

    @Test
    public void test_(){
        Object o = Optional.ofNullable (null).orElseGet (() -> null);
        System.out.println (
                o
        );
        // Optional<String> o = Optional.ofNullable (null)
        //         .map (m -> m = null)
        //         .filter (c -> c == "1");
        //
        // System.out.println (o.isPresent ());

    }

    @Test
    public  void ddd() {
        String[] ff =new String[]{"qw.ee","rf.dd"};
        List<String> strings = Arrays.asList (ff);
        Arrays.stream (ff).forEach (System.out::println);
    }
}

