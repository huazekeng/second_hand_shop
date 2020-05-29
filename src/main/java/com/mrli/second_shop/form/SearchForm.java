package com.mrli.second_shop.form;

import lombok.Data;

@Data
public class SearchForm {
    //    key: this.key,
//    size: this.pageSize,
//    page: this.currentPage,
//    sort: this.sort,
//    priceGt: this.min,
//    priceLte: this.max
    private String key;

    private Integer size = 10;

    private Integer page = 1;

    private Integer sort = 1;

    private String priceGt;

    private String priceLte;
}
