package br.com.study.listmanagerback.services;

import br.com.study.listmanagerback.entities.ToDoList;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Named.named;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ToDoListServiceTests {

    @Autowired
    private ToDoListService service;

    @Order(1)
    @ParameterizedTest()
    @MethodSource("getToDoLists")
    @DisplayName("Should Create ToDoList And Return Positive String")
    public void shouldCreateToDoListAndReturnPositiveString(ToDoList toDoList) {
        String expected = toDoList.getName() + " has been created.";
        String actual = service.create(toDoList);

        Assertions.assertEquals(expected, actual);
    }

    @Order(2)
    @ParameterizedTest
    @MethodSource("getToDoLists")
    @DisplayName("Should Find ToDoList By Id")
    public void shouldFindToDoListById(ToDoList toDoList, Long id) {
        ToDoList expected = new ToDoList(id, toDoList.getName(), toDoList.getDescription());
        ToDoList actual = service.findById(id);

        Assertions.assertEquals(expected, actual);
    }

    @Order(3)
    @Test
    @DisplayName("Should Find All ToDoLists")
    public void shouldFindAllToDoLists() {
        Integer expected = 0;
        Integer actual = service.findAll().size();

        MatcherAssert.assertThat(actual, Matchers.is(Matchers.greaterThanOrEqualTo(expected)));
    }

    @Order(4)
    @ParameterizedTest
    @MethodSource("getModifiedToDoList")
    @DisplayName("Should Update ToDoList By Id")
    public void shouldUpdateToDoListById(Long id, ToDoList modifiedToDoList) {
        ToDoList expected = new ToDoList(id, modifiedToDoList.getName(), modifiedToDoList.getDescription());
        service.updateById(id, modifiedToDoList);
        ToDoList actual = service.findById(id);

        Assertions.assertEquals(expected, actual);
    }

    @Order(5)
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L})
    @DisplayName("Should Delete ToDoList By Id")
    public void shouldDeleteToDoListById(Long id) {
        service.deleteById(id);
        Assertions.assertNull(service.findById(id));
    }

    static Stream<Arguments> getToDoLists() {
        final ToDoList toDoList0 = new ToDoList(null, "AAA", "AAA");
        final ToDoList toDoList1 = new ToDoList(null, "BBB", "BBB");

        return Stream.of(
                Arguments.arguments(named(toDoList0.getName(), toDoList0), 1L),
                        Arguments.arguments(named(toDoList1.getName(), toDoList1), 2L)
        );
    }

    static Stream<Arguments> getModifiedToDoList() {
        final ToDoList toDoList0 = new ToDoList(null, "AAA Updated", "AAA Updated");
        final ToDoList toDoList1 = new ToDoList(null, "BBB Updated", "BBB Updated");

        return Stream.of(
                Arguments.arguments(1L, named(toDoList0.getName(), toDoList0)),
                Arguments.arguments(2L, named(toDoList1.getName(), toDoList1))
        );
    }
}
