package br.net.ricardotakemura.search.presenter;

import br.net.ricardotakemura.search.presenter.impl.SimpleSearchPresenter;
import br.net.ricardotakemura.search.util.Configuration;
import br.net.ricardotakemura.search.util.FileUtils;
import br.net.ricardotakemura.search.view.SearchView;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SearchPresenterTest {

    private SearchPresenter presenter;
    private Configuration configuration;

    static abstract class MockSearchView implements SearchView {

        @Override
        public void start(String... args)  {
            //DO NOTHING
        }

        @Override
        public void onCreatedIndex() {
            //DO NOTHING
        }

        @Override
        public void onLoadedIndex() {
            //DO NOTHING
        }

        @Override
        public void onFailedIndex(Exception e) {
            fail(e);
        }
    }

    @BeforeAll
    void setUp() {
        configuration = Configuration.getInstance();
        presenter = new SimpleSearchPresenter();
        presenter.createIndexes(new File(getClass().getResource("/data").getFile()));
        presenter.loadIndexes();
    }

    @AfterAll
    void tearDown() {
        FileUtils.delete(configuration.getProperty("application.data.index.file"));
    }

    @Test
    void test_search_with_single_result() {
        presenter.setView(new MockSearchView() {
            @Override
            public void onSearchResult(Set<File> result) {
                assertEquals(1, result.size());
                assertTrue(result.stream().anyMatch(it -> it.getPath().contains("alo_mundo.txt")));
            }
        });
        presenter.search("alo");
    }

    @Test
    void test_search_with_two_results() {
        presenter.setView(new MockSearchView() {
            @Override
            public void onSearchResult(Set<File> result) {
                assertEquals(2, result.size());
                assertTrue(result.stream().anyMatch(it -> it.getPath().contains("alo_mundo.txt")));
                assertTrue(result.stream().anyMatch(it -> it.getPath().contains("hello_world.txt")));
            }
        });
        presenter.search("computer");
    }

    @Test
    void test_search_without_result() {
        presenter.setView(new MockSearchView() {
            @Override
            public void onSearchResult(Set<File> result) {
                assertEquals(0, result.size());
            }
        });
        presenter.search("teste");

    }

    @Test
    void test_search_with_number_and_two_results() {
        presenter.setView(new MockSearchView() {
            @Override
            public void onSearchResult(Set<File> result) {
                assertEquals(2, result.size());
                assertTrue(result.stream().anyMatch(it -> it.getPath().contains("alo_mundo.txt")));
                assertTrue(result.stream().anyMatch(it -> it.getPath().contains("hello_world.txt")));
            }
        });
        presenter.search("02");
    }

    @Test
    void test_search_with_two_words_and_one_result() {
        presenter.setView(new MockSearchView() {
            @Override
            public void onSearchResult(Set<File> result) {
                assertEquals(1, result.size());
                assertTrue(result.stream().anyMatch(it -> it.getPath().contains("hello_world.txt")));
            }
        });
        presenter.search("hello world");
    }

    @Test
    void test_search_with_two_words_and_two_blanks_and_one_result() {
        presenter.setView(new MockSearchView() {
            @Override
            public void onSearchResult(Set<File> result) {
                assertEquals(1, result.size());
                assertTrue(result.stream().anyMatch(it -> it.getPath().contains("hello_world.txt")));
            }
        });
        presenter.search("hello  world");
    }

    @Test
    void test_search_with_two_words_and_no_result() {
        presenter.setView(new MockSearchView() {
            @Override
            public void onSearchResult(Set<File> result) {
                assertEquals(0, result.size());
            }
        });
        presenter.search("hello alo");
    }

    @Test
    void test_search_with_three_words_and_one_result() {
        presenter.setView(new MockSearchView() {
            @Override
            public void onSearchResult(Set<File> result) {
                assertEquals(1, result.size());
                assertTrue(result.stream().anyMatch(it -> it.getPath().contains("hello_world.txt")));
            }
        });
        presenter.search("hello world computer");
    }

    @Test
    void test_search_with_three_words_and_four_blanks_and_one_result() {
        presenter.setView(new MockSearchView() {
            @Override
            public void onSearchResult(Set<File> result) {
                assertEquals(1, result.size());
                assertTrue(result.stream().anyMatch(it -> it.getPath().contains("hello_world.txt")));
            }
        });
        presenter.search("hello  world  computer");
    }

    @Test
    void test_search_with_three_words_and_no_result() {
        presenter.setView(new MockSearchView() {
            @Override
            public void onSearchResult(Set<File> result) {
                assertEquals(0, result.size());
            }
        });
        presenter.search("computer banana world");
    }

    @Test
    void test_search_with_blank_string_and_no_result() {
        presenter.setView(new MockSearchView() {
            @Override
            public void onSearchResult(Set<File> result) {
                assertEquals(0, result.size());
            }
        });
        presenter.search("\t ");
    }

    @Test
    void test_search_with_empty_string_and_no_result() {
        presenter.setView(new MockSearchView() {
            @Override
            public void onSearchResult(Set<File> result) {
                assertEquals(0, result.size());
            }
        });
        presenter.search("");
    }

    @Test
    void test_search_with_null_and_throws_exception() {
        try {
            presenter.search(null);
            fail("Expected exception");
        } catch (Exception e) {
            assertEquals(e.getClass(), NullPointerException.class);
        }
    }

}
