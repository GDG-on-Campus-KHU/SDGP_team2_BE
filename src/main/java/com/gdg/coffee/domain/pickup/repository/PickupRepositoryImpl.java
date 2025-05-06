package com.gdg.coffee.domain.pickup.repository;

import com.gdg.coffee.domain.bean.domain.QBean;
import com.gdg.coffee.domain.cafe.domain.QCafe;
import com.gdg.coffee.domain.ground.domain.QCoffeeGround;
import com.gdg.coffee.domain.member.domain.QMember;
import com.gdg.coffee.domain.pickup.domain.QPickup;
import com.gdg.coffee.domain.pickup.dto.PickupCafeSummaryDto;
import com.gdg.coffee.domain.pickup.dto.PickupStatus;
import com.gdg.coffee.domain.pickup.dto.PickupUserSummaryDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PickupRepositoryImpl implements PickupRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    private final QPickup p = QPickup.pickup;
    private final QCoffeeGround g = QCoffeeGround.coffeeGround;
    private final QBean b = QBean.bean;
    private final QCafe c = QCafe.cafe;
    private final QMember m = QMember.member;

    @Override
    public List<PickupCafeSummaryDto> findCafePickupListByStatusDsl(Long cafeId, PickupStatus status) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(g.cafe.id.eq(cafeId));

        if (status != null) {
            builder.and(p.status.eq(status));
        }

        return queryFactory
                .select(Projections.constructor(
                        PickupCafeSummaryDto.class,
                        p.id,
                        m.username,
                        p.pickupDate,
                        p.createdDate,
                        b.name,
                        p.amount,
                        p.status
                ))
                .from(p)
                .join(p.member, m).fetchJoin()   // Lazy 로딩 방지
                .join(p.ground, g).fetchJoin()
                .join(g.bean, b).fetchJoin()
                .where(builder)
                .fetch();
    }

    @Override
    public List<PickupUserSummaryDto> findUserPickupListByStatusDsl(Long userId, PickupStatus status) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(p.member.id.eq(userId));

        if (status != null) {
            builder.and(p.status.eq(status));
        }

        return queryFactory
                .select(Projections.constructor(
                        PickupUserSummaryDto.class,
                        p.id,
                        c.name,
                        p.pickupDate,
                        p.createdDate,
                        b.name,
                        p.amount,
                        p.status
                ))
                .from(p)
                .join(p.ground, g).fetchJoin()
                .join(g.cafe, c).fetchJoin()
                .join(g.bean, b).fetchJoin()
                .where(builder)
                .fetch();
    }

}
