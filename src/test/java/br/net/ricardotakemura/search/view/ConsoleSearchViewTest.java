package br.net.ricardotakemura.search.view;

import br.net.ricardotakemura.search.util.Configuration;
import br.net.ricardotakemura.search.util.FileUtils;
import br.net.ricardotakemura.search.view.impl.ConsoleSearchView;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConsoleSearchViewTest {

    enum Event {
        NONE,
        ON_SEARCH_RESULT,
        ON_CREATED_INDEX,
        ON_LOADED_INDEX,
        ON_FAILED_INDEX
    }

    static class MockConsoleSearchViewDelegate {

        private Event eventExecuted = Event.NONE;

        public Event getEventExecuted() {
            return eventExecuted;
        }

        public void onSearchResult(Set<File> result) {
            eventExecuted = Event.ON_SEARCH_RESULT;
        }

        public void onCreatedIndex() {
            eventExecuted = Event.ON_CREATED_INDEX;
        }

        public void onLoadedIndex() {
            eventExecuted = Event.ON_LOADED_INDEX;
        }

        public void onFailedIndex(Exception e) {
            eventExecuted = Event.ON_FAILED_INDEX;
        }
    }

    static class MockConsoleSearchView extends ConsoleSearchView {

        private MockConsoleSearchViewDelegate delegate;

        @Override
        public void onSearchResult(Set<File> result) {
            if (delegate != null) {
                delegate.onSearchResult(result);
            }
            super.onSearchResult(result);
        }

        @Override
        public void onCreatedIndex() {
            if (delegate != null) {
                delegate.onCreatedIndex();
            }
            super.onCreatedIndex();
        }

        @Override
        public void onLoadedIndex() {
            if (delegate != null) {
                delegate.onLoadedIndex();
            }
            super.onLoadedIndex();
        }

        @Override
        public void onFailedIndex(Exception e) {
            if (delegate != null) {
                delegate.onFailedIndex(e);
            }
            super.onFailedIndex(e);
        }

        public void setDelegate(MockConsoleSearchViewDelegate delegate) {
            this.delegate = delegate;
        }
    }

    private Configuration configuration;

    @BeforeAll
    void setUp() {
        configuration = Configuration.getInstance();
    }

    @AfterEach
    void tearDown() {
        FileUtils.delete(configuration.getProperty("application.data.index.file"));
    }

    @Test
    void test_create_indexes_with_success() {
        var consoleSearchView = new MockConsoleSearchView();
        var delegate = new MockConsoleSearchViewDelegate();
        consoleSearchView.setDelegate(delegate);
        consoleSearchView.start("--createindex", getClass().getResource("/data").getFile());
        assertEquals(Event.ON_CREATED_INDEX, delegate.getEventExecuted());
    }

    @Test
    void test_search_with_results_success() {
        var consoleSearchView = new MockConsoleSearchView();
        var delegate = new MockConsoleSearchViewDelegate() {
            @Override
            public void onSearchResult(Set<File> result) {
                super.onSearchResult(result);
                assertEquals(1, result.size());
            }
        };
        consoleSearchView.setDelegate(delegate);
        consoleSearchView.start("--createindex", getClass().getResource("/data").getFile());
        assertEquals(Event.ON_CREATED_INDEX, delegate.getEventExecuted());
        consoleSearchView.start("hello world");
        assertEquals(Event.ON_SEARCH_RESULT, delegate.getEventExecuted());
    }

    @Test
    void test_search_without_results_success() {
        var consoleSearchView = new MockConsoleSearchView();
        var delegate = new MockConsoleSearchViewDelegate() {
            @Override
            public void onSearchResult(Set<File> result) {
                super.onSearchResult(result);
                assertEquals(0, result.size());
            }
        };
        consoleSearchView.setDelegate(delegate);
        consoleSearchView.start("--createindex", getClass().getResource("/data").getFile());
        assertEquals(Event.ON_CREATED_INDEX, delegate.getEventExecuted());
        consoleSearchView.start("world abacaxi");
        assertEquals(Event.ON_SEARCH_RESULT, delegate.getEventExecuted());
    }

    @Test
    void test_search_with_fail() {
        var consoleSearchView = new MockConsoleSearchView();
        var delegate = new MockConsoleSearchViewDelegate();
        consoleSearchView.setDelegate(delegate);
        consoleSearchView.start("hello world");
        assertEquals(Event.ON_FAILED_INDEX, delegate.getEventExecuted());
    }

    @Test
    void test_search_without_arguments() {
        var consoleSearchView = new MockConsoleSearchView();
        var delegate = new MockConsoleSearchViewDelegate();
        consoleSearchView.setDelegate(delegate);
        consoleSearchView.start();
        assertEquals(Event.NONE, delegate.getEventExecuted());
    }

    @Test
    void test_search_with_invalid_arguments() {
        var consoleSearchView = new MockConsoleSearchView();
        var delegate = new MockConsoleSearchViewDelegate();
        consoleSearchView.setDelegate(delegate);
        consoleSearchView.start("--indice", "basico");
        assertEquals(Event.NONE, delegate.getEventExecuted());
    }
}
