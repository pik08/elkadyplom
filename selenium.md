# Selenium "kickstart"

Selenium to biblioteka przygotowana do wielu języków programowania, potrafiąca komunikować się ze sterownikiem przeglądarki internetowej, tak, aby zasymulować działanie użytkownika. Akcje, które Selenium potrafi wykonywać to wpisywanie tekstu do kontrolek, klikanie na przyciski, wysyłanie formularzy, wyszukiwanie elementów, itd. W szczególności, gdy zabraknie natywnych metod na zasymulowanie czegoś, można posłużyć się uruchomieniem w przeglądarce kodu JavaScriptowego.

Do uruchomienia testów z jej użyciem, będziemy potrzebowali przeglądarki internetowej, sterownika do tej przeglądarki i aplikacji napisanej w języku wspieranym przez Selenium. W tym artykule użyjemy Google Chrome, ChromeDriver oraz aplikacji w Javie. Selenium działa również z Firefoxem, Operą, Safari (dla OS X) i Internet Explorerem.

## Google Chrome

Opisywanie instalacji przeglądarki od Google'a nie jest celem tego artykułu. Po jej instalacji, upewnij się, że jej główny plik wykonywalny jest w odpowiedniej lokalizacji, wg poniższej tabelki:

| System Operacyjny | Oczekiwana lokalizacja Chrome'a                                                 |
| ----------------- | ------------------------------------------------------------------------------- |
| Linux             | /usr/bin/google-chrome                                                          |
| Mac               | /Applications/Google\ Chrome.app/Contents/MacOS/Google\ Chrome                  |
| Windows XP        | %HOMEPATH%\Local Settings\Application Data\Google\Chrome\Application\chrome.exe |
| Windows Vista     | C:\Users\%USERNAME%\AppData\Local\Google\Chrome\Application\chrome.exe          |

Dla systemu Linux, podana wyżej ścieżka powinna być symlinkiem do aktualnej wersji Google Chrome'a.

Jeśli zależy ci na używaniu przeglądarki ze ścieżki innej niż domyślna, będziesz musiał w kodzie dodać:
```java
ChromeOptions options = new ChromeOptions();
options.setBinary("/path/to/other/chrome/binary");
```
a następnie użyć obiektu ```options``` w konstruktorze ```ChromeDriver```. Ale o tym później.

## Instalacja Chromedrivera w systemie

* Ściągnij odpowiednią wersję chromedrivera ze strony [Chromedriver download]. Na ten moment najbardziej aktualna jest wersja 2.15 i jest ona kompatybilna z wersjami Google Chrome'a od 40 do 43.

* Wypakuj zawartość archiwum i upewnij się, że zrobiłeś to w katalogu, który jest w zmiennej PATH.
Jeśli jesteś leniwy i masz Linuxa, to mam dla ciebie prezent. Możesz uruchomić następujące komendy i wszystko będzie gotowe:
```sh
sudo apt-get install unzip
wget -N http://chromedriver.storage.googleapis.com/2.15/chromedriver_linux64.zip -P /tmp/
unzip /tmp/chromedriver_linux64.zip -d /tmp/
sudo mv /tmp/chromedriver /usr/local/share/
sudo ln -s /usr/local/share/chromedriver /usr/bin/chromedriver
```

## Test w Javie

* Jeśli używasz mavena, dodaj do pliku pom.xml:
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

## Poczytaj więcej

* Selenium strona domowa - [link][selenium-homepage]
* Chromedriver strona domowa - [link][chromedriver-homepage]

[Chromedriver download]:http://chromedriver.storage.googleapis.com/index.html
[Chromedriver wiki]:https://code.google.com/p/selenium/wiki/ChromeDriver
[chromedriver-homepage]:https://sites.google.com/a/chromium.org/chromedriver/
[selenium-homepage]:http://www.seleniumhq.org/
