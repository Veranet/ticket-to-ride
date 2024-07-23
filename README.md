## Ticket to Ride

### Overview
"Ticket to Ride" is a web application designed to manage travel routes 
and ticket purchases. The application allows travellers to calculate 
the most optimal travel price between two towns and purchase tickets 
if they have sufficient funds.

### Main Objects
1. Segment: A part of a route.
2. Route: A set of segments (one or more) representing a route between two towns.
3. Ticket: A permission for a traveller to travel via a certain route. It has a price.
4. Traveller: A person who takes a ticket from town A to town B.
### Pricing
* 1 segment: 5 GBP
* 2 segments: 7 GBP
* 3 segments: 10 GBP
### API Endpoints
1. Calculate the price of the most optimal travel between two towns

* Endpoint: GET /price
* Request Parameters: fromTown (String), toTown (String)
* Response: JSON object with the number of segments, total price, and currency.
2. Save the ticket to storage if a traveller has enough money

* Endpoint: POST /ticket-to-ride/ticket
* Request Body: JSON object with departure, arrival, segments, price, traveller amount, and traveller name.
* Response: JSON object with the result of the purchase and any change.
### Project Structure
The project follows a layered architecture:

* Controller Layer: Handles HTTP requests and responses.
* Service Layer: Contains business logic.
* Repository Layer: Handles database interactions.
### Technologies Used
* Java 21
* Spring Boot
* Spring Data
* Spring Security
* JUnit 5
* Mockito
* PostgreSQL
* Maven
### Setting Up the Project
1 Clone the repository:
```shell
git clone https://github.com/Veranet/ticket-to-ride
```
2 Configure the database:
* Update the application.yml file with your PostgreSQL database settings.
3 Build the project:
```shell
mvn clean package
```
### Security
* The API endpoint for calculating the price is public.
* The API endpoint for buying a ticket is private and protected with basic authentication.
* Two roles are defined: USER and ADMIN. Only users with the ADMIN role can access certain administrative endpoints.
### Additional Information
* Ensure that your IDE is configured to use Java 21.
* This project uses Maven for dependency management.
### Examples:
#### Find a ticket: 
Example input 1:  
```json
{
  "departure":"London",
  "arrival":"Bristol"
} 
```
 
Example output 1:  
```json
{
  "segments":7,"price":25,
  "currency":"GBP"
}
```
Save a ticket:  
Example input 1:  
```json
{
 "departure":"London",
 "arrival":"Bristol", 
 "segments":7,
 "price":25,
 "currency":"GBP",
 "travellerAmount":30,
 "traveller":"John Doe"
}
```
Example output 1:  
```json
{
 "result":"success",
 "change":5,
 "currency":"GBP"
}
```
 Save a ticket:  
Example input 1:  
```json
{
  "departure":"Coventry",
  "arrival":"Reading", 
  "segments":5,
  "price":17,
  "currency":"GBP",
  "travellerAmount":15,
  "traveller":"John Doe"
}
```
Example output 1:  
```json
{

  "result":"failure",
  "lackOf":2,
  "currency":"GBP"
}
```