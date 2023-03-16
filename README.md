Create a REST API using spring boot. The API should allow user to
• Create a user.
o Should create a user successfully when all parameters are
provided.
o Should return validation errors when any of the parameter is
not provided.
• Update some attributes of user.
o Should let update parameters of user. Date of birth is not
allowed to be updated and should return an error.
• Get a user.
o Should get the user details based on user id. Additional
points on getting the address of a user as a conditional
parameter. For example, by default GET should not return
user’s address, only return address associated with user
when requested.
o Should return an error when user is not found.
The following attributes of user need to be stored in the in-memory
database.
• First name
• Last name
• Age
• Gender (M or F)
• Date of birth (DD-MMM-YYYY)
• Address (Multiple address supported)
o Type (one of RESIDENTIAL, WORK)
o Line1
o Line2
o Postcode
o City
o State
