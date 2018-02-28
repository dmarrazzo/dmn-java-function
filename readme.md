DMN Projects Examples
=======================

A set of example DMN projects.
This is a repository hosted in Decision Central (Red Hat Decision Manager v7.0)

dmn-function-*
--------------

A set of projects to test a BKM calling an external Java function.

- **dmn-function** project to deploy in the kie-server
- **dmn-function-lib** project that host external Java service and data model
- **dmn-function-ks** project that host the kie-server client code

To test the sample:

1. Install the library in your maven repo (accessible from the kieserver) 

  ```
  cd dmn-function-lib
  mvn install dmn-function-lib
  ```

2. Deploy the DMN project **dmn-function** in your Decision Server (aka kieserver)

3. Execute the client application in **dmn-function-ks**
