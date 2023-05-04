package com.vinsguru.webfluxdemo.controller;


import com.vinsguru.webfluxdemo.dto.Response;
import com.vinsguru.webfluxdemo.exception.InputValidationException;
import com.vinsguru.webfluxdemo.service.ReactiveMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("reactive-math")
public class ReactiveMathValidationController {

    @Autowired
    private ReactiveMathService mathService;

    @GetMapping( "square/{input}/throw")
    public Mono<Response> findSquare(@PathVariable int input) {
        if(input <10 || input > 20){
            throw new InputValidationException(input);
        }
        return this.mathService.findSquare(input);
    }

    // throw new InputValidationException(input) will create InputValidationException
    // that exception is handled by InputValidationHandler
    // How
    // InputValidationHandler will build InputFailedValidationResponse
    // returns response Entity of <InputFailedValidationResponse , static code>

    @GetMapping("square/{input}/mono-error")
    public Mono<Response> monoError(@PathVariable int input){
        return Mono.just(input)
                .handle(((integer, sink) -> {
                    if(integer >=10 && integer <= 20) sink.next(integer); // IMP
                    else sink.error(new InputValidationException(integer));
                }))
                .cast(Integer.class)
                .flatMap(i -> this.mathService.findSquare(i));
    }

}
