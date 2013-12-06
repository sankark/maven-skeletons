package com.spring.configuration;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.google.common.base.Predicates.notNull;
import static com.google.common.collect.Maps.filterValues;
import static org.fest.util.Preconditions.checkNotNull;

public final class MappedConfig implements Config {
   private final Map<String, String> properties;

   public MappedConfig(Map<String, String> properties) {
      if (properties instanceof ImmutableMap<?, ?>) {
         this.properties = properties;
      } else {
         // Remove null values, as they are not allowed in an immutable map, and will have the same effect if omitted
         this.properties = ImmutableMap.copyOf(filterValues(properties, notNull()));
      }
   }

   public MappedConfig(Iterable<Properties> propertyValues) {
      Map<String, String> propsBld = new HashMap<>();
      for (Properties properties : checkNotNull(propertyValues)) {
         for (Map.Entry<Object, Object> entry : filterValues(properties, notNull()).entrySet()) {
            Map.Entry<String, String> stringEntry = toStringEntry(entry);
            propsBld.put(stringEntry.getKey(), stringEntry.getValue());
         }
      }
      this.properties = ImmutableMap.copyOf(propsBld);
   }

   public MappedConfig(Properties... propertyValues) {
      this(Arrays.asList(propertyValues));
   }

   @Override
   public String getProperty(String name) {
      return properties.get(name);
   }

   @Override
   public String getProperty(String name, String defaultValue) {
      return Objects.firstNonNull(getProperty(name), defaultValue);
   }

   public Map<String, String> asMap() {
      return properties;
   }

   private static Map.Entry<String, String> toStringEntry(Map.Entry<?, ?> entry) {
      Object key = entry.getKey();
      if (!(key instanceof String)) {
         throw new IllegalArgumentException("key '" + key + "' was not a string (" + key.getClass() + ")");
      }
      Object value = entry.getValue();
      if (!(value instanceof String)) {
         throw new IllegalArgumentException("value '" + value + "' was not a string (" + value.getClass() + ")");
      }
      @SuppressWarnings("unchecked")
      Map.Entry<String, String> result = (Map.Entry<String, String>) entry;
      return result;
   }
}
