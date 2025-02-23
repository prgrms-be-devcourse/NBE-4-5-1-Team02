package com.team2.demo.domain.product.controller;

import com.team2.demo.domain.product.dto.ProductDto;
import com.team2.demo.domain.product.service.ProductService;
import com.team2.demo.global.response.PaginationData;
import com.team2.demo.global.response.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Products", description = "상품 API")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @Operation(summary = "상품 목록 조회", description = "모든 등록된 상품 목록을 조회한다.")
    @GetMapping("/products")
    public RsData<PaginationData<ProductDto>> getProductList(
            @RequestParam(name="keyword-type", defaultValue = "title") String keywordType,
            @RequestParam(name="keyword", defaultValue = "") String keyword,
            @ParameterObject @PageableDefault(page = 0, size = 10) Pageable pageable) {

        Page<ProductDto> products = productService.getProductList(keywordType, keyword, pageable);
        return RsData.success(
                "Success.",
                PaginationData.<ProductDto>builder()
                        .data(products.getContent())
                        .page(products.getNumber())
                        .size(products.getSize())
                        .totalPages(products.getTotalPages())
                        .build()
        );
    }

    @Operation(summary = "주문 내 상품 단건 조회 (관리자)", description = "자신이 작성한 주문 내에 포함된 상품들을 페이지네이션 해 조회한다.")
    @GetMapping(path={"/admin/orders/{orderId}/products"})
    public RsData<PaginationData<ProductDto>> getProductsInOrderAdmin(
            @PathVariable(name = "orderId") String orderId,
            @ParameterObject @PageableDefault(page = 0, size = 10) Pageable pageable
    ){
        Page<ProductDto> items = productService.getProductsInOrder(orderId, pageable);

        PaginationData<ProductDto> products = PaginationData.<ProductDto>builder()
                .data(items.getContent())
                .page(items.getNumber())
                .size(items.getSize())
                .totalPages(items.getTotalPages())
                .build();

        return RsData.success("Success.", products);
    }

    @Operation(summary = "주문 내 상품 단건 조회 (사용자)", description = "주문 내에 포함된 상품들을 페이지네이션 해 조회한다.")
    @GetMapping(path="/orders/{orderId}/products")
    public RsData<PaginationData<ProductDto>> getProductsInOrder(
            @PathVariable(name = "orderId") String orderId,
            @RequestParam(name="email") String email,
            @ParameterObject @PageableDefault(page = 0, size = 10) Pageable pageable
    ){
        Page<ProductDto> items = productService.getProductsInOrder(orderId, email,pageable);

        PaginationData<ProductDto> products = PaginationData.<ProductDto>builder()
                .data(items.getContent())
                .page(items.getNumber())
                .size(items.getSize())
                .totalPages(items.getTotalPages())
                .build();

        return RsData.success("Success.", products);
    }
}
