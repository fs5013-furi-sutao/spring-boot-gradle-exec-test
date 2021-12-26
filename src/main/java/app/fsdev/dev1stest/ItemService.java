package app.fsdev.dev1stest;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public Item findById(int id) {
        return this.itemRepository.findById(id);
    }
}