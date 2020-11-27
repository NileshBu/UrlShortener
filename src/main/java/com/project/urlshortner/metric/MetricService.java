package com.project.urlshortner.metric;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.stereotype.Service;

@Service
public class MetricService implements IMetricService {

  private ConcurrentMap<String, ConcurrentHashMap<Integer, Integer>> metricMap;

  public MetricService() {
    metricMap = new ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>>();
  }

  @Override
  public void increaseCount(final String request, final int status) {
    increaseMainMetric(request, status);
  }

  @Override
  public Map getFullApiMetric() {
    return metricMap;
  }

  public void increaseMainMetric(final String request, final int status) {
    ConcurrentHashMap<Integer, Integer> statusMap = metricMap.get(request);
    if (statusMap == null) {
      statusMap = new ConcurrentHashMap<Integer, Integer>();
    }

    Integer count = statusMap.get(status);
    if (count == null) {
      count = 1;
    } else {
      count++;
    }
    statusMap.put(status, count);
    metricMap.put(request, statusMap);
  }


}
