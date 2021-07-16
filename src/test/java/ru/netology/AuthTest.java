package ru.netology;

import com.codeborne.selenide.Condition;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {
    @BeforeEach
    void befor() {
        open("http://localhost:9999");
    }

    @Test
    void inputValidLogPasStatActive() {
        val user = DataGenerator.UserInfo("active");
        DataGenerator.activeUser(user);
        $("span[data-test-id='login'] input").setValue(user.getLogin());
        $("span[data-test-id='password'] input").setValue(user.getPassword());
        $("button[data-test-id='action-login']").click();
        $(withText("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    void inputValidLogPasStat_Bocked() {
        val user = DataGenerator.UserInfo("blocked");
        DataGenerator.activeUser(user);
        $("span[data-test-id='login'] input").setValue(user.getLogin());
        $("span[data-test-id='password'] input").setValue(user.getPassword());
        $("button[data-test-id='action-login']").click();
        $("[class=notification__content]").shouldHave(text("Пользователь заблокирован"));
    }

    @Test
    void inputNo_Valid_LogValidPasStatActive() {
        val user = DataGenerator.UserInfo("active");
        DataGenerator.activeUser(user);
        $("span[data-test-id='login'] input").setValue(DataGenerator.generateLogin());
        $("span[data-test-id='password'] input").setValue(user.getPassword());
        $("button[data-test-id='action-login']").click();
        $("[class=notification__content]").shouldHave(text("Неверно указан логин или пароль"));
    }
    @Test
    void inputValidLogNo_Valid_PasStatActive() {
        val user = DataGenerator.UserInfo("active");
        DataGenerator.activeUser(user);
        $("span[data-test-id='login'] input").setValue(user.getLogin());
        $("span[data-test-id='password'] input").setValue(DataGenerator.generatePassword());
        $("button[data-test-id='action-login']").click();
        $("[class=notification__content]").shouldHave(text("Неверно указан логин или пароль"));
    }
}
