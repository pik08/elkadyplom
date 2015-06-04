# Selenium "kickstart"

## Instalacja Chromedrivera w systemie

* Upewnij się, że plik wykonywalny Google Chrome jest w odpowiedniej lokalizacji: [Chromedriver wiki].
Jeśli zależy ci na używaniu Google Chroma ze ścieżki innej niż domyślna, będziesz musiał w kodzie dodać:
```java
ChromeOptions options = new ChromeOptions();
options.setBinary("/path/to/other/chrome/binary");
```
a następnie użyć obiektu ```options``` w konstruktorze ```ChromeDriver```.

* Ściągnij odpowiednią wersję chromedrivera ze strony [Chromedriver download].

* Wypakuj zawartość archiwum i upewnij się, że zrobiłeś to w katalogu, który jest w zmiennej PATH.
Jeśli jesteś leniwy i masz linuxa, możesz uruchomić następujące komendy:
```sh
sudo apt-get install unzip
wget -N http://chromedriver.storage.googleapis.com/2.15/chromedriver_linux64.zip -P /tmp/
unzip /tmp/chromedriver_linux64.zip -d /tmp/
sudo mv /tmp/chromedriver /usr/local/share/
sudo ln -s /usr/local/share/chromedriver /usr/bin/chromedriver
```

## Test w Javie

* Do pom.xml dodaj:
```maven
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-chrome-driver</artifactId>
    <version>2.45.0</version>
</dependency>
```
Jeśli pojawi się błąd ```java.lang.NoClassDefFoundError: com/google/common/net/MediaType``` podczas uruchamiania testu, dodaj również:
```maven
<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>16.0.1</version>
</dependency>
```

* Stwórz plik Test.java o treści:
```java
package elkadyplom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Test {
  public static void main(String[] args) throws InterruptedException {
    // Tworzymy nową instancję chromedrivera
    // Będziemy używać interfejsu do WebDrivera,
    // dlatego równie dobrze może to być instancja Firefoxa,
    // a dalszy kod nadal będzie działał
    WebDriver driver = new ChromeDriver();

    // Zmieniamy adres na google.com
    driver.get("http://www.google.com");

    // Szukamy elementu w drzewie DOM o nazwie 'q'
    // Istnieją również inne selektory, patrz na klasę By
    WebElement element = driver.findElement(By.name("q"));

    // Wpisujemy jakiś tekst do znalezionego pola
    element.sendKeys("Hello world!");

    // Wysyłamy formularz. WebDriver zajmie się znalezieniem odpowiedniego formularza
    element.submit();

    // Czekamy aż strona się odświeży
    Thread.sleep(1000);

    // Wypisujemy nazwę strony, która aktualnie jest wyświetlana
    System.out.println("Page title is: " + driver.getTitle());

    // Zamykamy przeglądarkę
    driver.quit();
  }
}

```

* Uruchom metodę main z klasy Test i obserwuj wyniki

[Chromedriver download]:http://chromedriver.storage.googleapis.com/index.html
[Chromedriver wiki]:https://code.google.com/p/selenium/wiki/ChromeDriver#Requirements
