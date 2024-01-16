# Uruchomienie aplikacji Currency Exchange

## Przygotowanie środowiska

### 1. Instalacja JDK 17
Upewnij się, że na Twoim komputerze jest zainstalowane JDK 17.



## Pobranie projektu

### 1. Pobranie repozytorium
Sklonuj repozytorium projektu lub pobierz jego kod źródłowy.
https://github.com/NickiRafal/Currency_Exchange_Beckend.git
### 2. Otwarcie projektu w IntelliJ IDEA
- Otwórz IntelliJ IDEA.
- Wybierz opcję "Open" i wskaż ścieżkę do pobranego projektu.

## Konfiguracja aplikacji
### 1. Tworzenie bazy danych
- Utwórz bazę danych MySQL
### 2. Konfiguracja bazy danych MySQL
W pliku `application.properties` lub `application.yml` umieść konfigurację bazy danych:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/currency
spring.datasource.username=nazwa_użytkownika
spring.datasource.password=hasło
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```
## Uruchomienie aplikacji

### 1. Uruchomienie aplikacji

   Znajdź klasę główną CurrencyExchangeBeckendApplication(z adnotacją @SpringBootApplication).
   Kliknij prawym przyciskiem myszy na klasie głównej i wybierz opcję "Run".
   
## Praca

### 1. Port

Aplikacja pracuje na porcie server.port=8081

## Linki do aplikacji

### 1. Frontend https://github.com/NickiRafal/Currency_Exchange_Frontend.git
### 2. Beckend  https://github.com/NickiRafal/Currency_Exchange_Beckend.git

### Aplikacja nie jest ukończona ! Na chwilę obecną działa tylko rejestracja użytkownika.
