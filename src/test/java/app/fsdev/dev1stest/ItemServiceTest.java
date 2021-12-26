package app.fsdev.dev1stest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

// 1. Spring Boot のテストケースであることを明示する
@SpringBootTest
public class ItemServiceTest {

	// 2. 対象クラスのインスタンス作成と@Mockを付けたクラスをインジェクションする
	@InjectMocks
	private ItemService itemService;

	// 3. モック化対象
	@Mock
	private ItemRepository itemRepository;

	@Test
	public void test() {

		// 4.モック化したメソッドの動作を設定する。
		int id = 1;
		Item requiredItem = new Item();
		requiredItem.setId(id);
		requiredItem.setItemName("MockItem");
		requiredItem.setPrice(100);
		when(itemRepository.findById(id)).thenReturn(requiredItem);

		Item item = itemService.findById(1);

		// 5. モック化されたオブジェクトの値を検証
		assertEquals(item.getId(), 1);
		assertEquals(item.getItemName(), "MockItem");
		assertEquals(item.getPrice(), 100);
	}
}