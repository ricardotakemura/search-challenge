package br.net.ricardotakemura.search.model;

import java.io.File;

import br.net.ricardotakemura.search.util.Configuration;
import br.net.ricardotakemura.search.util.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import br.net.ricardotakemura.search.model.impl.MapSearchModelImpl;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(Lifecycle.PER_CLASS)
public class SearchModelTest {

    private SearchModel model;
    private Configuration configuration;

    @BeforeAll
    void setUp() throws Exception {
        configuration = Configuration.getInstance();
        model = new MapSearchModelImpl();
        model.createIndexes(new File(getClass().getResource("/data").getFile()));
        model.loadIndexes();
    }

    @AfterAll
    void tearDown() {
        FileUtils.delete(configuration.getProperty("application.data.index.file"));
    }

    @Test
    void test_search_with_single_result() {
        var result = model.search("hello");
        assertEquals(1, result.size());
        assertFalse(result.stream().anyMatch(it -> it.getPath().contains("alo_mundo.txt")));
        assertTrue(result.stream().anyMatch(it -> it.getPath().contains("hello_world.txt")));
    }

    @Test
    void test_search_with_two_results() {
        var result = model.search("world");
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(it -> it.getPath().contains("alo_mundo.txt")));
        assertTrue(result.stream().anyMatch(it -> it.getPath().contains("hello_world.txt")));
    }

    @Test
    void test_search_without_result() {
        var result = model.search("abacaxi");
        assertEquals(0, result.size());
    }

    @Test
    void test_search_with_number_and_two_results() {
        var result = model.search("02");
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(it -> it.getPath().contains("alo_mundo.txt")));
        assertTrue(result.stream().anyMatch(it -> it.getPath().contains("hello_world.txt")));
    }

    @Test
    void test_search_with_two_arguments_and_one_result() {
        var result = model.search("world", "hello");
        assertEquals(1, result.size());
        assertTrue(result.stream().anyMatch(it -> it.getPath().contains("hello_world.txt")));
    }

    @Test
    void test_search_with_two_arguments_and_no_result() {
        var result = model.search("alo", "hello");
        assertEquals(0, result.size());
    }

    @Test
    void test_search_with_three_arguments_and_one_result() {
        var result = model.search("computer", "world", "hello");
        assertEquals(1, result.size());
        assertTrue(result.stream().anyMatch(it -> it.getPath().contains("hello_world.txt")));
    }

    @Test
    void test_search_with_three_arguments_and_no_result() {
        var result = model.search("world", "computer", "banana");
        assertEquals(0, result.size());
    }

    @Test
    void test_search_without_argument_and_no_result() {
        var result = model.search();
        assertEquals(0, result.size());
    }

    @Test
    void test_search_with_blank_string_argument_and_no_result() {
        var result = model.search(" \n");
        assertEquals(0, result.size());
    }

    @Test
    void test_search_with_empty_string_argument_and_no_result() {
        var result = model.search("");
        assertEquals(0, result.size());
    }

    @Test
    void test_search_with_null_argument_and_no_result() {
        var result = model.search(null);
        assertEquals(0, result.size());
    }

    @Test
    void test_search_with_null_argument_and_valid_argument_and_no_result() {
        var result = model.search(null, "hello");
        assertEquals(0, result.size());
    }

    @Test
    void test_search_with_valid_argument_and_null_argument_and_no_result() {
        var result = model.search("hello", null);
        assertEquals(0, result.size());
    }
}
