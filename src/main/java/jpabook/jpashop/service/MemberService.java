package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // public 메서드에는 전부 트랜잭션 처리 + 읽기용 트랜잭션이 많으므로 기본을 읽기전용으로 설정
@RequiredArgsConstructor    // final 로 선언된 필드만으로 생성자를 생성한다.
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    @Transactional  // 클래스단에서 읽기전용이 되어있기 때문에 따로 적용
    public Long join(Member member) {
        validateDuplicatedMember(member);   // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 단건 조회
     */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    private void validateDuplicatedMember(Member member) {
        // EXCEPTION
        int size = memberRepository.findByName(member.getName()).size();
        if (size > 0) {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }

}
