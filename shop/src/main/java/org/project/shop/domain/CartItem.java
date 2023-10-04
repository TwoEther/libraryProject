package org.project.shop.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CartItem {
    @Id
    @GeneratedValue
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;


    @ManyToOne(cascade = CascadeType.ALL, fetch =  FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private int count;

    public void setItem(Item item) {
        if (this.item != null) {
            this.item.getCartItem().remove(this);
        }
        this.item = item;
        cart.getCartItem().add(this);
    }


    public void setCount(int count) {
        this.count = count;
    }

    public void setCart(Cart cart) {
        if (this.cart != null) {
            this.cart.getCartItem().remove(this);
        }
        this.cart = cart;
        cart.getCartItem().add(this);
    }

    public static CartItem createCartItem(Cart cart, Item item, int count) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setCount(count);
        return cartItem;
    }

    public CartItem(Cart cart, Item item, int count) {
        this.item = item;
        this.cart = cart;
        this.count = count;
    }

    public CartItem() {
    }

    public void setItemId(Long itemId) {
        this.item.setId(itemId);
    }

    public void addCount(int count) {
        this.count += count;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", item=" + item.toString() +
                ", cart=" + cart.toString() +
                ", count=" + count +
                '}';
    }
}
