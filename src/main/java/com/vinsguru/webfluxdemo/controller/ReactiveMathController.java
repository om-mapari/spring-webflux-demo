package com.vinsguru.webfluxdemo.controller;

import com.vinsguru.webfluxdemo.dto.MultiplyRequestDto;
import com.vinsguru.webfluxdemo.dto.Response;
import com.vinsguru.webfluxdemo.service.ReactiveMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("reactive-math")
public class ReactiveMathController {


    @Autowired
    private ReactiveMathService mathService;

    @GetMapping( "square/{input}")
    public Mono<Response> findSquare(@PathVariable int input) {
        return this.mathService.findSquare(input);
    }

    @GetMapping(value ="table/{input}")
    public Flux<Response> multiplicationTable(@PathVariable int input) {
        return this.mathService.multiplicationTable(input);
    }
    @GetMapping(value ="table/{input}/stream",produces = MediaType.TEXT_EVENT_STREAM_VALUE) // must do
    public Flux<Response> multiplicationTableStream(@PathVariable int input) {
        return this.mathService.multiplicationTable(input);
    }

    @PostMapping("multiply")
    public Mono<Response> multiply(@RequestBody Mono<MultiplyRequestDto> requestDtoMono)
    // we can also use like @RequestBody MultiplyRequestDto requestDtoMono to receive input
    {
        return this.mathService.multiply(requestDtoMono);
    }

}
