this is a REST project written in spring boot about the Sensor and its Measurements, you can work with this project through postman

============================================== Working With PostMan ==============================================
-
- create a new request with the post method to the address - http://localhost:8080/sensors/registration
- 
- Json request body {
-												"name": "sensorName"
- 									}
- 					
- you will register a new sensor on which you can record new Measurements
-
- create a new request with the post method to the address - http://localhost:8080/measurements/add
-
- Json request body {
-											"value": 51.8,
-   									"raining": true,
-    									"sensor": {
-  											"name": "sensor1"
-    									}
-										}
- 
