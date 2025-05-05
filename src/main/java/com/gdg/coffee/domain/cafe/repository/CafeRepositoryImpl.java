package com.gdg.coffee.domain.cafe.repository;

import com.gdg.coffee.domain.cafe.domain.Cafe;
import com.gdg.coffee.domain.cafe.domain.QCafe;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CafeRepositoryImpl implements CafeRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QCafe cafe = QCafe.cafe;

    // 고정된 반경 5km 상수 정의
    private static final double FIXED_RADIUS_KM = 5.0;

    @Override
    public List<Cafe> findCafesNear(double latitude, double longitude) {

        // 데이터베이스 공간 함수 ST_Distance_Sphere를 사용하여 거리를 계산하는 표현식 (결과: 미터)
        NumberExpression<Double> distance = Expressions.numberTemplate(Double.class,
                "ST_Distance_Sphere(point({0}, {1}), point({2}, {3}))",
                cafe.longitude, cafe.latitude, longitude, latitude);

        // 킬로미터로 변환 (미터 / 1000)
        NumberExpression<Double> distanceKm = distance.divide(1000.0);

        return queryFactory
                .selectFrom(cafe)
                .where(distanceKm.loe(FIXED_RADIUS_KM)) // 계산된 거리가 고정된 반경(5km) 이하인 조건
                .fetch();
    }
}