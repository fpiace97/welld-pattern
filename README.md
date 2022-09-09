# Pattern recognition

## About
A REST API to determine every line that contains N or more point and return all point involved.

## Quick Start with Maven
Run the application using **mvn spring-boot:run** under the folder project.

The application run under port 8080.

## Methods

The service expose the following methods:

    1. POST /point: add point to space - no duplicates 
    2. GET /space: return all points in the space
    3. DELETE /space: delete all points from space
    4. GET /lines/{n}: return all lines with points having n or more point - n>1

## Documentation
Documentation is available  at **http://localhost:8080/swagger-ui/**

### Response
Response is build with a status (OK/KO) and a message. 

#### Examples

1. /point :
   - Request:

         {
            "x": 1,
            "y": "1"
         }
   - Request:
    
         {
            "status": "OK",
            "message": "Point added correctly"
         }
   
2. /lines/2 :
   - Response:
   
         {
            "status": "OK",
            "message": "[LineSegment(listPoint=[Point(x=1, y=2), Point(x=1, y=1)])]"
         }
   
3. Failed /point :
   
    - Request:
   
          {
            "x": 1,
            "y": "e"
          }
   
    - Response:
   
             {
                "status": "KO",
                "message": "Bad Request check param"
             }    



### LOG
Logs is available under /logs folder