package com.tyh.optional;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import com.abc.bean.Trade;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @Description: OptionalTest01
 * @Author: 青衣醉
 * @Date: 2022/7/11 5:02 下午
 */
public class OptionalTest01 {


    @Test
    public void createTest1(){
        Trade trade = Trade.builder ()
                .iCode ("qyz0711")
                .iName ("青衣醉0711")
                .iType ("0711")
                .build ();
        // 构建一个value不可以为null的optional对象，如果of()的入参为null会报空指针异常；
        Optional<Trade> of1 = Optional.of (trade);
        //Optional<Trade> of2 = Optional.of (null);
        System.out.println ("of1:"+of1);
       // System.out.println ("of2:"+of2);
        //返回一个描述给定值的Optional ，如果为null ，则返回一个空的Optional,否则返回给定值的Optional 。
        Optional<Trade> ofNullable1 = Optional.ofNullable (trade);
        Optional<Trade> ofNullable2 = Optional.ofNullable (null);
        System.out.println ("ofNullable1:"+ofNullable1);
        System.out.println ("ofNullable2:"+ofNullable2);

        Trade orElseGet = ofNullable2.orElseGet (null);
        System.out.println ("orElseGet:"+orElseGet);
        ofNullable2.ifPresent (null);
    }

    @Test
    public void getTest02(){
        Trade trade = Trade.builder ()
                .iCode ("qyz0711")
                .iName ("青衣醉0711")
                .iType ("0711")
                .build ();
        Trade defaultTrade = Trade.builder ()
                .iCode ("MR1016")
                .iName ("默认值")
                .iType ("1016")
                .build ();
        Optional<Trade> tradeInfoEmptyOpt = Optional.empty();
        Optional<Trade> tradeInfoOpt = Optional.of(trade);
        // 1、get()-> 直接获取，注意如果value==null，会报NoSuchElementException异常
        Trade trade1 = tradeInfoOpt.get();
        System.out.println ("1、get():"+trade1);

        //2、orElse(T other)-> orElse可以传入一个Trade类型的参数当默认值，若value==null,则返回会默认值，否则返回value
        Trade trade2 = tradeInfoEmptyOpt.orElse (defaultTrade);
        Trade trade3 = tradeInfoOpt.orElse (defaultTrade);
        System.out.println ("2、orElse(),value == null:"+trade2);
        System.out.println ("2、orElse(),value != null:"+trade3);

        //3、orElseGet(Supplier<? extends T> other)-> 如果存在值，则返回该值，否则返回由供应函数supplier产生的结果。
        // 如果不存在值，且supplier为null,则抛出NullPointerException,和orElse不同的是orElseGet可以传入一段lambda表达式；
        Trade trade4 = tradeInfoEmptyOpt.orElseGet (() -> defaultTrade);
        Trade trade5 = tradeInfoOpt.orElseGet (() -> defaultTrade);
       // Trade trade6 = tradeInfoEmptyOpt.orElseGet (null);//不存在值，且supplier为null,则抛出NullPointerException
        System.out.println ("3、orElseGet(other),value == null:"+trade4);
        System.out.println ("3、orElseGet(other),value != null:"+trade5);

        //4、orElseThrow(Supplier<? extends X> exceptionSupplier)-> 可以传入一段lambda表达式，
        // lambda返回一个Exception；当value!=null时，返回value值；当value==null时，抛出该异常；。
        Trade trade7 = tradeInfoOpt.orElseThrow (NullPointerException::new);
        System.out.println ("4、orElseThrow(exceptionSupplier),value == null:"+trade7);


        //5、or(Supplier<? extends Optional<? extends T>> supplier)->如果值存在时，返回一个Optional描述的值，否则将返回一个Optional产生通过供给功能。
        Optional<Trade> optTrade = tradeInfoEmptyOpt.or(()->{
            return Optional.of (Trade.builder ()
                    .iCode ("OR0714")
                    .iName ("OR0714")
                    .iType ("0714")
                    .build ());
        });
        if (optTrade.isPresent ()) {
            System.out.println ("5or(supplier)"+optTrade.get ());
        }

    }
    @Test
    public void ifTest03(){
        Trade trade = Trade.builder ()
                .iCode ("qyz0711")
                .iName ("青衣醉0711")
                .iType ("0711")
                .build ();
        Trade defaultTrade = Trade.builder ()
                .iCode ("MR1016")
                .iName ("默认值")
                .iType ("1016")
                .build ();
        Optional<Trade> tradeInfoEmptyOpt = Optional.empty();
        Optional<Trade> tradeInfoOpt = Optional.of(trade);
        // 1、isPresent()-> 判断是否存在对象，我们在调用get之前，一定要先调用isPresent，因为直接如果value是null，直接调用get会报异常；
        if (tradeInfoEmptyOpt.isPresent ()) {
            Trade trade1 = tradeInfoOpt.get ();
            System.out.println ("isPresent():"+trade1);
        }else {
            System.out.println ("isPresent(): null");
        }
        //2、ifPresent(Consumer<? super T> consumer)->如果存在值，则使用该值执行给定的操作，否则不执行任何操作。
        tradeInfoOpt.ifPresent (trade2 -> {
            System.out.println("ifPresent(Consumer<? super T> consumer)："+trade2);
        });

        //3、ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction) -> 如果存在值，则使用该值执行给定的操作，否则就执行为空是的操作。
        tradeInfoOpt.ifPresentOrElse (trade3 -> {
            System.out.println("ifPresentOrElse():"+"trade3 is not null,"+trade3);
        },()->{
            System.out.println("ifPresentOrElse():"+"value is null");
        });
    }

