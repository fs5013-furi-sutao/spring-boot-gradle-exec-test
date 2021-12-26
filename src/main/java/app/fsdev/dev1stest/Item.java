package app.fsdev.dev1stest;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class Item {

    private int id;
    private String itemName;
    private int price;
}