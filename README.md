# Spring Boot + Gradle + VSCode でテストコードを始める方法

## 1. 必要なツールをインストールする

### 1-1. Windows 向けアプリ管理ツール「Scoop」をインストール

Windows 向けアプリ管理ツール Scoop の公式サイトのある通り、
以下のコマンドを実行して Scoop をインストールする。

`PowerShell で実行`
``` console
iwr -useb get.scoop.sh | iex
```

エラーが出る場合は、Powershell の実行ポリシーを変更して再度、上記のインストールコマンドを実行する。

`Powershell の実行ポリシーを変更`
``` console
Set-ExecutionPolicy RemoteSigned -scope CurrentUser
```

Scoop | A command-line installer for Windows  
https://scoop.sh/

### 1-2. VSCode をインストール

次のコマンドで VSCode ポータブルをインストールする。

``` console
scoop install vscode-portable
```

### 1-3. Java をインストール

Java がインストールされていない場合は、Java をインストールする。
Java がインストールされているかを確認するには、PowerShell などで次のコマンドを発行してみる。

`Java のバージョンを確認`
``` console
java --version
```

エラーが出る場合は Java がインストールされていない。

Java がインストールされていない場合は、次のコマンドでインストールできる Java を探す。
winget コマンドが使えない場合は、使っている Windows 環境に Winget が入っていないのでインストールしておく。

`インストールできる Java を探す`
``` console
winget search openjdk
```

`実行結果例: `
```
名前                                       ID                            バージョン   一致         ソース
---------------------------------------------------------------------------------------------------------
ojdkbuild OpenJDK JRE                      ojdkbuild.ojdkbuild           17.0010.12.1              winget
Microsoft Build of OpenJDK with Hotspot 17 Microsoft.OpenJDK.17          17.0.1.12    Tag: OpenJDK winget
Microsoft Build of OpenJDK with Hotspot 16 Microsoft.OpenJDK.16          16.0.2.7     Tag: OpenJDK winget
Microsoft Build of OpenJDK with Hotspot 11 Microsoft.OpenJDK.11          11.0.13.8    Tag: OpenJDK winget
```

推奨は Java のバージョン 11 なので、この中から OpenJDK 11 をインストールする。

`ID を完全一致（-e）させてインストール`
``` console
winget install -e --id Microsoft.OpenJDK.11
```

### 1-4. ビルドツール「Gradle」をインストール

次のコマンドで Gradle をインストールする。

``` console
scoop install gradle
```

## 2. VSCode の設定

### 2-1. 拡張機能のインストール

拡張機能ペイン（左サイドのブロックのようなアイコンをクリック）を開いて、
検索窓に「java」と入力。

すると、「Extension Pack for Java」が検索結果に出るので、これをインストールする。
Extension Pack for Java をインストールすることで、次の6つの拡張機能がインストールされる。

- Extension Pack for Java
- Debugger for Java
- Test Runner for Java
- Maven for Java
- Project Manager for Java
- Visual Studio IntelliCode

次に Spring Boot 用の拡張機能をインストールする。

拡張機能ペイン（左サイドのブロックのようなアイコンをクリック）を開いて、
検索窓に「spring」と入力。

すると、「Spring Boot Extension Pack」が検索結果に出るので、これをインストールする。
Spring Boot Extension Pack をインストールすることで、次の6つの拡張機能がインストールされる。

- Spring Boot Tools
- Spring Initializr Java Support
- Spring Boot Dashboard
  
次に Java の Getter や Setter、Constructor などのコーディングを削減できる 
Lombok 用の拡張機能をインストールする。

- Lombok Annotations Support for VS Code

### 2-2. エディタ設定

#### 2-2-1. タブスペース

コマンドパレット（F1キー または Ctrl + Shift + P）を開き、
`Preferences: Configure Language Specific Settings...` を選択する。 
次の選択で Java を選択すると、`settings.json` が開く。

"[java]" という項目が追加されているので以下のように設定する。

