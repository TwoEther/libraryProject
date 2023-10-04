package org.project.shop.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.project.shop.domain.CartItem;
import org.project.shop.domain.QCartItem;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.project.shop.domain.QCart.cart;
import static org.project.shop.domain.QCartItem.cartItem;
import static org.project.shop.domain.QItem.item;
import static org.project.shop.domain.QMember.member;
import static org.project.shop.domain.QCart.cart;
import static org.project.shop.domain.QCartItem.cartItem;

@Repository
public class CartItemRepositoryImpl implements CartItemRepository{
    @PersistenceContext
    EntityManager em;
    private final JPAQueryFactory queryFactory;

    public CartItemRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public CartItem findByCartIdAndItemId(Long cartId, Long itemId) {
        return queryFactory.selectFrom(cartItem)
                .where(cartItem.cart.id.eq(cartId).
                        and(cartItem.item.id.eq(itemId)))
                .fetchOne();
    }

    @Override
    public CartItem findById(Long id) {
        return queryFactory.select(cartItem)
                .from(cartItem)
                .where(cartItem.id.eq(id))
                .fetchOne();
    }


    @Override
    @Transactional
    public void save(CartItem cartItem) {
        em.persist(cartItem);
    }


    @Override
    public void clear() {
        queryFactory.delete(cartItem).execute();
    }

    @Override
    public List<CartItem> findAllCartItem() {
        return queryFactory.selectFrom(cartItem).fetch();
    }


    @Override
    public List<CartItem> findByCartId(Long cartId) {
        return queryFactory.selectFrom(cartItem)
                .leftJoin(cartItem.cart, cart)
                .fetchJoin()
                .where(cart.id.eq(cartId))
                .fetch();
    }

    @Override
    public List<CartItem> findCartItemByItem(Long itemId) {
        return queryFactory.selectFrom(cartItem)
                .where(cartItem.item.eq(
                        JPAExpressions.selectFrom(item)
                                .where(item.id.eq(itemId))
                ))
                .fetch();
    }
}
