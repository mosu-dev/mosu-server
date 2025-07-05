package life.mosu.mosuserver.domain.admin;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import life.mosu.mosuserver.domain.application.QApplicationJpaEntity;
import life.mosu.mosuserver.domain.profile.QProfileJpaEntity;
import life.mosu.mosuserver.presentation.admin.dto.StudentFilter;
import life.mosu.mosuserver.presentation.admin.dto.StudentListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StudentQueryRepositoryImpl implements StudentQueryRepository {

    private final JPAQueryFactory queryFactory;

    NumberExpression<Long> examCountExpr = QApplicationJpaEntity.applicationJpaEntity.userId.count();

    @Override
    public Page<StudentListResponse> searchAllStudents(
            StudentFilter filter,
            Pageable pageable
    ) {
        String name = filter.name();
        String phone = filter.phone();
        String order = filter.order();

        QProfileJpaEntity profile = QProfileJpaEntity.profileJpaEntity;
        QApplicationJpaEntity application = QApplicationJpaEntity.applicationJpaEntity;

        JPAQuery<Tuple> query = baseQuery(profile, application)
                .where(
                        buildNameCondition(profile, name),
                        buildPhoneCondition(profile, phone)
                )
                .orderBy(
                        order.equals("asc") ? application.count().asc() : application.count().desc()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        long total = getTotalCount(profile, name, phone);

        // 결과 매핑
        List<StudentListResponse> content = query.fetch().stream()
                .map(tuple -> mapToResponse(tuple, profile))
                .toList();

        return new PageImpl<>(content, pageable, total);
    }

    private JPAQuery<Tuple> baseQuery(QProfileJpaEntity profile,
            QApplicationJpaEntity application) {
        return queryFactory
                .select(
                        profile.id,
                        profile.userName,
                        profile.birth,
                        profile.phoneNumber,
                        profile.gender,
                        profile.education,
                        profile.schoolInfo.schoolName,
                        profile.grade,
                        examCountExpr
                )
                .from(profile)
                .leftJoin(application)
                .on(profile.userId.eq(application.userId))
                .groupBy(profile.userId, profile.userName);
    }

    private Predicate buildNameCondition(QProfileJpaEntity profile, String name) {
        if (name == null || name.isBlank()) {
            return null;
        }

        return profile.userName.contains(name);
    }

    private Predicate buildPhoneCondition(QProfileJpaEntity profile, String phone) {
        if (phone == null || phone.isBlank()) {
            return null;
        }

        return profile.phoneNumber.contains(phone);
    }

    private long getTotalCount(QProfileJpaEntity profile, String name, String phone) {
        return Optional.ofNullable(
                queryFactory
                        .select(profile.count())
                        .from(profile)
                        .where(
                                buildNameCondition(profile, name),
                                buildPhoneCondition(profile, phone)
                        )
                        .fetchOne()
        ).orElse(0L);
    }

    private StudentListResponse mapToResponse(Tuple tuple, QProfileJpaEntity profile) {
        Long examCount = Optional.ofNullable(tuple.get(examCountExpr))
                .orElse(0L);
        return new StudentListResponse(
                tuple.get(profile.userName),
                tuple.get(profile.birth) != null ? tuple.get(profile.birth).toString() : null,
                tuple.get(profile.phoneNumber),
                tuple.get(profile.gender) != null ? tuple.get(profile.gender).name() : null,
                tuple.get(profile.education),
                tuple.get(profile.schoolInfo.schoolName),
                tuple.get(profile.grade),
                examCount.intValue()
        );
    }
}