    @Test
    public void filterTests(){
        //Optional<T> filter(Predicate<? super T> predicate)->如果存在一个值，并且该值与给定的（predicate方法）匹配，
        // 则返回描述该值的Optional ，否则返回一个空的Optional 。
        Optional<Object> o = Optional.ofNullable ("3333");
        System.out.println ("filter(predicate):"+o);
        Optional<Object> o2 = o.filter (o1 -> false);
        Optional<Object> o3 = o.filter (o1 -> true);
        System.out.println ("filter(predicate),predicate=false:"+o2);
        System.out.println ("fiilter(predicate),predicate=true:"+o3);
    }


    @Test
    public void mapTest(){
        /**
         相同点：两个方法的返回值都是 Optional 对象！
         不同点：区别在于调用两个方法需要的入参（即拥有唯一方法的匿名内部类：也叫函数式接口）的返回值不同，
         map 方法会自动将入参中的函数式接口返回值包装成 Optional 对象，而 flatMap 方法则要求入参中的函数式接口返回值本身就是 Optional 对象。
         入参的不同也就导致了使用场景的不同，如果函数式接口返回值本身就是 Optional 对象，那么就可以直接使用 flatMap 方法；
         否则就应该使用 map 方法，省去了使用 Optional 包装的麻烦。比如示例中 getName 返回值是 String 类型，那么此时使用 map 方法更合理。

         主要看函数式接口返回值类型选择使用那个方法
         **/
        Trade trade = new Trade ("qyz0711", "青衣醉0711", "0711");
        //1、map(Function<? super T,? extends U> mapper)->如果存在value，则通过mapper方法处理，
        // 映射称该值结果并返回，结果类型还是为Optional包装的对象；若不存在这返回空的Optional对象
        Optional<Trade> optionalTrade = Optional.of (trade);
        Optional<String> s = optionalTrade.map (Trade::toString);
        System.out.println (s);
        System.out.println (s.get ());

        //2、flatMap(Function<? super T, ? extends Optional<? extends U>> mapper)
        Optional<Trade> optionalTrade2 = Optional.of (trade);
        Optional<String> s1 = optionalTrade2.flatMap (trade1 -> {
            return Optional.ofNullable (trade1.getICode ());
        });
        ///

        long count = optionalTrade2.stream ().map (trade1 -> {
            return trade.getICode ();
        }).count ();
        System.out.println (count);
    }

    @Test
    public void test4(){
        List<String> list = Lists.newArrayList ();
        list.add("Tom");
        list.add("Jerry");
        list.add("Tim");

        Optional<List<String>> optional = Optional.ofNullable(list);
        System.out.println (optional.getClass ());
        Stream<List<String>> stream = optional.stream();
        System.out.println (stream);
//        long count = stream.count();
//        System.out.println(count);
        stream.flatMap(x -> {
            System.out.println (x.getClass ());
            return x.stream();
        }).forEach(System.out::println);

    }
}
