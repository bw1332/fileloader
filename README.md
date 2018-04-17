# fileloader
Before run the project, modify the application.properties based on your OS. You could:
* setup the file folder to store the upload files
* setup the H2 database if necessary
* modify the uploading file size and request size
Application offers 3 Restful API
* /file POST  -- upload file
* /file/{id}/meta GET -- get file meta data by given id
* /file/{id} GET -- download the file by id