``` json
"[java]": {
    "editor.tabSize": 4,
    "editor.insertSpaces": true,
},
```

`editor.tabSize` では、タブのサイズをスペース4つ分に設定している。
`editor.insertSpaces` では、タブ文字をスペースに置き換えるかを設定する。
`true` に設定するとスペースに置き換えるようになる。

#### 2-2-2. 保存アクション

設定画面を開き、「Java > Save Actions: Organize Imports」を検索してチェックを入れる。
これにより、ファイル保存時に import 自動で追加または削除される。

## プロジェクトの作成

コマンドパレットを開き、「Spring Initializr: Create a Gradle Project...」を選択する。

順にプロジェクトに必要な選択肢が表示されるので、指示通りに進めていく。 
内容としては以下のサイトと同様なので、詳細は割愛する。

Spring Initializr  
https://start.spring.io/

ここの例では、 以下の情報でプロジェクトを作成する。

- Spring Boot version: 2.6.2
- Specify project language: Java
- Group Id: app.fsdev
- Artidact Id: dev1stest
- Specify packaging type: Jar
- Specify Java version: 11

Choose dependencies:
- Lombok
- Spring Web

## ライブラリのインストール

プロジェクトが作成されたら、以下のコマンドでライブラリをインストールする。

``` console
bradle build
```

Gradle では他にも様々なタスクを実行でき、実行できるタスク（コマンド）一覧は、
以下のコマンドで確認できる。

``` console
gradle tasks
```
## コーディング

### コーディング時の注意点

コーディングをしていると依存関係が解決できず、コンパイルエラーが出たり、補完機能が効かなくなったりする。

そういった場合は、コマンドパレットを開き「Java: Clean Java Language Server Workspace」を実行する。

### Entity を作成する。

まずは Entity を作成する。

Entity は、一般的にデータの格納オブジェクトとして利用される。

つまり、アプリ中で扱うデータを抽象化したモノが Entity というものである。

`@Component` アノテーションを付けておくことで、
利用クラスで Spring boot の `@Autowired` や Lombok の　`@RequiredArgsConstructor` 使って、
new を使わないインスタンス生成を実現できる。

new によるインスタンス生成をコーディングしないことで、
クラスの切り替えが Interface 実装クラスへの `@Component` の付け替えなどで簡単にできるようなる。

`src/main/java/app/fsdev/dev1stest/Item.java`
``` java
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
```

### Repository の作成

次に Entity を利用する Repository クラスを作成する。

Repository は、一般的にデータへ直接問い合わせる役目を任せられる。

ここでフィールドの Item インスタンスに `@Autowired` を付け、
コンストラクタを用意しておくことで、ItemRepository インスタンス生成時に、
自動で item フィールドにインスタンスが注入される。

この Spring Boot の機能を Dependency Injection（DI: 依存性の注入）という。

またこの動きのことを Inversion of Control（IoC: 制御の反転）という。

クラスの制御を普通はプログラマが行うが、
Spring Boot というフレームワーク側が行うという責務の反転が、DI によって実現される。

また `@Repository` アノテーションは `@Component` と同様に、
利用クラスで DI を行えるようにするアノテーションである。

`src/main/java/app/fsdev/dev1stest/ItemRepository.java`
``` java
package app.fsdev.dev1stest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepository {

    @Autowired
    private final Item item;

    public ItemRepository(Item item) {
        this.item = item;
    }

    public Item findById(int id) {
        this.item.setId(id);
        this.item.setItemName("Mr. Yamamoto");
        this.item.setPrice(777);
        return this.item;
    }
}
```

また `@Autowired` を使わずに Lombok の `@RequiredArgsConstructor` を使って 
DI を行うこともできる。その場合は、コンストラクタは必要なくなる。

`@RequiredArgsConstructor による ItemRepository.java の書き換え例: `
``` java
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
```

### Service の作成

次に Repository を利用する Service クラスを作成する。

