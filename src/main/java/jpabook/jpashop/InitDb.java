package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.doInit1();
        initService.doInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void doInit1() {

            Member member = createMember("김치킨", "치킨시", "치킨중앙로 1길", "12345");
            em.persist(member);

            Book book1 = createBook("치킨을 올바르게 먹는 방법", 12000, 100);
            em.persist(book1);

            Book book2 = createBook("각종 치킨 레시피", 22000, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 12000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 22000, 2);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);

        }

        public void doInit2() {

            Member member = createMember("김피자", "피자시", "피자중앙로 1길", "54321");
            em.persist(member);

            Book book1 = createBook("피자를 깔끔하게 먹는 방법", 15000, 200);
            em.persist(book1);

            Book book2 = createBook("정통 이탈리아 피자 레시피", 25000, 300);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 12000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 22000, 4);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);

        }

        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private Book createBook(String name, int price, int stockQuantity) {
            Book book1 = new Book();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(stockQuantity);
            return book1;
        }

        private Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(name, street, zipcode));
            return member;
        }

    }

}
