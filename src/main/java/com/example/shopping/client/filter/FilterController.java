package com.example.shopping.client.filter;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("filter")
@AllArgsConstructor
public class FilterController {
    private FilterService filterService;

    @PostMapping("")
    public ResponseEntity<?> filter(@RequestBody FilterDto filterDto,
                                    @RequestParam(name = "page", defaultValue = "10") Integer page,
                                    @RequestParam(name = "size",defaultValue = "1") Integer size) {
      return ResponseEntity.ok(filterService.filter(filterDto,page,size));
    }

}
