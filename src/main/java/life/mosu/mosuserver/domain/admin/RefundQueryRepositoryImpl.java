package life.mosu.mosuserver.domain.admin;

import static life.mosu.mosuserver.domain.base.BaseTimeEntity.formatDate;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import life.mosu.mosuserver.domain.application.QApplicationJpaEntity;
import life.mosu.mosuserver.domain.applicationschool.QApplicationSchoolJpaEntity;
import life.mosu.mosuserver.domain.payment.PaymentMethod;
import life.mosu.mosuserver.domain.payment.QPaymentJpaEntity;
import life.mosu.mosuserver.domain.profile.QProfileJpaEntity;
import life.mosu.mosuserver.domain.refund.QRefundJpaEntity;
import life.mosu.mosuserver.presentation.admin.dto.RefundListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefundQueryRepositoryImpl implements RefundQueryRepository {

    private final JPAQueryFactory queryFactory;

    QRefundJpaEntity refund = QRefundJpaEntity.refundJpaEntity;
    QApplicationSchoolJpaEntity appSchool = QApplicationSchoolJpaEntity.applicationSchoolJpaEntity;
    QApplicationJpaEntity application = QApplicationJpaEntity.applicationJpaEntity;
    QProfileJpaEntity profile = QProfileJpaEntity.profileJpaEntity;
    QPaymentJpaEntity payment = QPaymentJpaEntity.paymentJpaEntity;

    @Override
    public Page<RefundListResponse> searchAllRefunds(Pageable pageable) {
        long total = baseQuery().fetch().size();

        List<RefundListResponse> content = baseQuery()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(this::mapToResponse)
                .toList();

        return new PageImpl<>(content, pageable, total);

    }

    private JPAQuery<Tuple> baseQuery() {
        return queryFactory
                .select(
                        refund.id,
                        appSchool.examinationNumber,
                        profile.userName,
                        profile.phoneNumber,
                        refund.createdAt,
                        refund.agreedAt,
                        payment.paymentMethod,
                        refund.reason
                )
                .from(refund)
                .leftJoin(appSchool).on(refund.applicationSchoolId.eq(appSchool.id))
                .leftJoin(application).on(appSchool.applicationId.eq(application.id))
                .leftJoin(profile).on(profile.userId.eq(application.userId))
                .leftJoin(payment).on(payment.applicationSchoolId.eq(appSchool.id));
    }

    private RefundListResponse mapToResponse(Tuple tuple) {
        PaymentMethod paymentMethod = tuple.get(payment.paymentMethod);
        return new RefundListResponse(
                tuple.get(refund.id),
                tuple.get(appSchool.examinationNumber),
                tuple.get(profile.userName),
                tuple.get(profile.phoneNumber),
                formatDate(tuple.get(refund.createdAt)),
                formatDate(tuple.get(refund.agreedAt)),
                paymentMethod != null ? paymentMethod.getName() : "N/A",
                tuple.get(refund.reason)
        );
    }


}