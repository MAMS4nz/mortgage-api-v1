# MORTGAGE API V1 #

## "Treat this application as a real MVP that should go to production" ##

If one takes to heart those words, everything changes. An application purportedly set to go to Production, even if 
just as make-belive, has to take into account a completely different set of concerns. In this exercise we've tried to 
just hint tentatively to the most indispensable necessities of such a project (while, admittedly, still many aspects
of an actual project have not been implemented to avoid unnecessary and unjustifiable overkills).

### Running the service ###

This service has been developed as a Java Spring Boot project with Java 21 compatibility, with Gradle
as its building tool. You will need Java 21 or higher installed in your machine. Even if the project ships
with a fat jar ready to be run, it comes very in handy to rely on Gradle to build and run the project 
yourself. The best way to do this is by invoking .\gradlew.bat in the root folder of the project, so that
the most appropriate version of Gradle is employed. Of course, if your IDE already packs Gradle, as it's the case with
Intellij IDEA, you can bootRun the service from within the IDE, what is really convenient. 

It was our initial intent to Dockerize the project for you to ease the prospects of running the entire service and its 
environment in a container, but for the time being that goal has been dropped. This means that some environment variables
will have to be set for the service to operate. Again, taking to heart this ideal of working as if the project were an 
MVP, we've set up a three different configurations, one per your usual basic environments (prod, qa, dev) based on Spring
profiles and System variables, out of simplicity:

SPRING_PROFILES_ACTIVE

MORTGAGE-API-ACTUATOR-ADMIN-USER-DEV
MORTGAGE-API-ACTUATOR-ADMIN-PASSWORD-DEV

MORTGAGE-API-ACTUATOR-ADMIN-USER-QA
MORTGAGE-API-ACTUATOR-ADMIN-PASSWORD-QA

MORTGAGE-API-MEM-DB-SYS-USER-DEV
MORTGAGE-API-MEM-DB-SYS-USER-PASSWORD-DEV

MORTGAGE-API-MEM-DB-SYS-USER-QA
MORTGAGE-API-MEM-DB-SYS-USER-PASSWORD-QA

MORTGAGE-API-MEM-DB-SYS-USER-PROD
MORTGAGE-API-MEM-DB-SYS-USER-PASSWORD-PROD

The first one takes one of three possible values: dev, qa, prod. For the rest one can set whatever values are found to 
be proper. In a self-contained demonstrator as this one, it does not really matter. Depending on the profile chosen, 
different env variables come into play, but as their names indicate they allow you to access some Actuator monitoring 
endpoints (only available in dev and qa, with the latter being more restricted than the former) and, most importantly,
the in-memory H2 database web console, which incidentally was the storage solution selected for this project, bearing in
mind this was pretended to be implemented as a real business service. In passing we'll point out that this H2 database
is set to operate in 'MS SQL SERVER compatibility mode' and with a minimally realistic configuration. The idea would be 
to facilitate a medium-term migration to a more powerful database solution, should the application grow in the future.

### Logging, monitoring, health, metrics, traceability, instrumentation ###

Once again, initially it was considered to expose JMX communication from the service for an appropriate client (e. g. 
Dynatrace, or other), but on a second thought it was judged as way to ambitious for an exercise like this. Let's think
about, for example, the security requirements. Also an in-house tracing solution, akin to a database auditory table, was
envisioned, but this idea was also dropped for similar reasons. Not much attention has been given, either, to setting up
a custom system of logs (format, rotation, purging, etc.) since the default logging of the app (based on Java Logback)
is way more than enough at this point of development. Again, this should be of importance in an actual project, but 
we believe it falls outside the scope of this assessment. Just Spring's Actuator has been implemented, and even that 
leaving as a pending task some of its requirements (e. g., setting up HTTPS and replacing the BASIC auth scheme).

All actuator URL's share this structure: /h2-console/actuator-name

### Storage, persistence ###

As the in-memory solution to store the required information we settled on a single instance of the relational database 
H2. It feels as a good middle term between some memory cache and a full-blown database. On top of that, it features the
capability to persist the information between application restarts, were that required at some point. Furthermore, this
database eases all the process of testing and it's a typical solution for Spring Boot projects, enjoying plenty of 
documentation. The database is created and populated our of scripts (no ORM-generation here): 'schema.sql' and 'data.sql'.

The H2 web console can be found in: /h2-console

### Security. Authentication and authorization ###

An actual production microservice would feature an actual scheme of authentication and role authorization. On the onset of 
this exercise we felt tempted of setting up some minimalistic solution to mock or hint to this reality, probably some 
very basic Outh2 flow, but as the project progressed it increasingly felt as unnecessary and over-ambitious. Moreover,
it could very well be objected that the two endpoints exposed by the API might be considered, by their nature, intended
to be offered in an unrestricted a public way as consultation services. If that were the case, no stringent security 
requirements would be needed from the POV of the app itself. Concerns like perimeter security, load balancing, workload 
management/defense and such would mostly fall on gateway, AD and escalation solutions we assume would always exist in our 
infrastructure, were it cloud-based, on-premise or hybrid.

Therefore, the application includes Spring Security mostly only to secure in the most basic of ways the access to the 
Actuator endpoints in the dev and qa environments (the second standing more restricted than the former).

### API documentation: Swagger (OpenAPI) via SpringDoc Open API ###

