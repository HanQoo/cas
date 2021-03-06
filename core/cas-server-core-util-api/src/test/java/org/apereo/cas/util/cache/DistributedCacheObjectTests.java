package org.apereo.cas.util.cache;

import org.apereo.cas.util.PublisherIdentifier;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.val;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is {@link DistributedCacheObjectTests}.
 *
 * @author Misagh Moayyed
 * @since 5.3.0
 */
@Tag("Simple")
public class DistributedCacheObjectTests {
    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper()
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .findAndRegisterModules();
        MAPPER.activateDefaultTyping(MAPPER.getPolymorphicTypeValidator(),
            ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
    }

    @Test
    public void verifySerialization() throws Exception {
        val o = DistributedCacheObject.<String>builder()
            .value("objectValue")
            .publisherIdentifier(new PublisherIdentifier())
            .build();
        val file = new File(FileUtils.getTempDirectoryPath(), UUID.randomUUID().toString() + ".json");
        MAPPER.writeValue(file, o);
        val readPolicy = MAPPER.readValue(file, DistributedCacheObject.class);
        assertEquals(o, readPolicy);
    }


    @Test
    public void verifyAction() {
        val o = DistributedCacheObject.<String>builder()
            .value("objectValue")
            .publisherIdentifier(new PublisherIdentifier())
            .build();
        assertNotNull(o.toString());
        assertNotNull(o.getValue());
        assertTrue(o.getTimestamp() > 0);
        assertTrue(o.getProperties().isEmpty());
        o.getProperties().put("key", "value");
        assertFalse(o.getProperties().isEmpty());
        assertNotNull(o.getProperty("key", String.class));
        assertTrue(o.containsProperty("key"));
    }

    @Test
    public void verifyNullValue() {
        val o = DistributedCacheObject.<String>builder()
            .value("objectValue")
            .publisherIdentifier(new PublisherIdentifier())
            .build();
        assertTrue(o.getProperties().isEmpty());
        o.getProperties().put("key", null);
        o.getProperties().put("key2", "12.54");
        assertNull(o.getProperty("nothing", String.class));
        assertNull(o.getProperty("key", String.class));
        assertThrows(ClassCastException.class, () -> o.getProperty("key2", Long.class));
    }
}
