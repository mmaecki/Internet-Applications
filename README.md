# iadi-project-assignment-65355_65337_65324_65343
iadi-project-assignment-65355_65337_65324_65343 created by GitHub Classroom


Univeristy project created by a team of four - Mateusz Zieleziński, Anotni Lasik, Łukasz Mirek i Mateusz Małecki.

![example workflow](https://github.com/IADI-FCT-NOVA/iadi-project-assignment-65355_65337_65324_65343/actions/workflows/maven.yml/badge.svg)

This project backend starts on port 4200 and frontend on port 3000 (by npm start)

Possibilities of login

  username: Antos, password: 123 - role Client
  
  username: Kubica, password: 123 - role Driver
  
  username: robotnik, password: 123 - role Hub worker
  
  username: franek.kimono, password: 123 - role Manager


working endpoints:

  localhost:3000 - login page/home page

  localhost:3000/clientPackages" - packages list for client 

  localhost:3000/createClientPackage" - form to create package for client

  localhost:3000/createMessage/:packageId - form to create message

  localhost:3000/hubsPackagesList" - list of packages for hubworker in hub

  localhost:3000/driverPackages" - list of packages for driver in truck

  localhost:3000/clientMessages" - list of messages for client


change status of shipment for hubworker on frontend is not possible