Now, regarding the documentation of the API endpoints, we've resorted to the usual suspect: Swagger, implemented via 
Spring Doc API. The web documentation and machine-friendly docs may be found here:

/swagger-ui/index.html 
/v3/api-docs

There are only two endpoints, with no security requirements, no token or credentials needed. One GET operation to retrieve
the full list of mortgage rates and another one, a POST endpoint, to evaluate the feasibility and monthly payment of the
mortgage loans, as described by the assessment. Both of them utilize JSON for their communication, as expected, and the 
characteristics and data types of the request and response DTO objects may be trivially consulted either in the Swagger 
interface or out of the code itself. They're really simple. It was our original intent to greatly enrich the Swagger web 
document with a wealth of information, via SpringDoc annotations in our API, but it's turned to be totally superfluous.

### Tests ###

Even if the application does not feature a full set of tests, we wanted nevertheless to include some bare minimum of 
coverage. End-to-end and load tests were wittingly left out of the effort, while we also considered that providing full 
unit test coverage to all the layers of the service was too much for this type of exercise, even if ncessary in any
actual business project. Therefore, we've just included a set of integration tests for our single controller 
(MortgageController) and another set of unit tests for our only service implementation (MortgageServiceImpl). The first
group loads the entire context of the app (which is still fairly light) and mocks all the possible flows we feature as 
of now, resorting to the actual beans and components, database included. On the other hand, the service unit tests cover
in an isolated way all the kernel business logic of the app. Positive and negative outcomes are tested alike. We believe 
that it makes senses to leave repositories, mappers and others out of the testing suite for now.

As of now all tests are executed together, no separation existing between integration and unit tests, since the service
is still so small. They are launched when building the service and also with the Gradle 'test' task.

To ease your manual testing of the application a tiny POSTMAN collection has been included as part of the project as well.

### Development decisions ####

First of all we'll note that we took some liberties in the naming of DTO properties and the structuring of the REST 
endpoints, always bearing in mind the long-term scalability of the service and the best practices in REST API design. 
The drive was always to structure things in the best possible way and to avoid future confusions or collisions in the
naming of objects or elements.

Since we're working with H2 in 'MS SQL SERVER mode', we lack in the database a data type able to hold timezone information.
Therefore, we assume and enforce all across the application a single timezone, UTC.

Regarding the development flow and code-branching strategy, the initial intent was to adopt the GitHub Flow style, give 
its simplicity, but as time went by even this turned out to be unnecessary in such a personal endeavor. No formal protocol
was really needed at any time, in all honesty. No features or separate branches, let alone releases or tags.

### Business logic decisions ###

Ok, this IS important...

Interest Rates: As is common in mortgage loans, we assume that a longer repayment term entails greater risk for the 
financial institution, which will therefore apply a higher interest rate. Conversely, shorter terms result in lower 
uncertainty and, consequently, lower interest rates for the borrower.

For simplicity, we will assume that this is the only factor influencing the interest rate applicable to the loan. The 
direct relationship between loan duration and interest rate is implicitly reflected in the mortgage_rates table, where 
the maturity_period_months column is subject to a unique key constraint.

Additionally, following standard industry practice, we also consider a minimum repayment term of ten years, with a 
maximum of forty years in certain cases (e.g., young borrowers, mortgages with guarantors, etc.).

### CI / CD ###

No provisions were made for the (indispensable) integration of the service code base with CI/CD platforms, such as Jenkins,
Tekton, GCB and alike. We believe this falls well outside the scope of the assessment.

### Code quality and security. API analysis ###

The same goes for code quality, security and style tools such as Sonarqube, Checkmarx, FOSSA and similar ones, even if in
a real project those would be mandatory. Similarly, no external test coverage tool (think of Jacoco) has been integrated 
for now.

### Architecture, technologies ###

A non-negligible amount of time was devoted to determining the most appropriate architecture for a project with these 
characteristics. Two were the main contenders: a classical multi-tiered REST API (controllers, services, repositories, 
etc) and a more complex clean/hexagonal architecture (ports, adaptarse, usecases, domain...). We finally decided to go
with the lighter solution (multi-tiered, linear) since in our experience CLEAN usually comes with quite some overhead in
the overall number of classes and mappers. For a project like this it was not really justified, since we do not expect 
plug in and out any integrations and, on a different note, the code base will never grow that much. The directories 
structure reflects our final decision, being quite usual for a n-tiered REST API with Spring Boot.

Some technologies we've employed are (in no particular order):

- Adoptium JDK / Eclipse Temurin 21.0.5
- Java 21 (LTS and more mature than 23)
- Spring Boot 3.4.1
- Spring Data JPA with Hibernate as implementation
- Hibernate Validator
- Gradle
- H2 RDBMS
- IntelliJ IDEA
- JUnit
- Mockito
- SpringDoc OpenAPI (for Swagger generation)
- Embedded Tomcat (Spring Boot's default)
- Jackson (Spring Boot's default)
- POSTMAN
- ... and a custom 'favicon.ico'  -_o

### Licensing ###

Regarding the project's license, the typical choice for a service like this, if not freely accessible, would be a SaaS 
subscription-based license. However, due to the lack of additional information, we have opted for the **Apache License 2.0**, 
which is one of the most commonly used open-source licenses in commercial projects.
