package tests;

import apiMethods.UsersMethods;
import com.fasterxml.jackson.core.JsonProcessingException;
import dto.UserDto;
import endpoints.Logical;
import enums.StudentNames;
import enums.UserRoles;
import extensions.ChangeConstant;
import extensions.GetUserId;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.List;
import java.util.Optional;

import static org.apache.http.HttpStatus.SC_OK;

@DisplayName("Тесты студентов и преподавателей Хогвартса")
public class HogwartsInhabitantsTests extends BaseTest {
    SoftAssertions softAssertions = new SoftAssertions();

    @AfterEach
    void assertAll() {
        softAssertions.assertAll();
    }

    @DisplayName("Проверка, что Амбридж теперь директор")
    @Test
    @GetUserId(
            name = "Долорес Амбридж"
    )
    void checkIfAmbridgeIsDirectorTest(String userId) {
        UserDto userDto = usersMethods.getUserById(SC_OK, UserDto.class, userId);
        softAssertions.assertThat(userDto.getRole()).as("Долорес Амбридж должна быть директором")
                .isEqualTo(UserRoles.DIRECTOR.getRole());
    }

    @DisplayName("Проверка, что Филч поймал всех студентов")
    @Test
    @ChangeConstant(
            constantName = "isLightOn",
            beforeTestConstantName = "true",
            afterTestConstantName = "false"
    )
    void checkIfFilchCaughtAllStudentsTest() {
        reloadUsers();
        //Студенты пытаются спрятаться при включенном свете
        logicMethods.hideOrCatchStudents(Logical.HIDE_STUDENTS.getValue());
        //Филч ловит студентов
        List<UserDto> users = logicMethods.hideOrCatchStudents(Logical.CATCH_STUDENTS.getValue());

        softAssertions.assertThat(users.size()).as("Филч должен поймать всех студентов")
                .isEqualTo(4);
        softAssertions.assertThat(users.stream()
                        .map(UserDto::getName)
                        .toList())
                .contains(StudentNames.HARRY_POTTER.getName())
                .contains(StudentNames.GERMIONE_GRANGER.getName())
                .contains(StudentNames.RONALD_WISLEY.getName())
                .contains(StudentNames.NEVILL_LONGBOTTOM.getName());

    }

    @DisplayName("Проверка, что Филч поймал только одного из студентов")
    @Test
    @ChangeConstant(
            constantName = "isLightOn",
            beforeTestConstantName = "false",
            afterTestConstantName = "false"
    )
    void checkIfFilchCaughtOnlyOneStudentTest() {
        reloadUsers();
        //Студенты пытаются спрятаться при выключенном свете
        logicMethods.hideOrCatchStudents(Logical.HIDE_STUDENTS.getValue());
        //Филч ловит только одного студента
        List<UserDto> users = logicMethods.hideOrCatchStudents(Logical.CATCH_STUDENTS.getValue());

        softAssertions.assertThat(users.size()).as("Филч должен поймать только одного студента")
                .isEqualTo(1);
        softAssertions.assertThat(users.getFirst().getRole())
                .isEqualTo(UserRoles.STUDENT.getRole());
    }

    @DisplayName("Тест на зелье правды и выданных членов Отряда Дамблдора")
    @ParameterizedTest
    @CsvFileSource(
            resources = "/veritaserum.csv",
            numLinesToSkip = 1
    )
    void checkVeritaserumPotionTest(String veritaserum, Integer size) throws JsonProcessingException {
        //Снейп решает, какой будет эффект от зелья правды
        mongoDbMethods.changeConstantByName("veritaserum", veritaserum);
        //Снейп дает зелье пойманному ученику
        List<UserDto> users = logicMethods.ratDumbledoreSquad();

        softAssertions.assertThat(users.size())
                .as(String.format("Пойманный студент должен выдать только %d участников отряда", size))
                .isEqualTo(size);
    }

    @DisplayName("Тест на отправку Совы")
    @Test
    @ChangeConstant(
            constantName = "owlAdress",
            beforeTestConstantName = "http://phoenix-order.com/",
            afterTestConstantName = "http://magic-ministry.com/"
    )
    void sendOwlToPhoenixOrderTest() {
        //Оставшиеся члены отряда Дамблдора перехватывают сову и отправляют письмо в Орден Феникса
        String answer = logicMethods.sendOwl();

        softAssertions.assertThat(answer)
                .as("Письмо должно было попасть в Орден Феникса")
                .isEqualTo("Good response");
    }

    @DisplayName("Тест на создание заклинания, выгоняющее Амбридж из Хогвартса")
    @Test
    @ChangeConstant(
            constantName = "owlTimeout",
            beforeTestConstantName = "6",
            afterTestConstantName = "2"
    )
    void castSpellAgainstAmbridgeTest() {
        //Отряд Дамблдора сможет создать заклинание, которое должно вернуть в Хогвартс Дамблдора,
        List<UserDto> users = logicMethods.castSpell();
        Optional<UserDto> dumbledore = users.stream().filter(x-> x.getName().equals("Альбус Дамблдор"))
                        .findFirst();
        Optional<UserDto> ambridge = users.stream().filter(x-> x.getName().equals("Долорес Амбридж"))
                .findFirst();
        softAssertions.assertThat(dumbledore.get().getRole())
                .as("Дамблдор должен стать директором")
                .isEqualTo(UserRoles.DIRECTOR.getRole());
        softAssertions.assertThat(ambridge.get().getRole())
                .as("Амбридж должны уволить")
                .isEqualTo(UserRoles.UNEMPLOYED.getRole());
        softAssertions.assertThat(dumbledore.get().getIsArrested())
                .as("Дамблдор не должен быть арестован")
                .isFalse();
        softAssertions.assertThat(ambridge.get().getIsArrested())
                .as("Амбридж пора отправиться в Азкабан")
                .isTrue();
    }


    private void reloadUsers() {
        new UsersMethods().deleteUsersAndCreateDefaultUsers();
    }
}
