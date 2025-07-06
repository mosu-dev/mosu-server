//package life.mosu.mosuserver.domain.inquiry;
//
//import com.querydsl.jpa.impl.JPAQuery;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import life.mosu.mosuserver.presentation.inquiry.dto.InquiryResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Repository;
//
//@Repository
//@RequiredArgsConstructor
//public class InquiryQueryRepositoryImpl implements InquiryQueryRepository {
//
//    private final JPAQueryFactory queryFactory;
//
//    @Override
//    public Page<InquiryResponse> searchInquiries(
//            InquiryStatus status,
//            Pageable pageable
//    ){
//
//    }
//
//
//    private JPAQuery<Tuple> baseQuery(QInquiryJpaEntity inquiry){
//        return queryFactory
//                .select(
//                    inquiry.id,
//                    inquiry.title,
//                    inquiry.content,
//                    inquiry.
//                    inquiry.status,
//                    inquiry.createdAt,
//
//                )
//
//    }
//}
