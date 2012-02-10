package com.yammer.dropwizard.config.tests;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.yammer.dropwizard.config.FilterConfiguration;
import org.eclipse.jetty.servlet.FilterHolder;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class FilterConfigurationTest {
    private final FilterHolder holder = mock(FilterHolder.class);
    private final ImmutableMultimap.Builder<String, FilterHolder> mappings = ImmutableMultimap.builder();
    private final FilterConfiguration config = new FilterConfiguration(holder, mappings);

    @Test
    public void setsInitializationParameters() throws Exception {
        config.setInitParam("one", "1");

        verify(holder).setInitParameter("one", "1");
    }

    @Test
    public void addsInitializationParameters() throws Exception {
        config.addInitParams(ImmutableMap.of("one", "1", "two", "2"));

        verify(holder).setInitParameter("one", "1");
        verify(holder).setInitParameter("two", "2");
        verifyNoMoreInteractions(holder);
    }

    @Test
    public void mapsAUrlPatternToAFilter() throws Exception {
        config.addUrlPattern("/one");

        assertThat(mappings.build(),
                   is(ImmutableMultimap.of("/one", holder)));
    }

    @Test
    public void mapsUrlPatternsToAFilter() throws Exception {
        config.addUrlPatterns("/one", "/two");

        assertThat(mappings.build(),
                   is(ImmutableMultimap.of("/one", holder,
                                           "/two", holder)));
    }
}
