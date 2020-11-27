package com.project.urlshortner.controller;

import com.project.urlshortner.metric.IMetricService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/statistics/")
public class ApiStatisticsController {

  @Autowired
  private IMetricService metricService;

  @RequestMapping(value = "/api_metrics", method = RequestMethod.GET)
  @ResponseBody
  public Map getMetrics() {
    return metricService.getFullApiMetric();
  }

}
