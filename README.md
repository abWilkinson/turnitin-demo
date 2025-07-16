# Demo Project

## Data Storage Design

### Database

For data storage I've used a Postgresql Database. For this project I've taken a normalized approach
to schema design. The schema is designed as per the diagram below.

Skills and Championships have been modelled as separate entities to reduce duplication and are linked to athletes via link tables.
The superset skills are handled by a recursive parent_id for each skill. This could be improved upon by using a
closure table which I'll mention more in the section on improvements that can be made to this design.

![Database diagram.png](Database%20diagram.png)

### Potential improvements

This current design works for this demo, however there are several performance improvements which could be made to
improve scalability and performance.

#### Closure Table for skills

Currently, in order to find the superset skills a recursive CTE is being used. Although this is quite highly optimised
by Postgres, it's not ideal for scaling when dealing with either deep hierarchies or high throughput.

A closure table would store all paths through the hierarchy resulting in a simple indexed join. However, this also adds
more write complexity, adding or updating a new record needs to maintain the closure table.


#### Caching skills in memory

If the skill hierarchy has infrequent changes, it could be cached in memory instead of performing DB lookups each time.

#### Materialized View for professional experience

Currently, when filtering by experience the minimum year is calculated dynamically for each query. If this data does
not often change, then a materialized view could contain the earliest year for each athlete. This would depend on how
often the data changes, as the materialized view would need to be refreshed whenever the data becomes stale.

#### Merging queries

When filtering by skills this is currently a 2-step process, this is to reduce the SQL complexity but comes at a cost
of additional round trips. This could be optimized by merging the recursive CTE into the main query.

### Potential Alternatives
This approach uses a normalized relational schema, but there are other viable options I considered.

#### JSONB columns with PostgreSQL
Using JSONB could store more flexible data such as the skills and championship data. Using GIN indexes it can be filtered
efficiently. This would be more useful if there are varying attributes that are difficult to define in a schema. The main
trade off here is losing the strong referential integrity.

I decided against this and to just stick with a single approach of using normalized tables.

#### NoSQL e.g. DynamoDB

Would be a good option for very high throughput or for unstructured data. The skills and documents could be stored
in a single document with the athlete.

For this project it felt a bit overkill and would require some cloud dependencies adding complexity.

## App Design

### Spring Boot

The application is developed using Spring Boot (Java 21) built with Maven.
* Allows for fast development
* Integrates with other libaries out of the box
* Runs as a self-contained application making it easy to run in Docker
* Simple to create REST API which seems to fit the search requirement well

### JPA + Hibernate
* Allows modelling entities as Java classes
* Build dynamic queries without raw SQL
* Database agnostic

## Running the app

The app comes with a `docker-compose.yml` file. It can be run using `docker-compose up` or `docker-compose up db`
to only start the postgres database and run the app locally (e.g. through an IDE). On starting the app, the schema will be
created along with some sample data as part of a Flyway database migration.

The search is available as a `GET` request at http://localhost:8080/api/athletes/search and can be triggered via browser, curl, postman etc.

The filtering can be added via query parameters:
```
name - string (e.g. anna). Matches anywhere in a name, not exact matching
ageRange - a valid age range (<18, 18-21, 22-25, 26-30, >30)
skills - a string array of skills (inclusive)
minExperience - integer of years (e.g. 10)
```

Some search examples:
```
Find anyone with an 'a' in their name:
http://localhost:8080/api/athletes/search?name=a
```
```
Find based on all fields
http://localhost:8080/api/athletes/search?name=a&skills=Snowboarding,Cycling&ageRange=>30&minExperience=10
```

Current sample data loaded:
```
-----

Name: Anna Gasser 

Birthdate: August 16, 1991

Skills: Winter sports, snowboarding, gymnastics

Championships: World Snowboard Tour 2010, World Snowboard Tour 2011, FIS Snowboarding World Championship 2013, Winter Olympics 2014, FIS Snowboarding World Championship 2015, FIS Snowboarding World Championship 2016, FIS Snowboarding World Championship 2017

-----

Name: Tess Ledeux

Birthdate: November 23, 2001

Skills: Skiing

Championships: FIS race 2016, FIS Freestyle World Championship 2017

-----

Name: Nairo Quintana

Birthdate: February 4, 1990

Skills: Cycling

Championships: Route du Sud 2012, Tour of the Basque Country 2013, Tour de France 2014, Tour de France 2015, Tour de France 2016

-----

Name: John Smith

Birthdate: January 1, 2010

Skills: Alpine

Championships: FIS Snowboarding World Championship 2025

-----

Name: Homer Simpson

Birthdate: January 1, 2005

Skills: Cycling

Championships: None
```

Currently there is a heirarchy of:
```
Winter Sports -> Snowboarding -> Alpine
              -> Skiing
```