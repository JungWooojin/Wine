package com.team.winey.admin;

import com.team.winey.admin.model.ProductInsParam;
import com.team.winey.admin.model.ProductUpdParam;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService SERVICE;

    @Autowired
    public AdminController(AdminService SERVICE) {
        this.SERVICE = SERVICE;
    }

    @Operation(summary = "상품 등록")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public int postProduct(@RequestPart(required = false) MultipartFile pic, @RequestPart ProductInsParam param) {
        return SERVICE.postProduct(pic, param);
    }

    @Operation(summary = "상품 수정")
    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public int putProduct(@RequestPart(required = false) MultipartFile pic, @RequestPart ProductUpdParam param) {
        return SERVICE.putProduct(pic, param);
    }
}
