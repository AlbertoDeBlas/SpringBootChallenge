# Spring Boot Challenge


## Prerequisites
	Java 11
	Docker

## Challenge
REST API for the creation of cash transactions and calculation of transaction statistics

This project comprehends 3 endpoints:
- POST		/transactions
	
  Creates a new transaction. A transaction is composed by an amount (BigDecimal) and a timestamp (ISO 8601 format in UTC)
	
  Request Body Example:
	`{
	"amount": "20.432",
	"timestamp": "2021-01-11T23:59:51.312Z" }`
		
	        
	
	Return values:
	- 201 if it is successful
	- 204 transaction older than 60 seconds
	- 400 invalid JSON
	- 422 not parsable fields or future transaction (only valid for Marty McFly!)
		
- DELETE 	/transactions
	
  Deletes all created transactions
	Return value:
	- 204 all transactions deleted
- GET 		/statistics
	
  Statistics for the transaction within the last 60 seconds
  
  Returns:
	- sum (BigDecimal), average(BigDecimal), max(BigDecimal), min(BigDecimal), count(long) -> calculated with the amount attribute, all decimals rounded half up with exactly two decimals.

  Response Body Example:
   	`{
	"sum": "40.00","avg": "18.00","max": "20.45",
"min": "15.50", "count": 20
	}`

#### Additional features:
- The services are threadsafe with concurrent requests
- Transactions older than 60 seconds are discarded

#### Implementation details:
- Framework: Spring Boot
- Caching library for transactions: [Caffeine](https://github.com/ben-manes/caffeine) 
- Dockerization of the application in the Maven package phase 
