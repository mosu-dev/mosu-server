package life.mosu.mosuserver.domain.inquiry;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import life.mosu.mosuserver.presentation.inquiry.dto.InquiryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InquiryQueryRepositoryImpl implements InquiryQueryRepository {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final JPAQueryFactory queryFactory;
    QInquiryJpaEntity inquiry = QInquiryJpaEntity.inquiryJpaEntity;

    @Override
    public Page<InquiryResponse> searchInquiries(
            InquiryStatus status,
            String sortField,
            boolean asc,
            Pageable pageable
    ) {

        JPAQuery<Tuple> query = baseQuery(inquiry)
                .where(buildStatusCondition(inquiry, status))
                .orderBy(buildOrderByCondition(sortField, asc))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        long total = getTotalCount(inquiry, status);

        // 결과 매핑
        List<InquiryResponse> content = query.fetch().stream()
                .map(this::mapToResponse)
                .toList();

        return new PageImpl<>(content, pageable, total);
    }


    private JPAQuery<Tuple> baseQuery(QInquiryJpaEntity inquiry) {
        return queryFactory
                .select(
                        inquiry.id,
                        inquiry.title,
                        inquiry.content,
                        inquiry.author,
                        inquiry.status,
                        inquiry.createdAt
                )
                .from(inquiry);

    }

    private BooleanExpression buildStatusCondition(QInquiryJpaEntity inquiry,
            InquiryStatus status) {
        return status != null ? inquiry.status.eq(status) : null;
    }

    private OrderSpecifier<?> buildOrderByCondition(String sortField, boolean asc) {
        ComparableExpressionBase<?> expression = switch (sortField) {
            case "createdAt" -> inquiry.createdAt;
            case "author" -> inquiry.author;
            case "status" -> inquiry.status;
            default -> inquiry.id;
        };
        return asc ? expression.asc() : expression.desc();
    }

    private long getTotalCount(QInquiryJpaEntity inquiry, InquiryStatus status) {
        return Optional.ofNullable(
                queryFactory
                        .select(inquiry.count())
                        .from(inquiry)
                        .where(
                                buildStatusCondition(inquiry, status)
                        )
                        .fetchOne()
        ).orElse(0L);
    }

    private InquiryResponse mapToResponse(Tuple tuple) {
        return new InquiryResponse(
                tuple.get(inquiry.id),
                tuple.get(inquiry.title),
                tuple.get(inquiry.content),
                tuple.get(inquiry.author),
                tuple.get(inquiry.status),
                tuple.get(inquiry.createdAt) != null ? tuple.get(inquiry.createdAt)
                        .format(FORMATTER)
                        : null);
    }
}
