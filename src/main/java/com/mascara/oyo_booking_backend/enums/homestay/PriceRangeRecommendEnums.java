package com.mascara.oyo_booking_backend.enums.homestay;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 30/06/2024
 * Time      : 6:45 CH
 * Filename  : PriceRangeRecommendEnums
 */
public enum PriceRangeRecommendEnums {
    LOW_PRICE(0D, 350000D),
    MID_PRICE(350000D, 500000D),
    LUXURY_PRICE(500000D, 10000000D);
    private Double rangeStart;
    private Double rangeEnd;

    PriceRangeRecommendEnums(Double rangeStart, Double rangeEnd) {
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }
    }
