[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Build Status](https://travis-ci.org/rkonovalov/jsonignore.svg?branch=master)](https://travis-ci.org/rkonovalov/jsonignore)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.rkonovalov/json-ignore/badge.svg?style=blue)](https://search.maven.org/search?q=a:json-ignore)
[![Javadocs](https://www.javadoc.io/badge/com.github.rkonovalov/json-ignore.svg)](https://www.javadoc.io/doc/com.github.rkonovalov/json-ignore)
[![codecov](https://codecov.io/gh/rkonovalov/jsonignore/branch/master/graph/badge.svg)](https://codecov.io/gh/rkonovalov/jsonignore)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/a0133be1929145eabe7d50217587b896)](https://www.codacy.com/app/rkonovalov/jsonignore?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=rkonovalov/jsonignore&amp;utm_campaign=Badge_Grade)
[![SonarCloud](https://sonarcloud.io/api/project_badges/measure?project=rkonovalov_jsonignore&metric=alert_status)](https://sonarcloud.io/dashboard?id=rkonovalov_jsonignore)

# Json Ignore module
This module could be used in Spring Web Service project for filter(exclude) of fields in Service response.
When you used Spring @View interface and need more powerful and flexibility, this module could be useful.
For information please follow the links below.



## Index
* [Main page](https://rkonovalov.github.io/projects/jsonignore/1.0.6/)
* [Requirements](https://rkonovalov.github.io/projects/jsonignore/1.0.6/requirements/)
* [Installation](https://rkonovalov.github.io/projects/jsonignore/1.0.6/installation/)
* [Getting started](https://rkonovalov.github.io/projects/jsonignore/1.0.6/getting-started/)
* [Examples](https://rkonovalov.github.io/projects/jsonignore/1.0.6/examples/)
  * [Simple field filter](https://rkonovalov.github.io/projects/jsonignore/1.0.6/examples/filter-field/)  
  * [Session strategy filter](https://rkonovalov.github.io/projects/jsonignore/1.0.6/examples/filter-strategy/) 
  * [XML Schema-based filter configuration](https://rkonovalov.github.io/projects/jsonignore/1.0.6/examples/filter-file/)
  * [Whole Spring Controller filtration](https://rkonovalov.github.io/projects/jsonignore/1.0.6/examples/filter-controller/)
* [Release Notes](https://rkonovalov.github.io/projects/jsonignore/1.0.6/release-notes/)

# Release notes

## Version 1.0.6
    * Added JSON/XML converters inherited from HttpMessageConverter
    * Removed native reflection and added Jackson BeanSerializerModifier for field filtering
    * Added EnableJsonFilter annotation for enabling/disabling filtration
    * Added ability to apply filter annotations on whole Spring Rest controller
    * Fixed bugs

## Version 1.0.5
    * Added Filter provider for improving execution speed
    * Fixed bugs 

## Version 1.0.4
    * Added xml Schema-based configuration
    * Fixed bugs 

## Version 1.0.3
    * Added session strategy filtering

## Version 1.0.2
    * Added additional constructors

## Version 1.0.0
    * Initial release
