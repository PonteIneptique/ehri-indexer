package eu.ehri.project.search.converter.impl;

import com.google.common.collect.Iterables;
import eu.ehri.project.search.source.Source;
import eu.ehri.project.search.source.impl.InputStreamJsonSource;
import org.codehaus.jackson.JsonNode;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertFalse;

/**
 * @author Mike Bryant (http://github.com/mikesname)
 */
public class JsonConverterTest {

    private static String testResource = "inputdoc.json";

    private JsonNode inputNode;

    @Before
    public void setUp() throws Exception {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(testResource);
        Source<JsonNode> source = new InputStreamJsonSource(stream);
        try {
            inputNode = Iterables.get(source.getIterable(), 0);
        } finally {
            source.finish();
            stream.close();
        }
    }

    @Test
    public void testCorrectNumberOfOutputNodes() throws Exception {
        assertEquals(1, Iterables.size(new JsonConverter().convert(inputNode)));
    }

    @Test
    public void testOutputDocIsDifferent() throws Exception {
        JsonNode out = Iterables.get(new JsonConverter().convert(inputNode), 0);
        assertNotSame(out, inputNode);
    }

    @Test
    public void testOutputDocContainsRightValues() throws Exception {
        JsonNode out = Iterables.get(new JsonConverter().convert(inputNode), 0);
        assertFalse(out.path("id").isMissingNode());
        assertFalse(out.path("type").isMissingNode());
    }
}
