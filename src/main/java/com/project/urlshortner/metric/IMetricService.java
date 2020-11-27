package com.project.urlshortner.metric;

import java.util.Map;

public interface IMetricService {

  void increaseCount(final String request, final int status);

  Map getFullApiMetric();
 
}
