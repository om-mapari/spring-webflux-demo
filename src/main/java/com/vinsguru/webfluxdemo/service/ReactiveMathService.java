package com.vinsguru.webfluxdemo.service;


import com.vinsguru.webfluxdemo.dto.Response;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;


@Service
public class ReactiveMathService {

    public Mono<Response> findSquare(int input){
        Mono<Integer> integerMono = Mono.fromSupplier(() -> input * input);
        Mono<Response> map = integerMono.map(v -> new Response(v));
        return map;
    }




    public Flux<Response> multiplicationTable(int input){
//        Flux<Integer> range = Flux.range(1, 10);// |1,2,3|
//        Flux<Integer> rg  = range.doOnNext(i -> ReactiveMathService.sleepSecond(1));
//        Flux<Integer> rg2 = rg.doOnNext(i -> System.out.println("reative-math-service processing : " + i));
//        Flux<Response> map = rg2.map(i -> new Response(i * i));
//        return map;
            return Flux.range(1,10)
                    .delayElements(Duration.ofSeconds(1))
                    .doOnNext(i -> System.out.println("reative-math-service processing : " + i))
                    .map(i -> new Response(i * i));
    }


    public static void sleepSecond(int seconds){
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

//
//
//package com.vinsguru.webfluxdemo.service;
//
//import com.vinsguru.webfluxdemo.dto.Response;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//import java.time.Duration;
//
//@Service
//public class ReactiveMathService {
//
//    public Mono<Response> findSquare(int input){
//        return Mono.fromSupplier(() -> input * input)
//                .map(Response::new);
//    }
//
//    public Flux<Response> multiplicationTable(int input){
//        return Flux.range(1, 10)
//                .delayElements(Duration.ofSeconds(1))
//                //.doOnNext(i -> SleepUtil.sleepSeconds(1))
//                .doOnNext(i -> System.out.println("reactive-math-service processing : " + i))
//                .map(i -> new Response(i * input));
//    }
//
//
//}