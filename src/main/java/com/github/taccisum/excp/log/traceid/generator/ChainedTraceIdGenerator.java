package com.github.taccisum.excp.log.traceid.generator;

import com.github.taccisum.excp.log.traceid.TraceIdGenerator;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021-12-16
 */
public class ChainedTraceIdGenerator implements TraceIdGenerator {
    private List<TraceIdGenerator> generators;

    public ChainedTraceIdGenerator(List<TraceIdGenerator> generators) {
        this.generators = generators;
    }

    @Override
    public String generate() {
        String traceId = null;
        for (TraceIdGenerator generator : this.listGenerators()) {
            try {
                traceId = generator.generate();
            } catch (Exception ignored) {
            }
        }

        return traceId;
    }

    private List<TraceIdGenerator> cache;

    private List<TraceIdGenerator> listGenerators() {
        if (this.cache == null) {
            cache = generators
                    .stream()
                    .filter(g -> !(g instanceof ChainedTraceIdGenerator))
                    .sorted((a, b) -> a.getOrder() - b.getOrder())
                    .collect(Collectors.toList());
        }
        return this.cache;
    }
}
