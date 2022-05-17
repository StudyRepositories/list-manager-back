package br.com.study.listmanagerback.controllers;

import br.com.study.listmanagerback.entities.ToDoList;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Named.named;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ToDoListControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Order(1)
    @ParameterizedTest
    @MethodSource("getToDoLists")
    @DisplayName("Should Create ToDoList And Return Http Created")
    public void shouldCreateToDoListAndReturnHttpCreated(ToDoList toDoList) throws Exception {
        String json = new ObjectMapper().writer().withDefaultPrettyPrinter()
                .writeValueAsString(toDoList);

        mockMvc.perform(post("/api/todolists")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(content().string(containsString(
                        toDoList.getName() + " has been created."
                )));
    }

    @Order(2)
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L})
    @DisplayName("Should Create ToDoList And Return Http Created")
    public void shouldFindToDoListById(Long id) throws Exception {
        MvcResult res = mockMvc.perform(get("/api/todolists/" + id))
                .andExpect(status().is(HttpStatus.FOUND.value()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        String body = res.getResponse().getContentAsString();
        ToDoList toDoList = objectMapper.readValue(body, ToDoList.class);

        Assertions.assertNotNull(toDoList);
    }

    @Order(3)
    @Test
    @DisplayName("Should Find All ToDoLists")
    public void shouldFindAllToDoLists() throws Exception {
        MvcResult res = mockMvc.perform(get("/api/todolists"))
                .andExpect(status().is(HttpStatus.FOUND.value()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        String body = res.getResponse().getContentAsString();
        List<ToDoList> toDoLists = objectMapper.readValue(body, new TypeReference<List<ToDoList>>(){});
        MatcherAssert.assertThat(toDoLists.size(), is(greaterThanOrEqualTo(0)));
    }

    @Order(4)
    @ParameterizedTest
    @MethodSource("getModifiedToDoList")
    @DisplayName("Should Update ToDoList By Id")
    public void shouldUpdateToDoListById(Long id, ToDoList modifiedToDoList) throws Exception {
        mockMvc.perform(put("/api/todolists/" + id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(modifiedToDoList)))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }

    @Order(5)
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L})
    @DisplayName("Should Delete ToDoList By Id")
    public void shouldDeleteToDoListById(Long id) throws Exception{
        mockMvc.perform(delete("/api/todolists/" + id))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }

    static Stream<Arguments> getToDoLists() {
        final ToDoList toDoList0 = new ToDoList(null, "AAA", "AAA");
        final ToDoList toDoList1 = new ToDoList(null, "BBB", "BBB");

        return Stream.of(
                Arguments.arguments(named(toDoList0.getName(), toDoList0)),
                Arguments.arguments(named(toDoList1.getName(), toDoList1)));
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
