package com.platformcommons.platform.service.report.application.utility;

import liquibase.resource.InputStreamList;
import liquibase.resource.ResourceAccessor;

import java.io.IOException;
import java.io.InputStream;
import java.util.SortedSet;

public class InputStreamResourceAccessor implements ResourceAccessor {

    private InputStream inputStream;

    public InputStreamResourceAccessor(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public InputStreamList openStreams(String relativeTo, String streamPath) throws IOException {
        return null;
    }

    @Override
    public InputStream openStream(String relativeTo, String streamPath) throws IOException {
        return inputStream;
    }

    @Override
    public SortedSet<String> list(String relativeTo, String path, boolean recursive, boolean includeFiles, boolean includeDirectories) throws IOException {
        return null;
    }

    @Override
    public SortedSet<String> describeLocations() {
        return null;
    }
}
