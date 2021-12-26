package app.fsdev.dev1stest;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ItemRepository {

    private final Item item;

    public Item findById(int id) {
        this.item.setId(id);
        this.item.setItemName("Mr. Yamamoto");
        this.item.setPrice(777);
        return this.item;
    }
}