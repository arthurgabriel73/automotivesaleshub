package br.com.fiap.automotivesaleshub.acceptance.steps

import org.junit.platform.suite.api.ConfigurationParameter
import org.junit.platform.suite.api.IncludeEngines
import org.junit.platform.suite.api.SelectClasspathResource
import org.junit.platform.suite.api.Suite

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = "cucumber.glue", value = "br.com.fiap.automotivesaleshub")
@ConfigurationParameter(
    key = "cucumber.plugin",
    value = "pretty, summary, timeline:build/reports/timeline, html:build/reports/cucumber.html",
)
class CucumberTest
