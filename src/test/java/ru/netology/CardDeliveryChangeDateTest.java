package ru.netology;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.util.DataGenerate;

import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.util.DataGenerate.generateDate;

public class CardDeliveryChangeDateTest {
    private Faker faker;

    @BeforeEach
    public void setUpAll() {

        faker = new Faker(new Locale("ru"));
    }

    @Test
    public void shouldDataGenerate() {
    }

    @Test
    public void shouldSetForm() {

        String planningDate = generateDate(3);
        String planningDate2 = generateDate(7);

        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue(DataGenerate.generateCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue(DataGenerate.generateName());
        $("[data-test-id='phone'] input").setValue(DataGenerate.generatePhone());
        $(".checkbox__box").click();
        $("[type='button'] [class=button__text]").click();
        $("[class='notification__content']").shouldBe(visible).shouldHave(text("Успешно")).shouldHave(text("Встреча успешно запланирована на " + planningDate));

        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate2);
        $("[type='button'] [class=button__text]").click();
        $("[data-test-id='replan-notification'] [class='notification__content']").shouldBe(visible, Duration.ofSeconds(20)).shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $("[data-test-id='replan-notification'] [class=button__text]").shouldHave(text("Перепланировать")).click();
        $("[data-test-id='success-notification'] [class='notification__title']").shouldBe(visible, Duration.ofSeconds(20)).shouldHave(text("Успешно"));
        $("[data-test-id='success-notification'] [class='notification__content']").shouldBe(visible, Duration.ofSeconds(20)).shouldHave(text("Встреча успешно запланирована на " + planningDate2));

    }


}
