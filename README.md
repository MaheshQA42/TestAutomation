
# Shopping Basket (Java + Selenium + JUnit 5)

## Items & Offers
- Apple — **35p** each  
- Banana — **20p** each  
- Melon — **50p**, **Buy One Get One Free**  
- Lime — **15p**, **3 for the price of 2**

## Project Layout
```
shopping-basket/
├── pom.xml
├── README.md
├── src
│   ├── main/java/com/example/basket/BasketCalculator.java
│   └── test
│       ├── java/com/example/basket/BasketCalculatorTest.java
│       ├── java/com/example/basket/BasketUITest.java
│       └── resources/basket.html
└── .gitignore
```

## Requirementssssss
- Java 17+
- Maven 3.8+


## Run testsss
```bash
mvn -q test
```

### Headlesss modeee
The Selenium test uses headless Chrome by default via `--headless=new`. You can remove that option in `BasketUITest` if you want to see the browser.

