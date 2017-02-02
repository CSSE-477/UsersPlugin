# Users Directory Pluging for #Team YoloSwag Web Server
Jesse Shellabarger, Tayler How, Collin Trowbridge, Steve Trotta


[![build status](https://ada.csse.rose-hulman.edu/CSSE477-YoloSwag/UsersPlugin/badges/master/build.svg)](https://ada.csse.rose-hulman.edu/CSSE477-YoloSwag/UsersPlugin/commits/master)
[![coverage report](https://ada.csse.rose-hulman.edu/CSSE477-YoloSwag/UsersPlugin/badges/master/coverage.svg)](https://ada.csse.rose-hulman.edu/CSSE477-YoloSwag/UsersPlugin/commits/master)

The UsersPlug extends functionality of [Team #YoloSwag *dabs* Web server]https://ada.csse.rose-hulman.edu/shellajt/CSSE477Project). It adds functionality to track users and groups
of users. The API is access via /userapp/users/{id} and /userapp/groups/{id}. Both APIs suppert HEAD, DETELTE, GET, PUT and POST HTTP requests. It uses two different servlets, the
UsersServlet and the GroupsServlet to manage users and groups of users.