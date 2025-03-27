package com.interview.core.models;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import com.interview.core.testcontext.AppAemContext;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(AemContextExtension.class)
class DynamicSearchCardsModelTest {

    private final AemContext context = AppAemContext.newAemContext();
    private DynamicSearchCardsModel cards;
    private Resource resource;

    @BeforeEach
    public void setup() {
        context.create().page("/content/mypage");
        List<GenreListModel> genresList = Arrays.asList(new GenreListModel());

        resource = context.create().resource("/content/mypage/cards",
                "sling:resourceType", "interview/components/DynamicSearchCards",
                "title", "Dynamic search cards",
                "apiServlet", "/bin/api/servlet");
        cards = resource.adaptTo(DynamicSearchCardsModel.class);
    }

    @Test
    void testGetTitle() {
        assertNotNull(cards);
        assertEquals("Dynamic search cards", cards.getTitle());
    }

    @Test
    void testGetApiServlet() {
        assertNotNull(cards);
        assertEquals("/bin/api/servlet", cards.getApiServlet());
    }

    @Test
    void testGetGenres() {
        assertNotNull(cards);
        assertNull(cards.getGenres());
    }
}
