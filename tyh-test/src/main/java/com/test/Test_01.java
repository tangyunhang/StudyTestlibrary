package com.test;

import com.google.common.base.Function;
import com.test.utils.Builder;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * @Description: Test_01
 * @Author: 青衣醉
 * @Date: 2022/8/17 2:01 下午
 */
public class Test_01 {
    public static void main(String[] args) {

    }
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


    @Test
    public  void dd() {
        BigDecimal o1  = BigDecimal.valueOf (20000D);
        BigDecimal o2  = BigDecimal.valueOf (10000D);
        BigDecimal o3 = o1.add (o2);
        System.out.println (o3);
        BigDecimal o4 = BigDecimal.valueOf (-71342.47);
        System.out.println (o4);
        BigDecimal divide = o2.divide (o3,4,BigDecimal.ROUND_HALF_EVEN);
        BigDecimal divide2 = o1.divide (o3,4,BigDecimal.ROUND_HALF_EVEN);
        System.out.println (divide);
        System.out.println (o4.multiply (divide));
        System.out.println (o4.multiply (divide).setScale (4,BigDecimal.ROUND_HALF_EVEN));

        System.out.println (o4.multiply (divide2).setScale (4,BigDecimal.ROUND_HALF_EVEN));
        ;
    }
}

