## Project Summary: 
#### Name: CSV Parser
#### Description: Built generic comma-separated value (CSV) parser library and search utility in Java
#### Login & Hours: lwang58; ~18hours 
#### Github Link: https://github.com/lw2403/csv-lw2403-master-main-handin.git

## High level design decisions
### Classes & Interface
2 Interfaces(CreatorFromRow and Query) are used by classes that I created below. 
I separated classes into separate packages based on user's stories.
* Main: 
  * CreatorFromRow (Interface): this interface transforms a row into an object type T
  * FactoryFailureException: when CreatorFromRow fails to work, throw this exception
  * Main: used to interact with users and tell them how to run the programs 
* Usertory01:
  * CsvFileParser: this class reads csv files and search by column name or index 
  * CsvDataRowsParser: this class checks which data meet requirements. It's also used and called in other user stories
  * CsvParseFailureException: when file read or parse error such as file or column index/name does not exist, throw this error
* Userstory02:
  * CsvParser2: this class gets data from the Reader 
* Userstory03:
  * CsvParser3: this class transforms the returned data into an object type of my choice
  * Star1Entity and Star2Entity: object representations for the same data
  * Star1EntityCreatorFromRow: transforms data into start1entity object type
  * Start2EntityCreatorFromRow: transforms data into start2entity object type
* Supplementary CS1430 Userstory
  * Query (Interface): this interface checks if a row matches the current expression
  * BasicQuery: column index = string e.g 1 = abc
  * AndQuery: and (subquery1 , subquery2) 
  * NotQuery: not (subquery)
  * OrQuery: or (subquery)
  * QueryParser: transforms string expression to query object 
  * QueryParserFailureException: transform fails
  * CsvParser4: search condition is now an expression 

### Data Structures

* List: ArrayList
  * I used List<List<String>>:  to store rows that matches requirements 
  * I used List<String> to store one row 
* "and" "or" "not" queries:
Expression ->(interpreted) as query -> basic queries -> sub queries....(recursively)
similar to a tree

### Runtime/space optimization 
While searching through the files to find the data that meet requirements, one way to do this is 
to store the entire file/data List<List<String>> first, and then check if each meets requirements. 
This way wastes a lot of run time and space. So, I decided to read row first and only store the ones
that meet requirements into List<String>. This saves a lot of time and space compared to putting 
everything in List<List<String>> first.

## Errors & Bugs
* After testing, there’s no bugs.
* During the project, I handled errors in 3 main way:
  * In sup/userstory01/userstory02/userstory03 program packages, I will catch and throw the error: EXAMPLE  catch (IOException e) { String errMsg = "ERROR: read error," + 
  e.getMessage(); throw new CsvParseFailureException(errMsg, e); }
  * In main, when there’s an error such as the user not entering a valid input, I will use System.err.println(errMsg) to let the user know the issue such as “Invalid Input!” When they enter -2 for column index
  * In test files, I check if there’s an error: EXAMPLE try {reader = new FileReader("data/stars/ten-star.csv");} catch (FileNotFoundException e) {Assertions.fail(e);}

## Tests
I created a test file for each of the user stories. I also got rid of any warnings or checkstyle errors. 
* User story01: 
  * test 1 searches csv with header using column index, search value
  * test 2 searches csv without header using column name, search value
  * test 3-6 search for values that are, and aren’t, present in the CSV
  * test 7 tests exception with invalid columns
* User story02:
  * test 1 tests CSV data in FileReader 
  * test 2 tests CSV data in FileReader
  * other tests test all rows, CSV data in StringReader
* User story03:
  * test 1 tests Star1EntityCreatorFromRow() to transform to start1entity
* Supplementary CS1430 Users tory: 
  * All the tests test expression, tests basic query, and query, or query, and not query

For main, I tested the program through interacting with the terminal to check for errors such as invalid entry.

## Build, Run Program and Tests
### Build
1. Use IDEA to open project and click pom.xml to transform to maven project
2. mvn package 

### Run Program & Tests
2. To run program: choose user story and follow instruction to run program 
3. To run test: choose user story from the directory and click "run" to run the corresponding JUnit Test

EXAMPLE: User story 1 

--------help---------

1 ---------userstory01

2 --------- userstory02

3 ---------userstory03

4 ---------cs1340sup

choose program, input 1/2/3/4:
   1
   
input csv file path:

/Users/aw/Documents/GitHub/csv-lw2403-master-main/data/stars/ten-star.csv

input the first row is header(y/n): 

y

input column index(-1 means search in all column):

1
   
input search value:

Rigel

record count:2

records:
   
71454, Rigel Kentaurus B, -0.50359, -0.42128, -1.1767

71457, Rigel Kentaurus A, -0.50362, -0.42139, -1.17665