一般的なアプリでは、Service クラスに取り扱う業務のロジックを書くことになる。

ここでは DI を `@RequiredArgsConstructor` でコーディングしているが、
もちろん `@Autowired` を使って DI する書き方にすることもできる。

`src/main/java/app/fsdev/dev1stest/ItemService.java`
``` java
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
```

### Controller の作成

次に Service を利用する Controller クラスを作成する。

一般的な Web アプリでは、Controller は、ユーザからのリクエストを受付け、
どの Service に作業を任せるかを割り振る役割を担う。

もちろんこのコードも `@Autowired` を使って DI する書き方にすることができる。

`src/main/java/app/fsdev/dev1stest/ItemController.java`
``` java
package app.fsdev.dev1stest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/item")
    @ResponseBody
    public ResponseEntity<Item> item() {
        Item item = this.itemService.findById(333);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<Item>(item, headers, HttpStatus.OK);
    }
}
```

### Application クラスの確認

今回は、アプリの起点となる main メソッドを持つクラスは、
Gradle が自動作成した Dev1stestApplication クラスをそのまま使う。

`src/main/java/app/fsdev/dev1stest/Dev1stestApplication.java`
``` java
package app.fsdev.dev1stest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Dev1stestApplication {

    public static void main(String[] args) {
        SpringApplication.run(Dev1stestApplication.class, args);
    }
}
```

## アプリの実行

アプリの実行方法は基本的には、以下の3つがある。いずれかの方法で実行する。

1. main メソッドの上部に出現している「Run | Debug」の「Run」リンクを押す
2. メインクラスをエディタで開いた際に右上に表示される再生ボタンのアイコンを押す
3. 左サイドの Explorer ペインを開いた際に表示される「Spring Boot Dashboard」の再生ボタンのアイコンを押す
4. VSCode ヘッダメニューの Terminal > New Terminal から PowerShell を開いて以下のコマンドを実行する

`4: Gradle コマンドでアプリを起動する`
``` console
gradle bootRun
```

Gradle をインストールしていない場合でも、次のコマンドを使えば、プロジェクトに用意されたバッチでアプリを起動できる。

`Gradle をインストールしていない場合`
``` console
./gradlew bootRun
```

起動したら、Google Chrome に以下の拡張機能をインストールして、拡張機能を開く。

Talend API Tester - Free Edition  
https://chrome.google.com/webstore/detail/talend-api-tester-free-ed/aejoelaoggembcahagimdiliamlcdmfm?hl=ja

以下の情報を入力して Send ボタンをクリックする。

- Method: GET
- Path: http://localhost:8080/item

リクエスト処理が成功すれば、以下のレスポンスが返却される。

- Status Code: 200

Headers:
- Content-Type: application/json
  
Body:
``` json
{
"id": 333,
"itemName": "Mr. Yamamoto",
"price": 777
}
```

## 単体テストコードの作成

今回は Repository をモック化して単体テストを行う。
以下のコードを書いて、クラスやメソッドの横に表示される再生ボタンをクリックすれば、テストを実行できる。

JUnit5 を使い、`@SpringBootTest` でテストを行う。

JUnit4 では `@RunWith(MockitoJUnitRunner.class)` が使えたが JUnit5 では使えない。

JUnit5 では `@ExtendWith(SpringExtension.class)` も使える。 

`src/test/java/app/fsdev/dev1stest/ItemServiceTest.java`
``` java
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
```

## 結合テストコードの作成

以下のコードを書いて、クラスやメソッドの横に表示される再生ボタンをクリックすれば、テストを実行できる。

`src/test/java/app/fsdev/dev1stest/ItemServiceTest.java`
``` java
package app.fsdev.dev1stest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
public class Dev1stestApplicationTest {

    private MockMvc mockMvc;

    @Autowired
    private ItemController itemController;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }

    @Test
    void get_test() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/item"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(333))
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemName")
                        .value("Mr. Yamamoto"))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.price").value(777));
    }
}
```
