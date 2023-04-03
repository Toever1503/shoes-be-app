package com.photoism.cms.domain.etc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "99. Test")
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class TestController {

    @Operation(summary = "api test", description = "[@Operation] post test api")
    @GetMapping(value = "/etc/test")
    public String test() {
        return "hello";
    }
}
